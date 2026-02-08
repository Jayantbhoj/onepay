package com.jayant.payment_service.service.impl;


import com.jayant.payment_service.controller.dto.request.CreatePaymentRequest;
import com.jayant.payment_service.controller.dto.response.CreatePaymentResponse;
import com.jayant.payment_service.controller.dto.response.PaymentDetailResponse;
import com.jayant.payment_service.controller.dto.response.PaymentResponse;
import com.jayant.payment_service.entity.IdempotencyKey;
import com.jayant.payment_service.entity.Payment;
import com.jayant.payment_service.enums.PaymentStatus;
import com.jayant.payment_service.repository.IdempotencyKeyRepository;
import com.jayant.payment_service.repository.PaymentRepository;
import com.jayant.payment_service.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
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

        Optional<IdempotencyKey> existing =
                idempotencyKeyRepository.findByKey(idempotencyKey);

        if (existing.isPresent()) {
            Payment payment = paymentRepository
                    .findById(existing.get().getPaymentId())
                    .orElseThrow();

            return new CreatePaymentResponse(
                    payment.getId(),
                    payment.getStatus().name()
            );
        }

        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus(PaymentStatus.INITIATED);
        payment = paymentRepository.save(payment);

        IdempotencyKey key = new IdempotencyKey();
        key.setKey(idempotencyKey);
        key.setPaymentId(payment.getId());
        key.setResponseSnapshot("created");
        idempotencyKeyRepository.save(key);

        // simulate lifecycle
        payment.setStatus(PaymentStatus.PROCESSING);
        paymentRepository.saveAndFlush(payment);

        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.saveAndFlush(payment);

        return new CreatePaymentResponse(
                payment.getId(),
                payment.getStatus().name()
        );
    }

    @Override
    public List<PaymentResponse> getAll() {
        return paymentRepository.findAll()
                .stream()
                .map(p -> new PaymentResponse(
                        p.getId(),
                        p.getCurrency(),
                        p.getStatus().name()
                ))
                .toList();
    }

    @Override
    public PaymentDetailResponse getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        return new PaymentDetailResponse(
                payment.getId(),
                payment.getCurrency(),
                payment.getStatus().name(),
                payment.getAmount()
        );
    }

    @Override
    public void deleteById(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        paymentRepository.deleteAll();
    }



}
