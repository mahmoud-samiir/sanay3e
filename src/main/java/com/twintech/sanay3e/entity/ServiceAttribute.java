package com.twintech.sanay3e.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_attributes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents dynamic attributes for a service")
public class ServiceAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    // e.g. key="warranty", value="6 months"
    // e.g. key="tools_required", value="drill, screwdriver"
    @Column(nullable = false, length = 100)
    private String key;

    @Column(nullable = false, length = 255)
    private String value;
}
