package com.example.repository;

import com.example.entity.ProductAttachEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductAttachRepository extends CrudRepository<ProductAttachEntity,Integer> {
    @Query("from ProductAttachEntity where productId=?1")
    Iterable<ProductAttachEntity> findByProductId(Integer id);
}
