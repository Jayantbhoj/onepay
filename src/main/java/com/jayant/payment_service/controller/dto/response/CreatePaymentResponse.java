package com.jayant.payment_service.controller.dto.response;

public class CreatePaymentResponse {

    private final Long paymentId;
    private final String status;

    public CreatePaymentResponse(Long paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }
}
