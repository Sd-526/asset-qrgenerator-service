package com.example.asset_qrgenerator_service.service;



import com.example.asset_qrgenerator_service.dto.AssetRequestDto;
import com.example.asset_qrgenerator_service.dto.AssetResponseDto;
import com.example.asset_qrgenerator_service.entity.Asset;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface AssetService {

    public AssetResponseDto createAsset(AssetRequestDto dto);

    public Asset generateAssetQR(Long id) throws WriterException, IOException;

    public AssetResponseDto getAsset(Long id);
}
