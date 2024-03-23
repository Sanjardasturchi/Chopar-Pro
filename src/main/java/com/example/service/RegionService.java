package com.example.service;

import com.example.dto.extra.region.CreateRegionDTO;
import com.example.entity.RegionEntity;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public void create(CreateRegionDTO dto) {
        RegionEntity entity=new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        regionRepository.save(entity);
    }
}
