package com.twintech.sanay3e.entity;

import com.twintech.sanay3e.entity.enums.VerificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "provider_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Provider profile entity representing service providers in the system")
public class ProviderProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @Schema(description = "Required link to the User entity. Every provider profile must belong to exactly one user.")
    private User user;

    @Column(length = 20)
    private String nationalId;

    @Column(length = 255)
    private String avatarUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false)
    @Builder.Default
    private Double rating = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalReviews = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer completedOrders = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(length = 255)
    private String nationalIdFrontUrl;

    @Column(length = 255)
    private String nationalIdBackUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime joinedAt;

}
