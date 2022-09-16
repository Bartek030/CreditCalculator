package model;

import java.math.BigDecimal;

public class Rate {

    private final BigDecimal rateNumber;
    private final TimePoint timePoint;
    private final MortgageResidual mortgageResidual;
    private final RateAmounts rateAmounts;
    private final MortgageReference mortgageReference;

    public Rate(
            final BigDecimal rateNumber,
            final TimePoint timePoint,
            final MortgageResidual mortgageResidual,
            final RateAmounts rateAmounts,
            final MortgageReference mortgageReference
    ) {
        this.rateNumber = rateNumber;
        this.timePoint = timePoint;
        this.mortgageResidual = mortgageResidual;
        this.rateAmounts = rateAmounts;
        this.mortgageReference = mortgageReference;
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
