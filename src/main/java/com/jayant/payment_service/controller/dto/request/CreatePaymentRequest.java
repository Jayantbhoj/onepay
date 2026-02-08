package com.jayant.payment_service.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreatePaymentRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private String currency;


    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
