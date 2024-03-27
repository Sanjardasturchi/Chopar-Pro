package com.example.service;

import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileStatus;
import com.example.repository.ProfileRepository;
import com.example.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    //=============== Repository ==============
    @Autowired
    private ProfileRepository profileRepository;


    public String check(AuthDTO auth, Language language) {
        Optional<ProfileEntity> profile = profileRepository.findByEmail(auth.getEmail());
        if (profile.isEmpty()||!profile.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            return "Profile not found";
        } else if (!profile.get().getPassword().equals(MD5Util.encode(auth.getPassword()))) {
            return "Invalid password";
        }

        return "Success";
    }

    public ProfileDTO getProfile(AuthDTO auth) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(auth.getEmail());
        if (optional.isEmpty()) {
            return null;
        }
        ProfileEntity entity = optional.get();
        ProfileDTO dto=new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
