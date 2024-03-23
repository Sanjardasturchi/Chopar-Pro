package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer>, PagingAndSortingRepository<EmailHistoryEntity,Integer> {
    @Query("SELECT count (e) from EmailHistoryEntity e where e.email =?1 and e.createdDate between ?2 and ?3")
    Integer countSendEmail(String email, LocalDateTime from, LocalDateTime to);

    @Query("from EmailHistoryEntity e where e.createdDate between ?1 and ?2")
    Iterable<EmailHistoryEntity> byDates(LocalDateTime from, LocalDateTime to);

    Iterable<EmailHistoryEntity> findByEmail(String email);
}
