package service;

import model.InputData;
import model.Rate;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService {

    @Override
    public List<Rate> calculate(final InputData inputData) {
        List<Rate> rates = new LinkedList<>();
        BigDecimal rateNumber = BigDecimal.ONE;

        Rate firstRate = calculateFirstRate(rateNumber, inputData);
        rates.add(firstRate);

        Rate previousRate = firstRate;

        for (BigDecimal i = rateNumber.add(BigDecimal.ONE); i.compareTo(inputData.getMonthsDuration()) <= 0; i = i.add(BigDecimal.ONE)) {
            Rate nextRate = calcculateNextRate(i, inputData, previousRate);
            rates.add(nextRate);
            previousRate = nextRate;
        }
        return rates;
    }

    private Rate calculateFirstRate(final BigDecimal rateNumber, final InputData inputData) {
        return null;
    }

    private Rate calcculateNextRate(final BigDecimal i, final InputData inputData, final Rate previousRate) {
        return null;
    }
}
