package com.jayant.payment_service.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "idempotency_keys",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idempotency_key")
        }
)
public class IdempotencyKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private Long paymentId;

    @Lob
    @Column(nullable = false)
    private String responseSnapshot;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public String getResponseSnapshot() {
        return responseSnapshot;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setResponseSnapshot(String responseSnapshot) {
        this.responseSnapshot = responseSnapshot;
    }

    public Long getPaymentId() {
        return paymentId;
    }

}
