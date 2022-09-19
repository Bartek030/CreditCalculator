package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConstantAmountsCalculationServiceImpl implements ConstantAmountsCalculationService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal q = calculateQ(interestPercent);
        final BigDecimal residualAmount = inputData.getAmount();
        final BigDecimal referenceAmount = inputData.getAmount();
        final BigDecimal referenceDuration = inputData.getMonthsDuration();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal rateAmount = calculateConstantRateAmount(q, referenceAmount, referenceDuration, interestAmount, residualAmount);
        final BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment, final Rate previousRate) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal q = calculateQ(interestPercent);
        final BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();
        final BigDecimal referenceAmount = previousRate.getMortgageReference().getReferenceAmount();
        final BigDecimal referenceDuration = previousRate.getMortgageReference().getReferenceDuration();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal rateAmount = calculateConstantRateAmount(q, referenceAmount, referenceDuration, interestAmount, residualAmount);
        final BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateQ(final BigDecimal interestPercent) {
        return interestPercent.divide(MONTHS_IN_YEAR, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    private BigDecimal calculateConstantRateAmount(
            final BigDecimal q,
            final BigDecimal amount,
            final BigDecimal monthsDuration,
            final BigDecimal interestAmount,
            final BigDecimal residualAmount
    ) {
        final BigDecimal rateAmount = amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE), 50, RoundingMode.HALF_UP);
        return compareWithResidual(rateAmount, interestAmount, residualAmount);
    }

    private BigDecimal compareWithResidual(
            final BigDecimal rateAmount,
            final BigDecimal interestAmount,
            final BigDecimal residualAmount
    ) {
        if (rateAmount.subtract(interestAmount).compareTo(residualAmount) >= 0) {
            return residualAmount.add(interestAmount);
        }
        return rateAmount;
    }

    private BigDecimal calculateCapitalAmount(
            final BigDecimal rateAmount,
            final BigDecimal interestAmount,
            final BigDecimal residualAmount) {
        final BigDecimal capitalAmount = rateAmount.subtract(interestAmount);
        if (capitalAmount.compareTo(residualAmount) >= 0) {
            return residualAmount;
        }
        return capitalAmount;
    }

    private BigDecimal calculateInterestAmount(final BigDecimal residualAmount, final BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(MONTHS_IN_YEAR, 50, RoundingMode.HALF_UP);
    }
}
