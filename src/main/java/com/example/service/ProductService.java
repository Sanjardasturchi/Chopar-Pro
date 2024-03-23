package com.example.service;

import com.example.dto.ProductDTO;
import com.example.entity.ProductEntity;
import com.example.enums.Language;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAll(Language language) {
        List<ProductDTO> dtoList = new LinkedList<>();
        Iterable<ProductEntity> all = productRepository.findAll();
        for (ProductEntity entity : all) {
            if (entity.getVisible()) {
                dtoList.add(toDTO(entity, language));
            }
        }
        return dtoList;
    }

    private ProductDTO toDTO(ProductEntity entity, Language language) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPrice(entity.getPrice());
        dto.setViewCount(entity.getViewCount());
        if (entity.getPrtId() != null) {
            dto.setPrtId(entity.getPrtId());
        }
        if (entity.getDiscountPrice() != null) {
            dto.setDiscountPrice(entity.getDiscountPrice());
        }
        switch (language) {
            case UZ -> {
                dto.setDescription(entity.getDescriptionUz());
                dto.setName(entity.getNameUz());
            }
            case RU -> {
                dto.setDescription(entity.getDescriptionRu());
                dto.setName(entity.getNameRu());
            }
            case EN -> {
                dto.setDescription(entity.getDescriptionEn());
                dto.setName(entity.getNameEn());
            }
        }

        return dto;
    }
}
