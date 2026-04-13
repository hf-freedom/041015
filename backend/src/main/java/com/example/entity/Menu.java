package com.example.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Menu implements Serializable {
    private Long id;
    private String name;
    private String path;
    private String component;
    private Long parentId;
    private String icon;
    private Integer sort;
    private Integer type;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
