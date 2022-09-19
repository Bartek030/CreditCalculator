package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecreasingAmountsCalculationServiceImpl implements DecreasingAmountsCalculationService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = inputData.getAmount();
        final BigDecimal referenceAmount = inputData.getAmount();
        final BigDecimal referenceDuration = inputData.getMonthsDuration();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
        final BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment, final Rate previousRate) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();
        final BigDecimal referenceAmount = previousRate.getMortgageReference().getReferenceAmount();
        final BigDecimal referenceDuration = previousRate.getMortgageReference().getReferenceDuration();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
        final BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateInterestAmount(final BigDecimal residualAmount, final BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(MONTHS_IN_YEAR, 50, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateCapitalAmount(
            final BigDecimal amount,
            final BigDecimal monthsDuration,
            final BigDecimal residualAmount
    ) {
        final BigDecimal capitalAmount = amount.divide(monthsDuration, 50, RoundingMode.HALF_UP);
        if (capitalAmount.compareTo(residualAmount) >= 0) {
            return residualAmount;
        }
        return capitalAmount;
    }
}