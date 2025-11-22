package com.example.asset_qrgenerator_service.controller;

import com.example.asset_qrgenerator_service.dto.AssetRequestDto;
import com.example.asset_qrgenerator_service.dto.AssetResponseDto;
import com.example.asset_qrgenerator_service.entity.Asset;
import com.example.asset_qrgenerator_service.exception.BadRequestException;
import com.example.asset_qrgenerator_service.repository.AssetRepository;
import com.example.asset_qrgenerator_service.service.AssetServiceImpl;
import com.example.asset_qrgenerator_service.util.ApiErrorResponse;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    private final AssetServiceImpl service;
    private final AssetRepository repository;

    public AssetController(AssetRepository repository, AssetServiceImpl service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiErrorResponse<AssetResponseDto>> add(
            @Valid @RequestBody AssetRequestDto dto) {

        AssetResponseDto savedAsset = service.createAsset(dto);

        ApiErrorResponse<AssetResponseDto> response = new ApiErrorResponse<>();
        response.setStatuscode(HttpStatus.CREATED.value());
        response.setMessage("Asset created successfully");
        response.setData(savedAsset);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping(value = "/generateQR/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQR(@PathVariable Long id)
            throws WriterException, IOException {

        Asset assetqr = service.generateAssetQR(id);

        Optional.ofNullable(assetqr.getQrCode())
                .orElseThrow(() ->
                        new BadRequestException("Failed to generate QR Code for asset ID: " + id)
                );


        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header("Asset-ID", String.valueOf(assetqr.getAssetId()))
                .header("Message", "QR Code generated successfully")
                .body(assetqr.getQrCode());
    }
}
