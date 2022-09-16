package service;

import model.InputData;
import model.MortgageReference;
import model.Rate;

interface ReferenceCalculationService {

    MortgageReference calculate(InputData inputData);
}
