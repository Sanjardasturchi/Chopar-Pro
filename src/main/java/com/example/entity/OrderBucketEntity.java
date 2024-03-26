package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
@Entity
@Table(name = "order_bucket")
public class OrderBucketEntity extends BaseEntity{
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "price")
    private Double price;
}
