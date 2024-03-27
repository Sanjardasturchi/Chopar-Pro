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
            builder.append("and productId =:productId ");
            params.put("productId", filter.getProductId());
        }
        if (filter.getProfileId() != null) {
            builder.append("and profileId =:profileId ");
            params.put("profileId", filter.getProfileId());
        }
        if (filter.getStatus() != null) {
            builder.append("and status =:status ");
            params.put("status", filter.getStatus());
        }
        if (filter.getOrderDate() != null) {
            LocalDate orderDate = filter.getOrderDate();
            LocalDateTime fromDate = LocalDateTime.of(orderDate, LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(orderDate, LocalTime.MAX);

            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        }


        StringBuilder selectBuilder = new StringBuilder("FROM OrderEntity s where visible =true ");
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
}
