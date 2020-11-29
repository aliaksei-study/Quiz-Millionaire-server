package com.example.quiz.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public ResponseCookie createAccessTokenCookie(String token, long duration) {
        return ResponseCookie.from("accessToken", token)
                .maxAge(duration)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
