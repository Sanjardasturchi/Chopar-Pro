package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.RegionDTO;
import com.example.dto.extra.region.CreateRegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exp.AppBadException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    //=============== Service =================
    @Autowired
    private ResourceBundleService resourceBundleService;
    //=============== Repository ==============
    @Autowired
    private RegionRepository regionRepository;

    public void create(CreateRegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        regionRepository.save(entity);
    }

    public void update(Integer id, CreateRegionDTO regionDTO, Language language) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.found", language));
        }
        RegionEntity entity = optional.get();
        if (regionDTO.getOrderNumber() != null) {
            entity.setOrderNumber(regionDTO.getOrderNumber());
        }
        entity.setNameUz(regionDTO.getNameUz());
        entity.setNameRu(regionDTO.getNameRu());
        entity.setNameEn(regionDTO.getNameEn());
        regionRepository.save(entity);
    }

    public void delete(Integer id, Language language) {
        regionRepository.makeDelete(id);
    }

    public List<RegionDTO> getAll() {
        List<RegionDTO> dtoList=new LinkedList<>();
        for (RegionEntity entity : regionRepository.findAll()) {
            dtoList.add(toDTOFull(entity));
        }
        return dtoList;
    }

    private RegionDTO toDTOFull(RegionEntity entity) {
        RegionDTO dto=new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<RegionDTO> getByLanguage(Language language) {
        List<RegionDTO> dtoList=new LinkedList<>();
        for (RegionEntity entity : regionRepository.findAll()) {
            RegionDTO dto=new RegionDTO();
            dto.setId(entity.getId());
            switch (language) {
                case UZ -> dto.setName(entity.getNameUz());
                case RU -> dto.setName(entity.getNameRu());
                case EN -> dto.setName(entity.getNameEn());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
}
