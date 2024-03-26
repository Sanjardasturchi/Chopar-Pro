package com.example.repository;

import com.example.entity.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends CrudRepository<ProductEntity,Integer>, PagingAndSortingRepository<ProductEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update ProductEntity set visible=false where id=?1")
    void makeDeletedById(Integer id);

    Page<ProductEntity> findByCategoryId(Integer categoryId, Pageable paging);

    @Query(value = "select * from product where id in (\n" +
            "select b.product_id from order_bucket b " +
            "group by b.product_id,b.amount " +
            "order by b.amount desc limit 10) " +
            "order by product.created_date desc",nativeQuery = true)
    Iterable<ProductEntity> getTopTenSoldProducts();
}
