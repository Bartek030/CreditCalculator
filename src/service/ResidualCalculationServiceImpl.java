package service;

import model.InputData;
import model.MortgageResidual;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;

public class ResidualCalculationServiceImpl implements ResidualCalculationService {

    @Override
    public MortgageResidual calculate(final RateAmounts rateAmounts, final InputData inputData) {
        final BigDecimal residualAmount = inputData.getAmount().subtract(rateAmounts.getCapitalAmount());
        final BigDecimal residualDuration = inputData.getMonthsDuration().subtract(BigDecimal.ONE);

        return new MortgageResidual(residualAmount, residualDuration);
    }

    @Override
    public MortgageResidual calculate(final RateAmounts rateAmounts, final Rate previousRate) {
        final BigDecimal previousDuration = previousRate.getMortgageResidual().getDuration();
        final BigDecimal previousResidual = previousRate.getMortgageResidual().getAmount();

        final BigDecimal residualAmount = previousResidual.subtract(rateAmounts.getCapitalAmount());
        final BigDecimal residualDuration = previousDuration.subtract(BigDecimal.ONE);

        return new MortgageResidual(residualAmount, residualDuration);
    }
}
