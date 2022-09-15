package model;

import java.math.BigDecimal;

public class Rate {

    private final BigDecimal rateNumber;
    private final TimePoint timePoint;
    private final MortgageResidual mortgageResidual;
    private final RateAmounts rateAmounts;

    public Rate(final BigDecimal rateNumber, final TimePoint timePoint, final MortgageResidual mortgageResidual, final RateAmounts rateAmounts) {
        this.rateNumber = rateNumber;
        this.timePoint = timePoint;
        this.mortgageResidual = mortgageResidual;
        this.rateAmounts = rateAmounts;
    }

    public BigDecimal getRateNumber() {
        return rateNumber;
    }

    public TimePoint getTimePoint() {
        return timePoint;
    }

    public MortgageResidual getMortgageResidual() {
        return mortgageResidual;
    }

    public RateAmounts getRateAmounts() {
        return rateAmounts;
    }
}
