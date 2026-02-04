package com.jayant.payment_service.service.impl;


import com.jayant.payment_service.controller.dto.request.CreatePaymentRequest;
import com.jayant.payment_service.controller.dto.response.CreatePaymentResponse;
import com.jayant.payment_service.entity.IdempotencyKey;
import com.jayant.payment_service.entity.Payment;
import com.jayant.payment_service.enums.PaymentStatus;
import com.jayant.payment_service.repository.IdempotencyKeyRepository;
import com.jayant.payment_service.repository.PaymentRepository;
import com.jayant.payment_service.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
// this class has two private deps
    private final PaymentRepository paymentRepository;
    private final IdempotencyKeyRepository idempotencyKeyRepository;

    //DI of those two using constructor
    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            IdempotencyKeyRepository idempotencyKeyRepository
    ) {
// setting or assigning priv deps values
        this.paymentRepository = paymentRepository;
        this.idempotencyKeyRepository = idempotencyKeyRepository;
    }

    @Override
    @Transactional
    public CreatePaymentResponse create(
            CreatePaymentRequest request,
            String idempotencyKey
    ) {

        // 1. Check idempotency key
        Optional<IdempotencyKey> existing =
                idempotencyKeyRepository.findByKey(idempotencyKey);

        if (existing.isPresent()) {
            throw new IllegalStateException("Duplicate idempotency key");
        }

        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus(PaymentStatus.INITIATED);

        payment = paymentRepository.save(payment);

        // 3️⃣ Store idempotency key (THIS WAS MISSING)
        IdempotencyKey key = new IdempotencyKey();
        key.setKey(idempotencyKey);
        key.setPaymentId(payment.getId());
        key.setResponseSnapshot(
                "paymentId=" + payment.getId() +
                        ",status=" + payment.getStatus()
        );

        idempotencyKeyRepository.save(key);

        // 4️⃣ Return response
        return new CreatePaymentResponse(
                payment.getId(),
                payment.getStatus().name()
        );
    }

}
