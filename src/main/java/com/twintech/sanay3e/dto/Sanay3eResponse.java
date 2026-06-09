package com.twintech.sanay3e.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard API Response for Sanay3e Application")
public class Sanay3eResponse<T> {

    @Schema(description = "Request success status", example = "true")
    private boolean success;

    @Schema(description = "Response message", example = "Operation completed successfully")
    private String message;

    @Schema(description = "Response data - contains the actual response payload")
    private T data;

    @Schema(description = "Response timestamp", example = "2026-06-09T13:03:12.852Z")
    @Builder.Default
    private Instant timestamp = Instant.now();

    public static <T> Sanay3eResponse<T> success(T data) {
        return Sanay3eResponse.<T>builder().success(true).data(data).build();
    }

    public static <T> Sanay3eResponse<T> success(String message, T data) {
        return Sanay3eResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> Sanay3eResponse<T> error(String message) {
        return Sanay3eResponse.<T>builder().success(false).message(message).build();
    }

    public static <T> Sanay3eResponse<T> error(String message, T data) {
        return Sanay3eResponse.<T>builder().success(false).message(message).data(data).build();
    }
}
