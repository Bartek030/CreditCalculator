package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RateAmounts {

    private final BigDecimal rateAmount;
    private final BigDecimal interestAmount;
    private final BigDecimal capitalAmount;
    private final Overpayment overpayment;

    public RateAmounts(final BigDecimal rateAmount,
                       final BigDecimal interestAmount,
                       final BigDecimal capitalAmount,
                       final Overpayment overpayment
    ) {
        this.rateAmount = rateAmount;
        this.interestAmount = interestAmount;
        this.capitalAmount = capitalAmount;
        this.overpayment = overpayment;
    }

    BigDecimal getRateAmount() {
        return rateAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getInterestAmount() {
        return interestAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCapitalAmount() {
        return capitalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public Overpayment getOverpayment() {
        return overpayment;
    }
}
