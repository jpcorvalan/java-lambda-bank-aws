package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Fórmula para aplicar
 * P = Monto del préstamo (está en el Request [amount])
 * i = Tasa de interés mensual (está en el Request [rate])
 * n = Plazo del crédito en meses (está en el Request [term])
 * <p>
 * Cuota mensual = (P * i) / (1 - (1 + i) ^ (-n))
 * Se restan 0.2% de interés si el solicitante tiene cuenta bancaria en la entidad
 */

public class LambdaBank implements RequestHandler<BankRequest, BankResponse> {
    @Override
    public BankResponse handleRequest(BankRequest bankRequest, Context context) {

        // Definimos la precisión de los cálculos
        MathContext mathContext = MathContext.DECIMAL128;

        // Definimos la cantidad de decimales (2), y el método de redondeo (más de 5 hacia arriba, menos de 5 hacia abajo)
        BigDecimal amount = bankRequest.getAmount()
                                        .setScale(2, RoundingMode.HALF_UP);

        // divide() -> El porcentaje se divide por 100 para poder operar
        BigDecimal monthlyRate = bankRequest.getRate()
                                            .setScale(2, RoundingMode.HALF_UP)
                                            .divide(BigDecimal.valueOf(100), mathContext);

        BigDecimal monthlyRateWithAcc = bankRequest.getRate()
                                                    .subtract(BigDecimal.valueOf(0.2), mathContext)
                                                    .setScale(2, RoundingMode.HALF_UP)
                                                    .divide(BigDecimal.valueOf(100), mathContext);

        Integer term = bankRequest.getTerm();

        BigDecimal monthlyPayment = this.calculateQuotaAmount(amount, monthlyRate, term, mathContext);
        BigDecimal monthlyPaymentWithAcc = this.calculateQuotaAmount(amount, monthlyRateWithAcc, term, mathContext);

        BankResponse bankResponse = new BankResponse();

        bankResponse.setQuota(monthlyPayment);
        bankResponse.setRate(monthlyRate);
        bankResponse.setTerm(term);
        bankResponse.setQuotaWithAcc(monthlyPaymentWithAcc);
        bankResponse.setRateWithAcc(monthlyRateWithAcc);
        bankResponse.setTermWithAcc(term);

        return bankResponse;
    }

    /*
        Función para calcular el monto de las cuotas
         * P = Monto del préstamo (está en el Request [amount])
         * i = Tasa de interés mensual (está en el Request [rate])
         * n = Plazo del crédito en meses (está en el Request [term])
         *
         * Cuota mensual = (P * i) / (1 - (1 + i) ^ (-n))
         * Se restan 0.2% de interés si el solicitante tiene cuenta bancaria en la entidad
     */
    public BigDecimal calculateQuotaAmount(BigDecimal amount, BigDecimal monthlyRate, Integer term, MathContext mathContext) {

        // (1 + i)
        BigDecimal onePlusRate = monthlyRate.add(BigDecimal.ONE, mathContext);

        // (1 + i) ^ n
        BigDecimal onePlusRateToN = onePlusRate.pow(term, mathContext);

        // (1 + i) ^ -n (Inverso multiplicativo)
        BigDecimal onePlusRateToNegativeN = BigDecimal.ONE.divide(onePlusRateToN, mathContext);

        // (1 - (1 + i) ^ (-n)
        BigDecimal subtractOneFromPow = BigDecimal.ONE.subtract(onePlusRateToNegativeN, mathContext);

        // (P * i)
        BigDecimal finalRate = amount.multiply(monthlyRate, mathContext);

        // (P * i) / (1 - (1 + i) ^ (-n))
        BigDecimal finalOperation = finalRate.divide(subtractOneFromPow, mathContext);

        finalOperation = finalOperation.setScale(2, RoundingMode.HALF_UP);

        return finalOperation;
    }
}