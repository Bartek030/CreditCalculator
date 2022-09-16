package model;

import java.math.BigDecimal;

public class MortgageReference {

    private final BigDecimal referenceAmount;
    private final BigDecimal referenceDuration;

    public MortgageReference(final BigDecimal referenceAmount, final BigDecimal referenceDuration) {
        this.referenceAmount = referenceAmount;
        this.referenceDuration = referenceDuration;
    }

    BigDecimal getReferenceAmount() {
        return referenceAmount;
    }

    BigDecimal getReferenceDuration() {
        return referenceDuration;
    }
}
