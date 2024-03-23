package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void create(EmailHistoryDTO dto) {
        EmailHistoryEntity entity=new EmailHistoryEntity();
        entity.setEmail(dto.getEmail());
        entity.setMessage(dto.getMessage());
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        emailHistoryRepository.save(entity);
    }

    public Integer countSendEmail(String email, LocalDateTime from, LocalDateTime to) {
        return emailHistoryRepository.countSendEmail(email,from,to);
    }

    public List<EmailHistoryDTO> getByEmail(String email) {
        List<EmailHistoryDTO> dtoList=new LinkedList<>();
        Iterable<EmailHistoryEntity> entities = emailHistoryRepository.findByEmail(email);
        for (EmailHistoryEntity entity : entities) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<EmailHistoryDTO> getByDate(LocalDate date) {
        LocalDateTime from=LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to=LocalDateTime.of(date, LocalTime.MAX);
        List<EmailHistoryDTO> dtoList=new LinkedList<>();
        Iterable<EmailHistoryEntity> entities = emailHistoryRepository.byDates(from,to);
        for (EmailHistoryEntity entity : entities) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public PageImpl<EmailHistoryDTO> getByPagination(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EmailHistoryEntity> all = emailHistoryRepository.findAll(pageable);
        return new PageImpl<>(toDTOList(all.getContent()), pageable, all.getTotalElements());
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto=new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedData(entity.getCreatedDate());
        return dto;
    }


    private List<EmailHistoryDTO> toDTOList(List<EmailHistoryEntity> entityList) {
        List<EmailHistoryDTO> dtoList=new LinkedList<>();
        for (EmailHistoryEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }
}
