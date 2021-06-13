package czy.spring.boot.starter.auth.controller;

import czy.spring.boot.starter.auth.entity.User;
import czy.spring.boot.starter.auth.service.UserService;
import czy.spring.boot.starter.common.controller.BaseController;
import czy.spring.boot.starter.common.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "用户操作")
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    @Autowired
    private UserService userService;

    @ApiOperation("更改用户密码")
    @PreAuthorize("authenticated")
    @PutMapping("/change/password")
    public Result changePassword(
            @AuthenticationPrincipal User user,
            @ApiParam("原密码") @RequestParam String oldPassword,
            @ApiParam("新密码") @RequestParam String newPassword){
        this.userService.changePassword(user,oldPassword,newPassword);
        return Result.success();
    }

}
