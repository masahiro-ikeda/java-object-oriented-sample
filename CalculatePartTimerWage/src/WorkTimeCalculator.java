import java.util.Date;

/**
 * 労働時間の計算を行う専用クラス
 * 単位変換は可読性を下げるのでlong値(ミリ秒単位)で計算し、return時に必要に応じて変換する
 */
public class WorkTimeCalculator {

    // フィールド定数
    private static final long ONE_MINUTE = 1000 * 60; // ミリ秒で1分

    // フィールド変数
    private final Date start; // 開始時間
    private final Date finish; // 終了時間

    /**
     * コンストラクタ
     *
     * @param start  開始時間
     * @param finish 終了時間
     */
    public WorkTimeCalculator(Date start, Date finish) {
        this.start = start;
        this.finish = finish;
    }

    /**
     * その日の労働時間を算出する
     *
     * @return int 労働時間(分単位で)
     */
    public int getWorkTimeByMinute() {
        // ミリ秒単位で取り出す
        long start = this.start.getTime();
        long finish = this.finish.getTime();

        // 労働時間を算出
        long workTime = start - finish;

        // 分単位に変換 & int型にキャストしてreturn
        long workTimeByMinute = workTime / ONE_MINUTE;
        return (int) workTimeByMinute;
    }
}
