package com.example.service;

import com.example.dto.AttachDTO;
import com.example.dto.ProductDTO;
import com.example.dto.extra.ProductCreateDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ProductAttachEntity;
import com.example.entity.ProductEntity;
import com.example.enums.Language;
import com.example.repository.AttachRepository;
import com.example.repository.ProductAttachRepository;
import com.example.repository.ProductRepository;
import com.example.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductAttachRepository productAttachRepository;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private AttachService attachService;

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
        List<AttachDTO> attachDTOList=new LinkedList<>();
        for (ProductAttachEntity productAttach : productAttachRepository.findByProductId(entity.getId())) {
            AttachEntity attachEntity = attachService.get(productAttach.getAttachId().toString(), language);
            AttachDTO attachDTO = attachService.toDTO(attachEntity);
            attachDTOList.add(attachDTO);
        }
        dto.setAttachList(attachDTOList);
        return dto;
    }

    public void create(ProductCreateDTO dto) {
        ProductEntity entity=new ProductEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setPrice(dto.getPrice());
        entity.setCategoryId(dto.getCategoryId());
        entity.setPrtId(SpringSecurityUtil.getCurrentUser().getId());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionRu(dto.getDescriptionRu());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        productRepository.save(entity);
        for (String attachId : dto.getAttachList()) {
            if (attachRepository.findById(UUID.fromString(attachId)).isPresent()) {
                ProductAttachEntity productAttach=new ProductAttachEntity();
                productAttach.setAttachId(UUID.fromString(attachId));
                productAttach.setProductId(entity.getId());
                productAttach.setVisible(true);
                productAttach.setCreatedDate(LocalDateTime.now());
                productAttachRepository.save(productAttach);
            }
        }
    }

    public ProductDTO getById(Integer id) {
        return null;
    }
}
