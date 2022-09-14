package model;

import java.math.BigDecimal;

public class RateAmounts {

    private final BigDecimal rateAmount;
    private final BigDecimal interestAmount;
    private final BigDecimal capitalAmount;


    RateAmounts(final BigDecimal rateAmount, final BigDecimal interestAmount, final BigDecimal capitalAmount) {
        this.rateAmount = rateAmount;
        this.interestAmount = interestAmount;
        this.capitalAmount = capitalAmount;
    }

    BigDecimal getRateAmount() {
        return rateAmount;
    }

    BigDecimal getInterestAmount() {
        return interestAmount;
    }

    BigDecimal getCapitalAmount() {
        return capitalAmount;
    }
}
