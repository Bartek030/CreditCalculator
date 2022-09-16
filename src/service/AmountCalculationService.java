package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;

interface AmountCalculationService {
    RateAmounts calculate(final InputData inputData, final Overpayment overpayment);

    RateAmounts calculate(final InputData inputData, final Overpayment overpayment, final Rate previousRate);
}
