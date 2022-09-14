package model;

import java.math.BigDecimal;

public class MortgageResidual {

    private final BigDecimal amount;
    private final BigDecimal duration;

    MortgageResidual(final BigDecimal amount, final BigDecimal duration) {
        this.amount = amount;
        this.duration = duration;
    }

    BigDecimal getAmount() {
        return amount;
    }

    BigDecimal getDuration() {
        return duration;
    }
}
