package com.sayone.razorpay.repository;

import com.sayone.razorpay.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentOrder, Long> {

}
