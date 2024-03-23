package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.extra.CreateProfileByAdminDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        ProfileEntity entity=new ProfileEntity();
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

    public String updateByAdmin(CreateProfileByAdminDTO updateDTO, String profileId, Language language) {
        Optional<ProfileEntity> optional = profileRepository.findById(UUID.fromString(profileId));
        if (optional.isEmpty()) {
            throw  new AppBadException(resourceBundleService.getMessage("profile.not.found",language));
        }
        ProfileEntity entity = optional.get();
        entity.setName(updateDTO.getName());
        entity.setEmail(updateDTO.getEmail());
        entity.setPassword(updateDTO.getPassword());
        entity.setBirthDate(updateDTO.getBirthDate());
        entity.setStatus(updateDTO.getStatus());
        profileRepository.save(entity);
        return resourceBundleService.getMessage("success",language);
    }
}
