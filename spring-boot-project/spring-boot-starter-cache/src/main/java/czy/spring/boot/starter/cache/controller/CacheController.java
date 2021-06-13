package czy.spring.boot.starter.cache.controller;

import czy.spring.boot.starter.cache.service.CacheService;
import czy.spring.boot.starter.common.annotion.Read;
import czy.spring.boot.starter.common.annotion.Update;
import czy.spring.boot.starter.common.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(tags = "缓存操作")
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @Read
    @GetMapping("/names")
    public Result<Collection<String>> getNames(){
        return Result.success(this.cacheService.getNames());
    }

    @Update
    @PutMapping("/clear/all")
    @ApiOperation("清除所有缓存")
    public Result clearAll(@PathVariable @ApiParam("缓存名称") String name){
        this.cacheService.clearAll();
        return Result.success();
    }

    @Update
    @PutMapping("/clear/{name}")
    @ApiOperation("清除指定名称缓存")
    public Result clear(@PathVariable @ApiParam("缓存名称") String name){
        this.cacheService.clear(name);
        return Result.success();
    }

}
