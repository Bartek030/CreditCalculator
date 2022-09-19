package service;

import model.InputData;
import model.Overpayment;

import java.math.BigDecimal;

interface OverpaymentCalculationService {

    Overpayment calculate(BigDecimal rateNumber, InputData inputData );
}
