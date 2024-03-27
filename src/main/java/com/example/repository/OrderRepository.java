package com.example.repository;

import com.example.entity.OrderEntity;
import com.example.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<OrderEntity,Integer>, PagingAndSortingRepository<OrderEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update OrderEntity set status=?1 where id=?2")
    void changeStatus(OrderStatus cancelled, Integer id);

    @Query("from OrderEntity where profileId=?1")
    Page<OrderEntity> findAllByProfileId(UUID profileId, Pageable pageable);
}
