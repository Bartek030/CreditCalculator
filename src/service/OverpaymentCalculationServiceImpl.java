package service;

import model.InputData;
import model.Overpayment;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class OverpaymentCalculationServiceImpl implements OverpaymentCalculationService {

    @Override
    public Overpayment calculate(final BigDecimal rateNumber, final InputData inputData) {
        BigDecimal overpaymentAmount = calculateAmount(rateNumber, inputData.getOverpaymentSchema())
                .orElse(BigDecimal.ZERO);
        BigDecimal overpaymentProvision = calculateProvision(rateNumber, overpaymentAmount, inputData);

        return new Overpayment(overpaymentAmount, overpaymentProvision);
    }

    private Optional<BigDecimal> calculateAmount(final BigDecimal rateNumber, final Map<Integer, BigDecimal> overpaymentSchema) {
        for (Map.Entry<Integer, BigDecimal> entry : overpaymentSchema.entrySet()) {
            if (rateNumber.equals(BigDecimal.valueOf(entry.getKey()))) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    private BigDecimal calculateProvision(final BigDecimal rateNumber, final BigDecimal overpaymentAmount, final InputData inputData) {
        if (BigDecimal.ZERO.equals(overpaymentAmount)) {
            return BigDecimal.ZERO;
        }

        if (rateNumber.compareTo(inputData.getOverpaymentProvisionMonths()) > 0) {
            return BigDecimal.ZERO;
        }

        return overpaymentAmount.multiply(inputData.getOverpaymentProvisionPercent());
    }
}
