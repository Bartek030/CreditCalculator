package service;

import model.Rate;
import model.Summary;

import java.util.List;

interface SummaryService {

    Summary calculate(List<Rate> rates);
}
