package service;

import model.InputData;
import model.TimePoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimePointServiceImpl implements TimePointService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    private static LocalDate calculateDate(final BigDecimal rateNumber, final InputData inputData) {
        return inputData.getRepaymentStartDate().plus(rateNumber.subtract(BigDecimal.ONE).intValue(), ChronoUnit.MONTHS);
    }

    @Override
    public TimePoint calculate(final BigDecimal rateNumber, InputData inputData) {
        final LocalDate date = calculateDate(rateNumber, inputData);
        final BigDecimal year = calculateYear(rateNumber);
        final BigDecimal month = calculateMonth(rateNumber);
        return new TimePoint(date, year, month);
    }

    private BigDecimal calculateYear(final BigDecimal rateNumber) {
        return rateNumber.divide(MONTHS_IN_YEAR, RoundingMode.UP).max(BigDecimal.ONE);
    }

    private BigDecimal calculateMonth(final BigDecimal rateNumber) {
        return BigDecimal.ZERO.equals(rateNumber.remainder(MONTHS_IN_YEAR)) ? MONTHS_IN_YEAR : rateNumber.remainder(MONTHS_IN_YEAR);
    }
}
