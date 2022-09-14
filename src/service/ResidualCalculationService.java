package service;

import model.InputData;
import model.MortgageResidual;
import model.Rate;
import model.RateAmounts;

interface ResidualCalculationService {
    MortgageResidual calculate(final RateAmounts rateAmounts, final InputData inputData);
    MortgageResidual calculate(final RateAmounts rateAmounts, final Rate previousRate);
}
