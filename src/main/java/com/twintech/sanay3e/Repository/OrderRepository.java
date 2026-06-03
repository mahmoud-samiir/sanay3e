package com.twintech.sanay3e.Repository;

import com.twintech.sanay3e.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
