package service;

import model.InputData;
import model.Rate;
import model.Summary;

import java.util.List;

public interface PrintingService {

    String INTEREST_SUM = "SUMA ODSETEK: ";
    String OVERPAYMENT_PROVISION = "PROWIZJA ZA NADPŁATY: ";
    String LOSTS_SUM = "SUMA STRAT: ";
    String OVERPAYMENT_REDUCE_RATE = "NADPŁATA, ZMNIEJSZENIE RATY";
    String OVERPAYMENT_REDUCE_PERIOD = "NADPŁATA, SKRÓCENIE OKRESU";
    String OVERPAYMENT_FREQUENCY = "SCHEMAT DOKONYWANIA NADPŁAT: ";
    String RATE_NUMBER = "NR: ";
    String YEAR = "ROK: ";
    String MONTH = "MIESIĄC: ";
    String DATE = "DATA: ";
    String MONTHS = " MIESIĘCY ";
    String RATE = "RATA: ";
    String INTEREST = "ODSETKI: ";
    String CAPITAL = "KAPITAŁ: ";
    String OVERPAYMENT = "NADPŁATA: ";
    String LEFT_AMOUNT = "PKWOTA: ";
    String LEFT_MONTHS = "PMSC: ";
    String MORTGAGE_AMOUNT = "KWOTA KREDYTU: ";
    String MORTGAGE_PERIOD = "OKRES KREDYTOWANIA: ";

    String CURRENCY = " ZL ";
    String NEW_LINE = "\n";
    String PERCENT = "%  ";

    void printInputDataInfo(final InputData inputData);

    void printRates(final List<Rate> rates);

    void printSummary(Summary summary);
}
