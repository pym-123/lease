package com.atguigu.lease.web.admin.custom.interceptor;

import com.atguigu.lease.common.context.LoginUser;
import com.atguigu.lease.common.context.LoginUserContext;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String remoteAddr = request.getRemoteAddr();

        String token = request.getHeader("access_token");

        if(token==null){
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }
        Claims claims = JwtUtil.parseToken(token, remoteAddr);

      //  LoginUser loginUserClaims = claims.get("loginUser", LoginUser.class);

        Long userId = claims.get("userId", Long.class);

        String userName = claims.get("userName", String.class);

        LoginUser loginUser = new LoginUser(userId, userName);

        LoginUserContext.setLoginUser(loginUser);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LoginUserContext.clear();
    }


}
