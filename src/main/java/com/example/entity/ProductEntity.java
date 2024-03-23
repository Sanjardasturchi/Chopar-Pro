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
@Table(name = "product")
public class ProductEntity extends BaseEntity{
    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "description_uz")
    private String descriptionUz;
    @Column(name = "description_ru")
    private String descriptionRu;
    @Column(name = "description_en")
    private String descriptionEn;
    @Column(name = "price")
    private Double price;
    @Column(name = "discount_price")
    private Double discountPrice;
    @Column(name = "prt_id")
    private UUID prtId;
    @Column(name = "view_count")
    private Integer viewCount;
    @Column(name = "category_id")
    private Integer categoryId;
}
