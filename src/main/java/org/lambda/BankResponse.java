package org.lambda;

import java.math.BigDecimal;

public class BankResponse {
    private BigDecimal quota;   // Cuánto le valdrá la cuota
    private BigDecimal rate;    // De cuanto será el interés
    private Integer term;       // Cuántos meses se le darán para pagar

    /*
        Los 3 atributos siguientes corresponden a los mismos datos de arriba pero en este caso
        se aplicarán descuentos si la persona que solicita, tiene ya una cuenta bancaria
        en el banco donde está solicitando el préstamo, entonces, también deben calcularse
    */
    private BigDecimal quotaWithAcc;
    private BigDecimal rateWithAcc;
    private Integer termWithAcc;


    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getQuotaWithAcc() {
        return quotaWithAcc;
    }

    public void setQuotaWithAcc(BigDecimal quotaWithAcc) {
        this.quotaWithAcc = quotaWithAcc;
    }

    public BigDecimal getRateWithAcc() {
        return rateWithAcc;
    }

    public void setRateWithAcc(BigDecimal rateWithAcc) {
        this.rateWithAcc = rateWithAcc;
    }

    public Integer getTermWithAcc() {
        return termWithAcc;
    }

    public void setTermWithAcc(Integer termWithAcc) {
        this.termWithAcc = termWithAcc;
    }
}
