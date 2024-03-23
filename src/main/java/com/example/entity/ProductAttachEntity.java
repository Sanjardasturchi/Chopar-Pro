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
@Table(name = "product_attach")
public class ProductAttachEntity extends BaseEntity{
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "attach_id")
    private UUID attachId;
}
