package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "attach")
public class AttachEntity extends BaseEntityWithUUID{
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private Double size;
    @Column(name = "extension")
    private String extension;
    @Column(name = "url")
    private String url;
}
