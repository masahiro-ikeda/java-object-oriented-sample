import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 日給の計算を行う専用クラス
 * 金額計算を行うので数値はBigDecimalで扱います。
 */
public class WageCalculator {

    // フィールド定数
    private final static BigDecimal ONE_HOUR_BY_MINUTE = new BigDecimal(60); // 1時間 = 60分

    // フィールド変数
    private BigDecimal perHourWage; // 時給額
    private BigDecimal workTimeByMinute; // 労働時間 (分単位)

    /**
     * コンストラクタ
     *
     * @param perHourWage      時給額
     * @param workTimeByMinute 労働時間(分単位)
     */
    public WageCalculator(BigDecimal perHourWage, BigDecimal workTimeByMinute) {
        this.perHourWage = perHourWage;
        this.workTimeByMinute = workTimeByMinute;
    }

    /**
     * 日給を計算する
     *
     * @return BigDecimal 日給額
     */
    public BigDecimal getDailyWage() {

        /*
         * ここでは労働時間をhour単位に変換して計算していますが、
         * 時給を分給に変換して計算する方法もあります。
         *
         * <例> 時給1200円で3時間30分働いた場合
         *      ・時給1200円 * 3.5時間 = 日給4200円
         *      ・分給20円 * 210分 = 日給4200円
         *
         * 法律的には給与は分単位で支払うことになっているので後者の方が正確かもしれません。
         */

        // 労働時間を 分 -> 時間 単位へ
        BigDecimal workTimeByHour = this.workTimeByMinute.divide(ONE_HOUR_BY_MINUTE, 0, RoundingMode.DOWN);

        // 日給額を計算
        BigDecimal dailyWage = this.perHourWage.multiply(workTimeByHour);

        // 端数処理
        dailyWage = dailyWage.setScale(0, RoundingMode.DOWN);
        return dailyWage;
    }
}
