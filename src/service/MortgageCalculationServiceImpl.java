package service;

import model.InputData;
import model.Rate;

import java.util.List;

public class MortgageCalculationServiceImpl implements MortgageCalculationService {

    private final PrintingService printingService;
    private final RateCalculationService rateCalculationService;

    public MortgageCalculationServiceImpl(
            final PrintingService printingService,
            final RateCalculationService rateCalculationService
    ) {
        this.printingService = printingService;
        this.rateCalculationService = rateCalculationService;
    }

    @Override
    public void calculate(final InputData inputData) {
        printingService.printInputDataInfo(inputData);
        final List<Rate> calculate = rateCalculationService.calculate(inputData);
    }
}
