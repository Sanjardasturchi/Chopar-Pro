package com.example.repository;

import com.example.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible=false where id=?1")
    void makeDelete(Integer id);

    @Query("from CategoryEntity where visible=true ")
    Iterable<CategoryEntity> getAll();

    @Query("Select s FROM OrderEntity s join OrderBucketEntity ob on ob.orderId=s.id " +
            "where s.visible =true and ob.productId =:productId and s.profileId =:profileId " +
            "and s.status =:status and s.createdDate between :fromDate and :toDate ")
    Iterable<CategoryEntity> get();
}
