package com.example.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApiPermission implements Serializable {
    private Long id;
    private String name;
    private String url;
    private String method;
    private Long menuId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
