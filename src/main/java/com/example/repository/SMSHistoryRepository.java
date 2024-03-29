package com.example.repository;

import com.example.entity.SMSHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface SMSHistoryRepository extends CrudRepository<SMSHistoryEntity,Integer> {
    @Query("from SMSHistoryEntity where visible=true and phone=?1")
    Iterable<SMSHistoryEntity> findByPhone(String phone);

    @Query("from SMSHistoryEntity where visible=true and createdDate between ?1 and ?2")
    Iterable<SMSHistoryEntity> findByDates(LocalDateTime from, LocalDateTime to);

    @Query("from SMSHistoryEntity where visible=true")
    Page<SMSHistoryEntity> getByPage(Pageable pageable);
}
