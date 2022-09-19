package service;

import model.Rate;

import java.math.BigDecimal;

interface Function {

    BigDecimal calculate(Rate rate);
}
