package model;

import java.math.BigDecimal;

public class Summary {

    private final BigDecimal interestSum;

    public Summary(final BigDecimal interestSum) {
        this.interestSum = interestSum;
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }


}
