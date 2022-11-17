package com.ssu.travel.global.security.jwt.utils;

import com.ssu.travel.domain.user.entity.Role;
import com.ssu.travel.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class JwtUserConvertor implements Function<Claims, User> {
    @Override
    public User apply(Claims claims) {
        long id = Long.parseLong(claims.getSubject());
        String email = (String) claims.get("email");
        Role role = Role.valueOf((String) claims.get("role"));

        return User.convertUser(id, email, role);
    }
}
