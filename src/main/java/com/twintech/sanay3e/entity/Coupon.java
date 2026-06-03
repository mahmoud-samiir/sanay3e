package com.twintech.sanay3e.entity;

import com.twintech.sanay3e.entity.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType; // PERCENTAGE or FIXED

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    // For PERCENTAGE: cap the max discount amount
    @Column(precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount;

    // Minimum order value to apply the coupon
    @Column(precision = 10, scale = 2)
    private BigDecimal minOrderAmount;

    @Column(nullable = false)
    private LocalDate expiresAt;

    // Max number of times this coupon can be used globally
    @Column
    private Integer maxUses;

    // How many times it's been used so far
    @Column(nullable = false)
    @Builder.Default
    private Integer usedCount = 0;

    // Max times a single user can use this coupon
    @Column(nullable = false)
    @Builder.Default
    private Integer maxUsesPerUser = 1;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


}
