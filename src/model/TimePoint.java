package model;

import java.math.BigDecimal;
import java.time.LocalDate;

class TimePoint {

    private final LocalDate date;
    private final BigDecimal year;
    private final BigDecimal month;

    TimePoint(final LocalDate date, final BigDecimal year, final BigDecimal month) {
        this.date = date;
        this.year = year;
        this.month = month;
    }

    LocalDate getDate() {
        return date;
    }

    BigDecimal getYear() {
        return year;
    }

    BigDecimal getMonth() {
        return month;
    }
}
