package com.jayant.payment_service.controller;

import com.jayant.payment_service.controller.dto.request.CreatePaymentRequest;
import com.jayant.payment_service.controller.dto.response.CreatePaymentResponse;
import com.jayant.payment_service.controller.dto.response.PaymentDetailResponse;
import com.jayant.payment_service.controller.dto.response.PaymentResponse;
import com.jayant.payment_service.entity.Payment;
import com.jayant.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public CreatePaymentResponse create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        return paymentService.create(request, idempotencyKey);
    }

    @GetMapping()
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAll();
    }

    @GetMapping("/{id}")
    public PaymentDetailResponse getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaymentById(@PathVariable Long id) {
        paymentService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllPayments() {
        paymentService.deleteAll();
    }


}
