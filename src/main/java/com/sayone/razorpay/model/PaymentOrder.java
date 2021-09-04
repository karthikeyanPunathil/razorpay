package com.sayone.razorpay.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "payment_order")
@Data
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "razorpay_signature")
    private String razorpaySignature;

}
