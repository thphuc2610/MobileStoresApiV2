package com.example.store.dao.custom;

import com.example.store.constant.AppConstant;
import com.example.store.dao.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtGenerator {

    public String returnToken(User user){
        return Jwts.builder()
                .claim("name", user.getName())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(AppConstant.JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
