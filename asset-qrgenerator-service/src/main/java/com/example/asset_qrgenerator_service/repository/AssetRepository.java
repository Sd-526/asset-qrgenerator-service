package com.example.asset_qrgenerator_service.repository;

import com.example.asset_qrgenerator_service.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    boolean existsBySerialNum(String serialNum);
}
