import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CalculatePartTimerWageLogic {
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
            BigDecimal perHourWage = new BigDecimal(partTimerDetail[2]);

            // 月給計算用の変数
            BigDecimal totalWage = BigDecimal.ZERO;

            for (String attendance : attendances) {
                String[] attendanceDetail = attendance.split(SEPARATOR);
                String selectedId = attendanceDetail[1];

                // 読み込んだ勤怠履歴が違う人の場合はなにもしない
                if (!targetId.equals(selectedId)) {
                    continue;
                }

                // 労働時間の計算
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date start = null;
                Date finish = null;
                try {
                    start = format.parse(attendanceDetail[2]);
                    finish = format.parse(attendanceDetail[3]);
                } catch (ParseException e) {
                    e.getStackTrace();
                }
                WorkTimeCalculator workTimeCalculator = new WorkTimeCalculator(start, finish);
                int workTimeByMinute = workTimeCalculator.getWorkTimeByMinute();

                // 日給の計算
                WageCalculator wageCalculator = new WageCalculator(perHourWage, new BigDecimal(workTimeByMinute));
                BigDecimal dailyWage = wageCalculator.getDailyWage();

                // 合計金額に加算
                totalWage = totalWage.add(dailyWage);
            }

            String targetName = partTimerDetail[1];
            System.out.println(targetName + " さんのお給料は " + totalWage + " 円でした。");
        }
    }
}
