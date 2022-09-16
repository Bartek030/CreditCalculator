package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountCalculationServiceImpl implements AmountCalculationService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment) {
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData, overpayment);
            case DECREASING:
                return calculateDecreasingRate(inputData, overpayment);
            default:
                throw new MortgageException();
        }
    }

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment, final Rate previousRate) {
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData, overpayment, previousRate);
            case DECREASING:
                return calculateDecreasingRate(inputData, overpayment, previousRate);
            default:
                throw new MortgageException();
        }
    }

    private RateAmounts calculateConstantRate(final InputData inputData, final Overpayment overpayment) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = inputData.getAmount();

        final BigDecimal q = calculateQ(interestPercent);

        final BigDecimal rateAmount = calculateConstantRateAmount(q, residualAmount, inputData.getMonthsDuration());
        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private RateAmounts calculateConstantRate(final InputData inputData, final Overpayment overpayment, final Rate previousRate) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

        final BigDecimal q = calculateQ(interestPercent);

        final BigDecimal rateAmount = calculateConstantRateAmount(q, inputData.getAmount(), inputData.getMonthsDuration());
        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private RateAmounts calculateDecreasingRate(final InputData inputData, final Overpayment overpayment) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = inputData.getAmount();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateDecreasingCapitalAmount(residualAmount, inputData.getMonthsDuration());
        final BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private RateAmounts calculateDecreasingRate(final InputData inputData, final Overpayment overpayment, final Rate previousRate) {
        final BigDecimal interestPercent = inputData.getInterestPercent();
        final BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

        final BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        final BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), inputData.getMonthsDuration());
        final BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateQ(final BigDecimal interestPercent) {
        return interestPercent.divide(MONTHS_IN_YEAR, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    private BigDecimal calculateConstantRateAmount(final BigDecimal q, final BigDecimal amount, final BigDecimal monthsDuration) {
        return amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE), 50, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateInterestAmount(final BigDecimal residualAmount, final BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(MONTHS_IN_YEAR, 50, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateConstantCapitalAmount(final BigDecimal rateAmount, final BigDecimal interestAmount) {
        return rateAmount.subtract(interestAmount);
    }

    private BigDecimal calculateDecreasingCapitalAmount(final BigDecimal amount, final BigDecimal monthsDuration) {
        return amount.divide(monthsDuration, 50, RoundingMode.HALF_UP);
    }
}
