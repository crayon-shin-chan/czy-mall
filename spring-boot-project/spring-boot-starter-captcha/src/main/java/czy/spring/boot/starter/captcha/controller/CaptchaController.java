package czy.spring.boot.starter.captcha.controller;

import czy.spring.boot.starter.captcha.service.CaptcheService;
import czy.spring.boot.starter.common.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@PermitAll
@RestController
@Api(tags = "验证码操作")
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptcheService captcheService;

    @GetMapping("/image/{key}")
    @ApiOperation("获取图片验证码")
    public Result<String> image(@ApiParam("验证码key") @PathVariable String key){

        String base64 = this.captcheService.image(key);

        return new Result<>(200,"获取验证码成功",base64);
    }

}
