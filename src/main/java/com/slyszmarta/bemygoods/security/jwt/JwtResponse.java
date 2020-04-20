package com.slyszmarta.bemygoods.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtResponse {
    private final String jwttoken;
}
