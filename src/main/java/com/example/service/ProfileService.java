package com.example.service;

import com.example.config.details.CustomUserDetails;
import com.example.dto.ProfileDTO;
import com.example.dto.extra.profile.UpdateProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    //=============== Service =================
    @Autowired
    private ResourceBundleService resourceBundleService;
    //=============== Repository ==============
    @Autowired
    private ProfileRepository profileRepository;

    public boolean saveByAdmin(ProfileDTO dto) {
        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            return false;
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setBirthDate(dto.getBirthDate());
        entity.setRole(ProfileRole.ROLE_ADMIN);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        profileRepository.save(entity);
        return true;
    }

    public String updateByAdmin(UpdateProfileDTO updateDTO, String profileId, Language language) {
        Optional<ProfileEntity> optional = profileRepository.findById(UUID.fromString(profileId));
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.found", language));
        }
        ProfileEntity entity = optional.get();
        entity.setName(updateDTO.getName());
        if (updateDTO.getStatus() != null) {
            entity.setStatus(updateDTO.getStatus());
        }
        profileRepository.save(entity);
        return resourceBundleService.getMessage("success", language);
    }

    public String updateOwnDetail(UpdateProfileDTO updateDTO, Language language) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        UUID id = currentUser.getId();
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.found", language));
        }
        ProfileEntity entity = optional.get();
        entity.setName(updateDTO.getName());
        if (updateDTO.getStatus() != null) {
            entity.setStatus(updateDTO.getStatus());
        }
        profileRepository.save(entity);
        return resourceBundleService.getMessage("success", language);
    }

    public PageImpl<ProfileDTO> getByPagination(Integer page, Integer size, Language language) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProfileEntity> all = profileRepository.findAllByPage(pageable);
        return new PageImpl<>(toDTOList(all.getContent()), pageable, all.getTotalElements());
    }

    private List<ProfileDTO> toDTOList(List<ProfileEntity> entityList) {
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setBirthDate(entity.getBirthDate());
        return dto;
    }

    public String deleteById(String id, Language language) {
        profileRepository.makeDeleteById(UUID.fromString(id));
        return "Done";
    }
}
