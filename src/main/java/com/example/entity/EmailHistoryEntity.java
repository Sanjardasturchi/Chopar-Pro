package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity extends BaseEntity{
    private String message;
    private String email;
}
