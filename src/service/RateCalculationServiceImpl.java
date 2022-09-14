package service;

import model.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService {

    private final TimePointService timePointService;
    private final AmountCalculationService amountCalculationService;
    private final ResidualCalculationService residualCalculationService;

    public RateCalculationServiceImpl(final TimePointService timePointService,
                                      final AmountCalculationService amountCalculationService,
                                      final ResidualCalculationService residualCalculationService) {
        this.timePointService = timePointService;
        this.amountCalculationService = amountCalculationService;
        this.residualCalculationService = residualCalculationService;
    }

    @Override
    public List<Rate> calculate(final InputData inputData) {
        List<Rate> rates = new LinkedList<>();
        BigDecimal rateNumber = BigDecimal.ONE;

        Rate firstRate = calculateRate(rateNumber, inputData);
        rates.add(firstRate);

        Rate previousRate = firstRate;

        for (BigDecimal i = rateNumber.add(BigDecimal.ONE); i.compareTo(inputData.getMonthsDuration()) <= 0; i = i.add(BigDecimal.ONE)) {
            Rate nextRate = calculateRate(i, inputData, previousRate);
            rates.add(nextRate);
            previousRate = nextRate;
        }
        return rates;
    }

    private Rate calculateRate(final BigDecimal rateNumber, final InputData inputData) {
        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountCalculationService.calculate(inputData);
        MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts, inputData);

        return new Rate(rateNumber, timePoint, mortgageResidual, rateAmounts);
    }

    private Rate calculateRate(final BigDecimal rateNumber, final InputData inputData, final Rate previousRate) {
        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountCalculationService.calculate(inputData, previousRate);
        MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts, previousRate);

        return new Rate(rateNumber, timePoint, mortgageResidual, rateAmounts);
    }
}
