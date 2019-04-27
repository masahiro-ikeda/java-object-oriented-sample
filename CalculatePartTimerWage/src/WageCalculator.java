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

    // 法定労働時間
    private final static BigDecimal LEGAL_WORKING_TIME = new BigDecimal(8).multiply(ONE_HOUR_BY_MINUTE);

    /**
     * 日給を計算する
     *
     * @return BigDecimal 日給額
     */
    public BigDecimal getDailyWage() {

        BigDecimal basicWorkTime; // 基本労働時間
        BigDecimal overWorkTime; // 時間外労働時間

        // 総労働時間から基本労働時間と時間外労働時間の算出
        if (this.workTimeByMinute.compareTo(LEGAL_WORKING_TIME) > 0) {
            basicWorkTime = LEGAL_WORKING_TIME;
            overWorkTime = workTimeByMinute.subtract(LEGAL_WORKING_TIME);
        } else {
            basicWorkTime = this.workTimeByMinute;
            overWorkTime = BigDecimal.ZERO;
        }

        // 基本賃金を計算
        BigDecimal basicWage = calculateWage(basicWorkTime, false);
        // 時間外割増賃金の計算
        BigDecimal overTimeWage = calculateWage(overWorkTime, true);
        // 日給の計算
        BigDecimal dailyWage = basicWage.add(overTimeWage);

        // 念のために端数処理してreturn
        dailyWage = dailyWage.setScale(0, RoundingMode.DOWN);
        return dailyWage;
    }

    // 残業代の割増割合
    private static final BigDecimal OVER_TIME_PREMIUM = new BigDecimal("1.25");

    // 賃金計算処理を共通化
    private BigDecimal calculateWage(BigDecimal workTime, boolean isOverTime) {

        // 労働時間を 分 -> 時間 単位へ
        BigDecimal workTimeByHour = workTime.divide(ONE_HOUR_BY_MINUTE, 0, RoundingMode.DOWN);

        // 時給額に割増単価を反映
        BigDecimal actualPerHourWage = this.perHourWage;
        if (isOverTime) {
            actualPerHourWage = actualPerHourWage.multiply(OVER_TIME_PREMIUM);
        }

        return actualPerHourWage.multiply(workTimeByHour);
    }
}
