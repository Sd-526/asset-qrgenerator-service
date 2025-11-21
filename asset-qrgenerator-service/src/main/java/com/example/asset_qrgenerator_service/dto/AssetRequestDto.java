package com.example.asset_qrgenerator_service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetRequestDto {
    @NotBlank(message = "Model name is required")
    private String modelName;

    @NotBlank(message = "Serial name is required")
    private String serialNum;

    @PastOrPresent(message = "Manufacture date cannot be a future date")
    private LocalDate manufactureDate;

    @PastOrPresent(message = "Purchase date cannot be a future date")
    private LocalDate purchaseDate;

    @FutureOrPresent(message = "Expiry date must be today or a future date")
    private LocalDate expiryDate;

    @Positive(message = "Cost must be a positive value")
    private Double cost;

    private String type;

}
