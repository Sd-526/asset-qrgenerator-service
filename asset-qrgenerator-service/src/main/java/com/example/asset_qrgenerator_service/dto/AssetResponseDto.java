package com.example.asset_qrgenerator_service.dto;

import com.example.asset_qrgenerator_service.enums.AssetStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetResponseDto {
    private Long assetId;
    private String modelName;
    private String serialNum;
    private LocalDate manufactureDate;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private Double cost;
    private String type;
    private AssetStatus status;
}
