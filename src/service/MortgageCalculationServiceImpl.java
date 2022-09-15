package service;

import model.InputData;
import model.Rate;
import model.Summary;

import java.util.List;

public class MortgageCalculationServiceImpl implements MortgageCalculationService {

    private final PrintingService printingService;
    private final RateCalculationService rateCalculationService;
    private final SummaryService summaryService;

    public MortgageCalculationServiceImpl(
            final PrintingService printingService,
            final RateCalculationService rateCalculationService,
            final SummaryService summaryService) {
        this.printingService = printingService;
        this.rateCalculationService = rateCalculationService;
        this.summaryService = summaryService;
    }

    @Override
    public void calculate(final InputData inputData) {
        printingService.printInputDataInfo(inputData);
        final List<Rate> rates = rateCalculationService.calculate(inputData);
        final Summary summary = summaryService.calculate(rates);
        printingService.printSummary(summary);
        printingService.printRates(rates);
    }
}
