import model.InputData;
import model.RateType;
import service.*;

import javax.security.sasl.SaslServerFactory;
import java.math.BigDecimal;

class Main {

    public static void main(String[] args) {

        InputData inputData = new InputData()
                .withAmount(new BigDecimal("298000"))
                .withRateType(RateType.CONSTANT);

        PrintingService printingService = new PrintingServiceImpl();
        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountCalculationServiceImpl(),
                new ResidualCalculationServiceImpl()
        );

        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                SummaryServiceFactory.create()
        );
        mortgageCalculationService.calculate(inputData);
    }
}
