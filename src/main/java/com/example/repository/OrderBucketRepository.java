package com.example.repository;

import com.example.entity.OrderBucketEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderBucketRepository extends CrudRepository<OrderBucketEntity,Integer> {
    @Query("from OrderBucketEntity where orderId=?1")
    Iterable<OrderBucketEntity> findByOrderId(Integer id);

    @Transactional
    @Modifying
    @Query("delete from OrderBucketEntity where orderId=?1")
    void deleteByOrderId(Integer id);
}
