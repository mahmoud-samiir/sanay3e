package com.twintech.sanay3e.entity;

import com.twintech.sanay3e.entity.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents system notifications sent to users")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    // Polymorphic reference: which entity triggered this notification
    @Column(length = 50)
    private String refType; // "ORDER", "SUBSCRIPTION", "REVIEW", etc.

    @Column
    private Long refId; // the ID of that entity

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


}
