package com.twintech.sanay3e.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents subscription plans available in the system")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; // e.g. "Basic", "Pro", "Premium"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer durationDays; // 30 = monthly, 365 = yearly

    @Column(nullable = false)
    @Builder.Default
    private Integer maxOrders = -1; // -1 = unlimited

    // JSON array of feature strings: ["priority_support", "free_inspection", ...]
    @Column(columnDefinition = "JSON")
    private String features;


    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

}
