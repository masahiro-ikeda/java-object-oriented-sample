import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

public class CalculatePartTimerWage {
    private static final String SEPARATOR = ",";

    public static void main(String[] args) {

        // アルバイト従業員一覧の読み込み
        final String PATH_PART_TIMERS = "アルバイト従業員一覧ファイル(PartTimers.csv)のパスを設定して下さい。";
        FileManager partTimersFile = new FileManager(PATH_PART_TIMERS);
        List<String> partTimers = partTimersFile.getContent();

        // 勤怠履歴の読み込み
        final String PATH_ATTENDANCES = "勤務履歴ファイル(Attendances.csv)のパスを設定して下さい。";
        FileManager attendanceFile = new FileManager(PATH_ATTENDANCES);
        List<String> attendances = attendanceFile.getContent();

        // ダブルループで従業員ごとのアルバイト代を計算する
        for (String partTimer : partTimers) {

            // アルバイト従業員のデータを取り出し
            String[] partTimerDetail = partTimer.split(SEPARATOR);
            String targetId = partTimerDetail[0];
            String targetName = partTimerDetail[1];
            BigDecimal PerHourWage = new BigDecimal(partTimerDetail[2]);
            BigDecimal totalWage = BigDecimal.ZERO;

            for (String attendance : attendances) {
                String[] attendanceDetail = attendance.split(SEPARATOR);
                String selectedId = attendanceDetail[1];

                // 読み込んだ勤怠履歴が違う人の場合はなにもしない
                if (!targetId.equals(selectedId)) {
                    continue;
                }

                // 労働時間の計算
                Time start = Time.valueOf(attendanceDetail[2]);
                Time finish = Time.valueOf(attendanceDetail[3]);
                WorkTimeCalculator workTimeCalculator = new WorkTimeCalculator(start, finish);
                int workTime = workTimeCalculator.getWorkTimeByMinute();

                // 日給の計算
                WageCalculator wageCalculator = new WageCalculator(PerHourWage, new BigDecimal(workTime));
                BigDecimal dailyWage = wageCalculator.getDailyWage();

                // 合計金額に加算
                totalWage = totalWage.add(dailyWage);
            }

            System.out.println(targetName + " さんのお給料は " + totalWage + " 円でした。");
        }
    }
}
