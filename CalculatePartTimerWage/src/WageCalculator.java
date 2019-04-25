import java.math.BigDecimal;
import java.math.RoundingMode;

public class WageCalculator {

    // フィールド定数
    private final static int ONE_HOUR_BY_MINUTE = 60;

    // フィールド変数
    private BigDecimal wage;
    private BigDecimal workTimeByMinute;

    /**
     * コンストラクタ
     *
     * @param wage 時給
     * @param workTimeByMinute 労働時間(分単位)
     */
    public WageCalculator(BigDecimal wage, BigDecimal workTimeByMinute) {
        this.wage = wage;
        this.workTimeByMinute = workTimeByMinute;
    }

    /**
     * 日給を計算する
     *
     * @return dailyWage
     */
    public BigDecimal getDailyWage() {

        BigDecimal workTimeByHour = workTimeByMinute.divide(new BigDecimal(ONE_HOUR_BY_MINUTE), 0, RoundingMode.DOWN);
        BigDecimal dailyWage = wage.multiply(workTimeByHour);
        dailyWage = dailyWage.setScale(0, RoundingMode.DOWN);

        return dailyWage;
    }
}
