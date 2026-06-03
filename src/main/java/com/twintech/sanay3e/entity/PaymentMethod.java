package com.twintech.sanay3e.entity;

import com.twintech.sanay3e.entity.enums.CardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents user payment methods such as card or wallet")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardType type; // VISA, MASTERCARD, MEEZA, WALLET

    // Last 4 digits only — never store full card number
    @Column(length = 4)
    private String last4;

    // Tokenized card reference from payment gateway (e.g. Paymob token)
    @Column(length = 255)
    private String gatewayToken;

    @Column(length = 100)
    private String gatewayName;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


}
