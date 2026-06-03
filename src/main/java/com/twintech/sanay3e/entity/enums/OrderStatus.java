package com.twintech.sanay3e.entity.enums;

public enum OrderStatus {
    PENDING,         // waiting for provider to accept
    ACCEPTED,        // provider accepted
    ON_THE_WAY,      // provider heading to client
    IN_PROGRESS,     // work started
    COMPLETED,       // work done
    CANCELLED,       // cancelled by client or provider
    REJECTED         // provider rejected
}
