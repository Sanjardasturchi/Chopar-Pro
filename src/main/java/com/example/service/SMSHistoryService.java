package com.example.service;

import com.example.dto.SMSHistoryDTO;
import com.example.entity.SMSHistoryEntity;
import com.example.enums.SMSStatus;
import com.example.repository.SMSHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class SMSHistoryService {
    @Autowired
    private SMSHistoryRepository smsHistoryRepository;
    @Value("${my.eskiz.uz.email}")
    private String email;
    @Value("${my.eskiz.uz.password}")
    private String password;
    @Value("${sms.fly.uz.url}")
    private String url;

    public String sendSMS(String phone, String message) {
        LocalDateTime from = LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(1));
        LocalDateTime to = LocalDateTime.now();
        Iterable<SMSHistoryEntity> lastOneMinute = smsHistoryRepository.findByDates(from, to);
        int count = 0;
        for (SMSHistoryEntity entity : lastOneMinute) {
            count++;
            if (count > 2) {
                return "Wait one minute";
            }
        }
        sendSmsHTTP(phone, message);
        return "DONE";
    }

    public List<SMSHistoryDTO> getByPhone(String phone) {
        sendSMS(phone,"Assalomu alaykum bratim!");
        List<SMSHistoryDTO> dtoList = new LinkedList<>();
        Iterable<SMSHistoryEntity> entitiesByPhone = smsHistoryRepository.findByPhone(phone);
        for (SMSHistoryEntity entity : entitiesByPhone) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<SMSHistoryDTO> getByDate(LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        List<SMSHistoryDTO> dtoList = new LinkedList<>();
        Iterable<SMSHistoryEntity> entitiesByPhone = smsHistoryRepository.findByDates(from, to);
        for (SMSHistoryEntity entity : entitiesByPhone) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    private SMSHistoryDTO toDTO(SMSHistoryEntity entity) {
        SMSHistoryDTO dto = new SMSHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<SMSHistoryDTO> getByPagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SMSHistoryEntity> entityPage = smsHistoryRepository.getByPage(pageable);
        Iterable<SMSHistoryEntity> entitiesByPhone = entityPage.getContent();
        List<SMSHistoryDTO> dtoList = new LinkedList<>();
        for (SMSHistoryEntity entity : entitiesByPhone) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public String getTokenWithAuthorization() {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url + "/api/auth/login")
                .method("POST", body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException();
            } else {
                JSONObject object = new JSONObject(response.body().string());
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public void sendSmsHTTP(String phone, String text) {
        String token = "Bearer " + getTokenWithAuthorization();

        OkHttpClient client = new OkHttpClient();

        if (phone.startsWith("+")) {
            phone = phone.substring(1);
        }

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", phone)
                .addFormDataPart("message", text)
                .addFormDataPart("from", "4546")
                .build();

        Request request = new Request.Builder()
                .url(url + "/api/message/sms/send")
                .method("POST", body)
                .header("Authorization", token)
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SMSHistoryEntity smsHistory = new SMSHistoryEntity();
        smsHistory.setMessage(text);
        smsHistory.setPhone(phone);
        smsHistory.setVisible(true);
        smsHistory.setStatus(SMSStatus.USED);
        smsHistory.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(smsHistory);
    }
}
