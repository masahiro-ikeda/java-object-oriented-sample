import java.sql.Time;

public class WorkTimeCalculator {

    // フィールド定数
    private static final long ONE_MINUTE = 1000 * 60; // ミリ秒で1分

    // フィールド変数
    final private Time start; // 開始時間
    final private Time finish; // 終了時間

    /**
     * コンストラクタ
     *
     * @param start  開始時間
     * @param finish 終了時間
     */
    public WorkTimeCalculator(Time start, Time finish) {
        this.start = start;
        this.finish = finish;
    }

    /**
     * その日の労働時間を算出する
     *
     * @return 労働時間(分単位で)
     */
    public int getWorkTimeByMinute() {
        // ミリ秒単位で取り出す
        long startByMillisecond = start.getTime();
        long finishByMillisecond = finish.getTime();

        // 労働時間を計算して分単位に変換
        long workTimeByMillisecond = finishByMillisecond - startByMillisecond;
        long workTimeByMinute = workTimeByMillisecond / ONE_MINUTE;

        // キャストしてint型で変換
        return (int) workTimeByMinute;
    }
}
