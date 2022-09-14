package service;

import model.InputData;
import model.Rate;
import model.RateAmounts;

interface AmountCalculationService {
    RateAmounts calculate(final InputData inputData);

    RateAmounts calculate(InputData inputData, Rate previousRate);
}
