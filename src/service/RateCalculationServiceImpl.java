package service;

import model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService {

    private final TimePointService timePointService;
    private final AmountCalculationService amountCalculationService;
    private final OverpaymentCalculationService overpaymentCalculationService;
    private final ResidualCalculationService residualCalculationService;
    private final ReferenceCalculationService referenceCalculationService;

    public RateCalculationServiceImpl(
            final TimePointService timePointService,
            final AmountCalculationService amountCalculationService,
            final OverpaymentCalculationService overpaymentCalculationService,
            final ResidualCalculationService residualCalculationService,
            final ReferenceCalculationService referenceCalculationService
    ) {
        this.timePointService = timePointService;
        this.amountCalculationService = amountCalculationService;
        this.overpaymentCalculationService = overpaymentCalculationService;
        this.residualCalculationService = residualCalculationService;
        this.referenceCalculationService = referenceCalculationService;
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

            if (mortgageFinished(nextRate)) {
                break;
            }
        }
        return rates;
    }

    private static boolean mortgageFinished(final Rate nextRate) {
        final BigDecimal toCompare = nextRate.getMortgageResidual().getAmount().setScale(0, RoundingMode.HALF_UP);
        return BigDecimal.ZERO.equals(toCompare);
    }

    private Rate calculateRate(final BigDecimal rateNumber, final InputData inputData) {
        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        Overpayment overpayment = overpaymentCalculationService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountCalculationService.calculate(inputData, overpayment);
        MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts, inputData);
        MortgageReference mortgageReference = referenceCalculationService.calculate(inputData);

        return new Rate(rateNumber, timePoint, mortgageResidual, rateAmounts, mortgageReference);
    }

    private Rate calculateRate(final BigDecimal rateNumber, final InputData inputData, final Rate previousRate) {
        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        Overpayment overpayment = overpaymentCalculationService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountCalculationService.calculate(inputData, overpayment, previousRate);
        MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts, previousRate);
        MortgageReference mortgageReference = referenceCalculationService.calculate(inputData);

        return new Rate(rateNumber, timePoint, mortgageResidual, rateAmounts, mortgageReference);
    }
}
