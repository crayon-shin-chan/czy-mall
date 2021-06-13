package czy.spring.boot.starter.auth.controller;

import czy.spring.boot.starter.auth.entity.Role;
import czy.spring.boot.starter.auth.service.RoleService;
import czy.spring.boot.starter.common.annotion.Update;
import czy.spring.boot.starter.common.controller.BaseController;
import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.ienum.CommonError;
import czy.spring.boot.starter.common.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 角色操作控制器
 */
@RestController
@Api(tags = "角色操作")
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {
    
    @Autowired
    private RoleService roleService;

    @Update
    @PutMapping("/authorize")
    @ApiOperation("角色授权操作")
    public Result authorize(@RequestBody Role role){
        if(Objects.isNull(role.getId())){
            throw new HttpException(CommonError.NEED_ID);
        }
        this.roleService.authorize(role);
        return Result.success();
    }

    @Update
    @ApiOperation("启用角色")
    @PutMapping("/enable/{id}")
    public Result enable(@PathVariable @ApiParam(value = "角色ID",example = "1") Long id){
        this.roleService.enable(id);
        return Result.success();
    }

    @Update
    @ApiOperation("批量启用角色")
    @PutMapping("/enable/batch")
    public Result enable(@RequestBody @ApiParam("角色ID列表") List<Long> ids){
        this.roleService.batchEnable(ids);
        return Result.success();
    }


    @Update
    @ApiOperation("禁用角色")
    @PutMapping("/disable/{id}")
    public Result disable(@PathVariable @ApiParam(value = "角色ID",example = "1") Long id){
        this.roleService.disable(id);
        return Result.success();
    }

    @Update
    @ApiOperation("批量禁用角色")
    @PutMapping("/disable/batch")
    public Result disable(@RequestBody @ApiParam("角色ID列表") List<Long> ids){
        this.roleService.batchDisable(ids);
        return Result.success();
    }

}
