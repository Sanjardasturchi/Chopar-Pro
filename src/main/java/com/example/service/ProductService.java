package com.example.service;

import com.example.dto.AttachDTO;
import com.example.dto.ProductDTO;
import com.example.dto.extra.ProductCreateDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ProductAttachEntity;
import com.example.entity.ProductEntity;
import com.example.enums.Language;
import com.example.exp.AppBadException;
import com.example.repository.AttachRepository;
import com.example.repository.ProductAttachRepository;
import com.example.repository.ProductRepository;
import com.example.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    //=============== Service =================
    @Autowired
    private AttachService attachService;
    @Autowired
    private ResourceBundleService resourceBundleService;
    //=============== Repository ==============
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductAttachRepository productAttachRepository;
    @Autowired
    private AttachRepository attachRepository;

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
        List<AttachDTO> attachDTOList = new LinkedList<>();
        for (ProductAttachEntity productAttach : productAttachRepository.findByProductId(entity.getId())) {
            AttachEntity attachEntity = attachService.get(productAttach.getAttachId().toString(), language);
            AttachDTO attachDTO = attachService.toDTO(attachEntity);
            attachDTOList.add(attachDTO);
        }
        dto.setAttachList(attachDTOList);
        return dto;
    }

    public void create(ProductCreateDTO dto) {
        ProductEntity entity = new ProductEntity();
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
                ProductAttachEntity productAttach = new ProductAttachEntity();
                productAttach.setAttachId(UUID.fromString(attachId));
                productAttach.setProductId(entity.getId());
                productAttach.setVisible(true);
                productAttach.setCreatedDate(LocalDateTime.now());
                productAttachRepository.save(productAttach);
            }
        }
    }

    public ProductDTO getById(Integer id) {
        Optional<ProductEntity> optional = productRepository.findById(id);
        return optional.map(productEntity -> toDTO(productEntity, Language.UZ)).orElse(null);
    }

    public String update(Integer id, ProductCreateDTO dto, Language language) {
        Optional<ProductEntity> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return resourceBundleService.getMessage("profile.not.found", language);
        }
        ProductEntity entity = optional.get();
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
        productAttachRepository.deleteByProductId(id);
        for (String attachId : dto.getAttachList()) {
            if (attachRepository.findById(UUID.fromString(attachId)).isPresent()) {
                ProductAttachEntity productAttach = new ProductAttachEntity();
                productAttach.setAttachId(UUID.fromString(attachId));
                productAttach.setProductId(entity.getId());
                productAttach.setVisible(true);
                productAttach.setCreatedDate(LocalDateTime.now());
                productAttachRepository.save(productAttach);
            }
        }
        return "DONE";
    }

    public String delete(Integer id, Language language) {
        if (productRepository.findById(id).isEmpty()) {
            return resourceBundleService.getMessage("profile.not.found", language);
        }
        productRepository.makeDeletedById(id);
        return "DONE";
    }

    public PageImpl<ProductDTO> getByPaginationByCategoryId(Integer categoryId, Language language, Integer page, Integer size) {
        List<ProductDTO> dtoList = new LinkedList<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<ProductEntity> entityPage = productRepository.findByCategoryId(categoryId, paging);
        List<ProductEntity> entityList = entityPage.getContent();
        for (ProductEntity entity : entityList) {
            ProductDTO dto = new ProductDTO();
            dto.setId(entity.getId());
            dto.setPrice(entity.getPrice());
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
            List<AttachDTO> attachDTOList = new LinkedList<>();
            for (ProductAttachEntity productAttach : productAttachRepository.findByProductId(entity.getId())) {
                AttachEntity attachEntity = attachService.get(productAttach.getAttachId().toString(), language);
                AttachDTO attachDTO = attachService.toDTO(attachEntity);
                attachDTOList.add(attachDTO);
                break;
            }
            dto.setAttachList(attachDTOList);
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, paging, entityPage.getTotalElements());
    }

    public List<ProductDTO> getLastTenAddedProducts(Language language) {
        List<ProductDTO> dtoList = new LinkedList<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(0, 10, sort);
        Page<ProductEntity> entityPage = productRepository.findAll(paging);
        List<ProductEntity> entityList = entityPage.getContent();
        for (ProductEntity entity : entityList) {
            ProductDTO dto = new ProductDTO();
            dto.setId(entity.getId());
            dto.setPrice(entity.getPrice());
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
            List<AttachDTO> attachDTOList = new LinkedList<>();
            for (ProductAttachEntity productAttach : productAttachRepository.findByProductId(entity.getId())) {
                AttachEntity attachEntity = attachService.get(productAttach.getAttachId().toString(), language);
                AttachDTO attachDTO = attachService.toDTO(attachEntity);
                attachDTOList.add(attachDTO);
                break;
            }
            dto.setAttachList(attachDTOList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<ProductDTO> getTopTenSoldProducts(Language language) {
        Iterable<ProductEntity> entityList = productRepository.getTopTenSoldProducts();
        List<ProductDTO> dtoList = new LinkedList<>();
        for (ProductEntity entity : entityList) {
            ProductDTO dto = new ProductDTO();
            dto.setId(entity.getId());
            dto.setPrice(entity.getPrice());
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
            List<AttachDTO> attachDTOList = new LinkedList<>();
            for (ProductAttachEntity productAttach : productAttachRepository.findByProductId(entity.getId())) {
                AttachEntity attachEntity = attachService.get(productAttach.getAttachId().toString(), language);
                AttachDTO attachDTO = attachService.toDTO(attachEntity);
                attachDTOList.add(attachDTO);
                break;
            }
            dto.setAttachList(attachDTOList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public ProductDTO getByIdForClient(Integer id, Language language) {
        Optional<ProductEntity> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Product nor found");
        }
        ProductEntity entity = optional.get();
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
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
        List<AttachDTO> attachDTOList = new LinkedList<>();
        for (ProductAttachEntity productAttach : productAttachRepository.findByProductId(entity.getId())) {
            AttachEntity attachEntity = attachService.get(productAttach.getAttachId().toString(), language);
            AttachDTO attachDTO = attachService.toDTO(attachEntity);
            attachDTOList.add(attachDTO);
        }
        dto.setAttachList(attachDTOList);
        return dto;
    }

    public ProductDTO getByIdForAdmin(Integer id, Language language) {
        Optional<ProductEntity> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Product nor found");
        }
        ProductEntity entity = optional.get();
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCategoryId(entity.getCategoryId());
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
        List<AttachDTO> attachDTOList = new LinkedList<>();
        for (ProductAttachEntity productAttach : productAttachRepository.findByProductId(entity.getId())) {
            AttachEntity attachEntity = attachService.get(productAttach.getAttachId().toString(), language);
            AttachDTO attachDTO = attachService.toDTO(attachEntity);
            attachDTOList.add(attachDTO);
        }
        dto.setAttachList(attachDTOList);
        return dto;
    }
}
