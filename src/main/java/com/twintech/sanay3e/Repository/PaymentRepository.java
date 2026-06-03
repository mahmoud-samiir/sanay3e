package com.twintech.sanay3e.Repository;

import com.twintech.sanay3e.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
