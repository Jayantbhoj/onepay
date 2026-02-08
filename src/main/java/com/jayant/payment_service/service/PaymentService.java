package com.jayant.payment_service.service;

import com.jayant.payment_service.controller.dto.request.CreatePaymentRequest;
import com.jayant.payment_service.controller.dto.response.CreatePaymentResponse;
import com.jayant.payment_service.controller.dto.response.PaymentDetailResponse;
import com.jayant.payment_service.controller.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    CreatePaymentResponse create(
            CreatePaymentRequest request,
            String idempotencyKey
    );

    List<PaymentResponse> getAll();

    PaymentDetailResponse getById(Long id);

}
