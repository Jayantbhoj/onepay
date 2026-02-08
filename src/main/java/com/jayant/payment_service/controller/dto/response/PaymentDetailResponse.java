package com.jayant.payment_service.controller.dto.response;

import java.math.BigDecimal;

public class PaymentDetailResponse extends PaymentResponse {

    private BigDecimal amount;

    public PaymentDetailResponse(
            Long id,
            String currency,
            String status,
            BigDecimal amount
    ) {
        super(id, currency, status);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
