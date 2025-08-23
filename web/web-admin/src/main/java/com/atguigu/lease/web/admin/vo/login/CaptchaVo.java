package com.atguigu.lease.web.admin.vo.login;

import com.atguigu.lease.web.admin.service.LoginService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Schema(description = "图像验证码")
@AllArgsConstructor
public class CaptchaVo {


    @Schema(description="验证码图片信息")
    private String image;

    @Schema(description="验证码key")
    private String key;
}
