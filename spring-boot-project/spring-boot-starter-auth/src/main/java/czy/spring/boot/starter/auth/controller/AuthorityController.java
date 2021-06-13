package czy.spring.boot.starter.auth.controller;

import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.auth.service.AuthorityService;
import czy.spring.boot.starter.common.annotion.Read;
import czy.spring.boot.starter.common.annotion.Update;
import czy.spring.boot.starter.common.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 权限操作控制器，没有增删改查导入导出
 */
@RestController
@Api(tags = "权限操作")
@RequestMapping("/authority")
public class AuthorityController{

    @Autowired
    private AuthorityService authorityService;

    @Read
    @GetMapping("/all")
    @ApiOperation("获取所有命名空间与权限的映射")
    public Result<Map<String, List<Authority>>> getAll(){
        Map<String,List<Authority>> map = this.authorityService.getAll();
        return new Result<>(200,"获取权限映射成功",map);
    }

    @Update
    @ApiOperation("启用权限")
    @PutMapping("/enable/{id}")
    public Result enable(@PathVariable @ApiParam(value = "权限ID",example = "1") Long id){
        this.authorityService.enable(id);
        return Result.success();
    }

    @Update
    @ApiOperation("批量启用权限")
    @PutMapping("/enable/batch")
    public Result enable(@RequestBody @ApiParam("权限ID列表") List<Long> ids){
        this.authorityService.batchEnable(ids);
        return Result.success();
    }


    @Update
    @ApiOperation("禁用权限")
    @PutMapping("/disable/{id}")
    public Result disable(@PathVariable @ApiParam(value = "权限ID",example = "1") Long id){
        this.authorityService.disable(id);
        return Result.success();
    }

    @Update
    @ApiOperation("批量禁用权限")
    @PutMapping("/disable/batch")
    public Result disable(@RequestBody @ApiParam("权限ID列表") List<Long> ids){
        this.authorityService.batchDisable(ids);
        return Result.success();
    }

}
