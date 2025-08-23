package com.atguigu.lease.common.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginUser {
    private Long userId;
    private String userName;
}
