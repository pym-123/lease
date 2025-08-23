package com.atguigu.lease.common.exception;

import com.atguigu.lease.common.result.ResultCodeEnum;



public class LeaseException extends RuntimeException {
    public LeaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
    }
}
