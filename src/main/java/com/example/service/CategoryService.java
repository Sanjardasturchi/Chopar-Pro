package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.Language;
import com.example.exp.AppBadException;
import com.example.repository.CategoryRepository;
import com.example.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    //=============== Service =================
    @Autowired
    private ResourceBundleService resourceBundleService;
    //=============== Repository ==============
    @Autowired
    private CategoryRepository regionRepository;

    public void create(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setPrtId(SpringSecurityUtil.getCurrentUser().getId());
        regionRepository.save(entity);
    }

    public void update(Integer id, CategoryDTO regionDTO, Language language) {
        Optional<CategoryEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.found", language));
        }
        CategoryEntity entity = optional.get();
        if (regionDTO.getOrderNumber() != null) {
            entity.setOrderNumber(regionDTO.getOrderNumber());
        }
        entity.setNameUz(regionDTO.getNameUz());
        entity.setNameRu(regionDTO.getNameRu());
        entity.setNameEn(regionDTO.getNameEn());
        entity.setPrtId(SpringSecurityUtil.getCurrentUser().getId());
        regionRepository.save(entity);
    }

    public void delete(Integer id, Language language) {
        regionRepository.makeDelete(id);
    }

    public List<CategoryDTO> getAll() {
        List<CategoryDTO> dtoList=new LinkedList<>();
        for (CategoryEntity entity : regionRepository.getAll()) {
            dtoList.add(toDTOFull(entity));
        }
        return dtoList;
    }

    private CategoryDTO toDTOFull(CategoryEntity entity) {
        CategoryDTO dto=new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPrtId(entity.getPrtId());
        return dto;
    }

    public List<CategoryDTO> getByLanguage(Language language) {
        List<CategoryDTO> dtoList=new LinkedList<>();
        for (CategoryEntity entity : regionRepository.getAll()) {
            CategoryDTO dto=new CategoryDTO();
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
