package org.lambda;

import java.math.BigDecimal;

public class BankRequest {
    private BigDecimal amount;  // Cantidad del préstamo
    private Integer term;       // En cuantas cuotas se paga
    private BigDecimal rate;    // La tasa de interés acordada (porcentaje, por ejemplo: 1.3%, 2.4%, etc)

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
