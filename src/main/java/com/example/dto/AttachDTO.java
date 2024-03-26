package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private UUID id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdDate;
    private String url;
    private Long duration;
    private String absolutePath;
}
