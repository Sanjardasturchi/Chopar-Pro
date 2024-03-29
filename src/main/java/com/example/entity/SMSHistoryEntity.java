package com.example.entity;

import com.example.enums.SMSStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "sms_history")
public class SMSHistoryEntity extends BaseEntity{
    @Column(name = "phone")
    private String phone;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SMSStatus status;
}
