package com.jayant.payment_service.controller.dto.response;

public class PaymentResponse {

    private Long id;
    private String currency;
    private String status;

    public PaymentResponse(Long id, String currency, String status) {
        this.id = id;
        this.currency = currency;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
}
