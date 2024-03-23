package com.example.entity;

import com.example.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{
    @Column(name = "profile_id")
    private UUID profileId;
    @Column(name = "amount")
    private Double amount;//(total-amount)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "delivered_address")
    private String deliveredAddress;
    @Column(name = "delivered_contact")
    private String deliveredContact;
}
