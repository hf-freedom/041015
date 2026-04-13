package com.example.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private Long orgId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
