package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.extra.CreateProfileByAdminDTO;
import com.example.dto.extra.RegistrationByEmailDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.EmailHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.utils.JWTUtil;
import com.example.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {
    //=============== Service =================
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private MailSenderService mailSender;
    //=============== Repository ==============
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    /**
     * This method is used to create profile by admin
     */
    public String registerByAdmin(CreateProfileByAdminDTO dto, Language language) {
        Optional<ProfileEntity> profile = profileRepository.findByEmail(dto.getEmail());
        if (profile.isPresent()) {
            ProfileEntity entity = profile.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException(resourceBundleService.getMessage("profile.already.exist",language));
            }
        }
        ProfileEntity entity=new ProfileEntity();
        entity.setName(dto.getName());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setVisible(true);
        entity.setRole(ProfileRole.ROLE_ADMIN);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        return "Success";
    }








    public String register(RegistrationByEmailDTO dto, Language language) {
        Optional<ProfileEntity> profile = profileRepository.findByEmail(dto.getEmail());
        if (profile.isPresent()) {
            ProfileEntity entity = profile.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException(resourceBundleService.getMessage("profile.already.exist",language));
            }
        }
        LocalDateTime from=LocalDateTime.now().minusMinutes(1);
        LocalDateTime to=LocalDateTime.now();
        if (emailHistoryRepository.countSendEmail(dto.getEmail(), from, to) > 3) {
            throw new AppBadException("To many attempt. Please try after 1 minute.");
        }

        ProfileEntity entity=new ProfileEntity();
        entity.setName(dto.getName());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setVisible(true);
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);

        String jwt = JWTUtil.encodeForEmail(entity.getId());
        String text ="<h1 style=\"text-align: center\">"+resourceBundleService.getMessage("hello",language)+" %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">"+resourceBundleService.getMessage("click.on.the.link.below.to.complete.your.registration",language)+"</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8080/registration/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";
        text = String.format(text, entity.getName(), jwt);
        EmailHistoryDTO emailHistoryDTO=new EmailHistoryDTO();
        emailHistoryDTO.setMessage(text);
        emailHistoryDTO.setEmail(entity.getEmail());
        emailHistoryService.create(emailHistoryDTO);
        mailSender.sendEmail(dto.getEmail(), resourceBundleService.getMessage("complete.registration",language), text);

        return resourceBundleService.getMessage("success",language);
    }

    public String emailVerification(Integer id) {
        return null;
    }

    public void changeProfileStatus(String id) {
        profileRepository.changeStatus(ProfileStatus.ACTIVE,UUID.fromString(id));
    }


}
