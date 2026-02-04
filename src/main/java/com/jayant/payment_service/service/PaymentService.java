package com.jayant.payment_service.service;

import com.jayant.payment_service.controller.dto.request.CreatePaymentRequest;
import com.jayant.payment_service.controller.dto.response.CreatePaymentResponse;

public interface PaymentService {

    CreatePaymentResponse create(
            CreatePaymentRequest request,
            String idempotencyKey
    );

}
