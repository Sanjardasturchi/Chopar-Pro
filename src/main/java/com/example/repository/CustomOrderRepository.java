package com.example.repository;

import com.example.dto.extra.order.OrderFilterDTO;
import com.example.entity.OrderEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class CustomOrderRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl<OrderEntity> filter(OrderFilterDTO filter, Integer size, Integer page) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getProductId() != null) {
            builder.append("and ob.productId =:productId ");
            params.put("productId", filter.getProductId());
        }
        if (filter.getProfileId() != null) {
            builder.append("and s.profileId =:profileId ");
            params.put("profileId", filter.getProfileId());
        }
        if (filter.getStatus() != null) {
            builder.append("and s.status =:status ");
            params.put("status", filter.getStatus());
        }
        if (filter.getOrderDate() != null) {
            LocalDate orderDate = filter.getOrderDate();
            LocalDateTime fromDate = LocalDateTime.of(orderDate, LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(orderDate, LocalTime.MAX);

            builder.append("and s.createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        }


        StringBuilder selectBuilder = new StringBuilder("Select s FROM OrderEntity s join OrderBucketEntity ob on ob.orderId=s.id " +
                "where s.visible =true ");
        selectBuilder.append(builder);
//        selectBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("Select count(s) FROM OrderEntity s where visible =true ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult((page - 1) * size); // offset (page-1)*size
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<OrderEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();
        return new PageImpl<OrderEntity>(entityList, PageRequest.of(page - 1, size), totalElements);
    }

//    public PageImpl<OrderEntity> filter(OrderFilterDTO filter, Integer size, Integer page) {
//        StringBuilder builder = new StringBuilder();
//        Map<String, Object> params = new HashMap<>();
//
//        // Query builder for OrderEntity
//        StringBuilder selectBuilder = new StringBuilder("select s. FROM OrderEntity s JOIN OrderBucketEntity ob ON s.id = ob.orderId WHERE s.visible = true ");
//
//        // Query builder for counting total elements
//        StringBuilder countBuilder = new StringBuilder("SELECT COUNT(s) FROM OrderEntity s WHERE visible = true ");
//
//        if (filter.getProductId() != null) {
//            // Join OrderBucketEntity to filter by productId
////            selectBuilder.append("JOIN OrderBucketEntity ob ON s.id = ob.orderId ");
//            builder.append("AND ob.productId = :productId ");
//            params.put("productId", filter.getProductId());
//        }
//
//        if (filter.getProfileId() != null) {
//            builder.append("AND s.profileId = :profileId ");
//            params.put("profileId", filter.getProfileId());
//        }
//        if (filter.getStatus() != null) {
//            builder.append("AND s.status = :status ");
//            params.put("status", filter.getStatus());
//        }
//        if (filter.getOrderDate() != null) {
//            LocalDate orderDate = filter.getOrderDate();
//            LocalDateTime fromDate = LocalDateTime.of(orderDate, LocalTime.MIN);
//            LocalDateTime toDate = LocalDateTime.of(orderDate, LocalTime.MAX);
//
//            builder.append("AND s.createdDate BETWEEN :fromDate AND :toDate ");
//            params.put("fromDate", fromDate);
//            params.put("toDate", toDate);
//        }
//
//        // Append conditions to the main query builder
//        selectBuilder.append(builder);
//        countBuilder.append(builder);
//
//        // Create queries
//        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
//        Query countQuery = entityManager.createQuery(countBuilder.toString());
//
//        // Set parameters
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            selectQuery.setParameter(param.getKey(), param.getValue());
//            countQuery.setParameter(param.getKey(), param.getValue());
//        }
//
//        // Pagination
//        selectQuery.setMaxResults(size); // limit
//        selectQuery.setFirstResult((page - 1) * size); // offset (page-1)*size
//
//        // Execute queries
//        List<OrderEntity> entityList = selectQuery.getResultList();
//        Long totalElements = (Long) countQuery.getSingleResult();
//
//        // Return paginated results
//        return new PageImpl<>(entityList, PageRequest.of(page - 1, size), totalElements);
//    }

}
