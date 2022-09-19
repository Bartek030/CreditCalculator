package service;

import model.InputData;
import model.TimePoint;

import java.math.BigDecimal;

interface TimePointService {
    TimePoint calculate(BigDecimal rateNumber, InputData inputData);
}
