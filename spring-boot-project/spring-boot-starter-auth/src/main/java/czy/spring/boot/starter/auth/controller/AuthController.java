package czy.spring.boot.starter.auth.controller;

import czy.spring.boot.starter.auth.action.LoginAction;
import czy.spring.boot.starter.auth.constant.SessionConstant;
import czy.spring.boot.starter.auth.entity.User;
import czy.spring.boot.starter.auth.service.UserService;
import czy.spring.boot.starter.captcha.service.CaptcheService;
import czy.spring.boot.starter.common.pojo.Result;
import czy.spring.boot.starter.common.util.ActionUtil;
import czy.spring.boot.starter.common.util.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags="认证操作")
@RequestMapping("/auth")
public class AuthController {

    @Autowired(required = false)
    private List<LoginAction> loginActions;

    @Autowired
    private UserService userService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private CaptcheService captcheService;

    @PermitAll
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<Map> login(
            @RequestParam @ApiParam(value = "用户名、手机号、email等",required = true) String username,
            @RequestParam @ApiParam(value = "用户密码",required = true) String password,
            @RequestParam(required = false) @ApiParam("验证码") String code,
            @RequestParam(required = false) @ApiParam("验证码key") String key,
            @ApiParam(hidden = true) HttpServletRequest request
    )throws ServletException {

        /* 验证码校验 */
        this.captcheService.verify(key,code);

        /* spring security的扩展登陆，内部使用认证管理器认证 */
        request.login(username,password);
        request.getSession().setAttribute(SessionConstant.USER, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new Result(200,"登陆成功", ActionUtil.doAction(loginActions));
    }

    @PermitAll
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result logout(@ApiParam(hidden = true) HttpServletRequest request)throws ServletException{
        request.logout();
        return Result.builder().code(200).message("登出成功").build();
    }

    @PermitAll
    @ApiOperation("注册用户")
    @PostMapping("/register")
    public Result<User> register(
            @RequestParam @ApiParam("用户名") String username,
            @RequestParam @ApiParam("密码") String password,
            @RequestParam @ApiParam("邮箱") String email,
            @RequestParam @ApiParam("手机号") String phone,
            @RequestParam(required = false) @ApiParam("手机验证码") String code

    ){

        /* 校验字段 */
        ValidateUtil.checkUserName(username).checkPassword(password).checkEmail(email).checkPhone(phone);

        /* 注册用户 */
        User user = this.userService.register(username,password,email,phone,code);

        return new Result<>(200,"注册用户成功",user);
    }
}
