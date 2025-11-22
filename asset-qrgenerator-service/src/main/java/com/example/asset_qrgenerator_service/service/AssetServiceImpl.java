package com.example.asset_qrgenerator_service.service;


import com.example.asset_qrgenerator_service.dto.AssetRequestDto;
import com.example.asset_qrgenerator_service.dto.AssetResponseDto;
import com.example.asset_qrgenerator_service.entity.Asset;
import com.example.asset_qrgenerator_service.exception.BadRequestException;
import com.example.asset_qrgenerator_service.exception.DuplicateSerialNumException;
import com.example.asset_qrgenerator_service.exception.ResourceNotFoundException;
import com.example.asset_qrgenerator_service.repository.AssetRepository;
import com.example.asset_qrgenerator_service.util.QRGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository repository;
    private final ObjectMapper mapper;

    public AssetServiceImpl(AssetRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AssetResponseDto createAsset(AssetRequestDto dto) {

        if (repository.existsBySerialNum(dto.getSerialNum())) {
            throw new DuplicateSerialNumException("Serial number already exists: " + dto.getSerialNum());
        }

        if (dto.getPurchaseDate().isBefore(dto.getManufactureDate())) {
            throw new BadRequestException("Purchase date cannot be before manufacture date");
        }

        if (dto.getExpiryDate() != null && dto.getExpiryDate().isBefore(dto.getPurchaseDate())) {
            throw new BadRequestException("Expiry date cannot be before purchase date");
        }

        Asset asset = mapper.convertValue(dto, Asset.class);

        Asset saved = repository.save(asset);

        return mapper.convertValue(saved, AssetResponseDto.class);
    }

    @Override
    public Asset generateAssetQR(Long id) throws WriterException, IOException {

        Asset asset = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset with ID " + id + " not found"));

        String qrText = "Asset ID: " + asset.getAssetId() +
                "\nModel Name: " + asset.getModelName() +
                "\nSerial Number: " + asset.getSerialNum() +
                "\nManufacture Date: " + asset.getManufactureDate() +
                "\nPurchase Date: " + asset.getPurchaseDate() +
                "\nExpiry Date: " + asset.getExpiryDate() +
                "\nCost: " + asset.getCost() +
                "\nType: " + asset.getType() +
                "\nStatus: " + asset.getStatus();

        asset.setQrCode(QRGenerator.generateQRCode(qrText, 300, 300));
        return repository.save(asset);
    }

    @Override
    public AssetResponseDto getAsset(Long id) {
        Asset asset = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset with ID: " + id + " not found"));
        return mapper.convertValue(asset, AssetResponseDto.class);
    }

}
