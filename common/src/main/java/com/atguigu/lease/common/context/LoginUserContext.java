package com.atguigu.lease.common.context;

public class LoginUserContext {
    private static final ThreadLocal<LoginUser> LOGIN_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setLoginUser(LoginUser loginUser){
        LOGIN_USER_THREAD_LOCAL.set(loginUser);
    }
    public static LoginUser getLoginUser(){
        return LOGIN_USER_THREAD_LOCAL.get();
    }

    public static void clear(){
        LOGIN_USER_THREAD_LOCAL.remove();
    }
}
