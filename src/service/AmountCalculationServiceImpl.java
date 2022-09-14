package service;

import model.InputData;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountCalculationServiceImpl implements AmountCalculationService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    @Override
    public RateAmounts calculate(final InputData inputData) {
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData);
            case DECREASING:
                return calculateDecreasingRate(inputData);
            default:
                throw new RuntimeException("Case not handled");
        }
    }

    @Override
    public RateAmounts calculate(final InputData inputData, final Rate previousRate) {
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData, previousRate);
            case DECREASING:
                return calculateDecreasingRate(inputData, previousRate);
            default:
                throw new RuntimeException("Case not handled");
        }
    }

    private RateAmounts calculateConstantRate(final InputData inputData) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = inputData.getAmount();

        final BigDecimal q = calculateQ(interestPercent);

        final BigDecimal rateAmount = calculateConstantRateAmount(q, residualAmount, inputData.getMonthsDuration());
        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private RateAmounts calculateConstantRate(final InputData inputData, final Rate previousRate) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

        final BigDecimal q = calculateQ(interestPercent);

        final BigDecimal rateAmount = calculateConstantRateAmount(q, residualAmount, inputData.getMonthsDuration());
        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private RateAmounts calculateDecreasingRate(final InputData inputData) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = inputData.getAmount();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateDecreasingCapitalAmount(residualAmount, inputData.getMonthsDuration());
        final BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private RateAmounts calculateDecreasingRate(final InputData inputData, final Rate previousRate) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), inputData.getMonthsDuration());
        final BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private BigDecimal calculateQ(final BigDecimal interestPercent) {
        return interestPercent.divide(MONTHS_IN_YEAR, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    private BigDecimal calculateConstantRateAmount(final BigDecimal q, final BigDecimal amount, final BigDecimal monthsDuration) {
        return amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE),2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateInterestAmount(final BigDecimal residualAmount, final BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(MONTHS_IN_YEAR, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateConstantCapitalAmount(final BigDecimal rateAmount, final BigDecimal interestAmount) {
        return rateAmount.subtract(interestAmount);
    }

    private BigDecimal calculateDecreasingCapitalAmount(final BigDecimal amount, final BigDecimal monthsDuration) {
        return amount.divide(monthsDuration, 2, RoundingMode.HALF_UP);
    }
}
