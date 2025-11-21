package com.example.asset_qrgenerator_service.entity;

import com.example.asset_qrgenerator_service.enums.AssetStatus;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset_seq")
    @SequenceGenerator(name = "asset_seq", sequenceName = "asset_sequence", allocationSize = 1)
    private Long assetId;

    @Column(nullable = false, length = 255)
    private String modelName;

    @Column(nullable = false, length = 255, unique = true)
    private String serialNum;

    private LocalDate manufactureDate;

    private LocalDate purchaseDate;

    private LocalDate expiryDate;

    private Double cost;

    @Column(length = 50)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AssetStatus status = AssetStatus.ACTIVE;

    @Lob
    private byte[] qrCode;
}
