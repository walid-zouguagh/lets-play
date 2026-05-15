package com.lets_play.dto;

import com.lets_play.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String email;
    private Role role;

}
