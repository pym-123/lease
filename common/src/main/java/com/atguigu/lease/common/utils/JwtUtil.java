package com.atguigu.lease.common.utils;

import com.atguigu.lease.common.context.LoginUser;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static final String SIGN_KEY = "JHBVJLHGBIUHOIHUIOHIHIYUGUYUGOIUGYOUGUYGYUGIUYTFRTYFUOGBUIFGHAIFOUYGRG";
    private static  final Long EXP_TIME = 60 * 1000L ;
    public static String getToken(LoginUser loginUser,String remoteAddr){

        String signKey = remoteAddr+SIGN_KEY;

        SecretKey secretKey = Keys.hmacShaKeyFor(signKey.getBytes());

        return Jwts.builder().claim("userId", loginUser.getUserId()).claim("userName", loginUser.getUserName()).setExpiration(new Date(System.currentTimeMillis()+EXP_TIME)).signWith(secretKey).compact();
    }

    public static Claims parseToken(String token,String remoteAddr){

        String signKey = remoteAddr+SIGN_KEY;

        SecretKey secretKey = Keys.hmacShaKeyFor(signKey.getBytes());

         Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

         return claimsJws.getBody();
    }
}
