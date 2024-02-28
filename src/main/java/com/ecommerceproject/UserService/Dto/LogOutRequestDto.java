package com.ecommerceproject.UserService.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutRequestDto {
    private String token;
    private Long userId;
}
