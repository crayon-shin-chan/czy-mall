package czy.spring.boot.starter.common.controller;

import czy.spring.boot.starter.common.annotion.*;
import czy.spring.boot.starter.common.pojo.Result;
import czy.spring.boot.starter.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 控制器基础接口
 * @param <T>：实体类型
 */
public interface IController<T extends BaseEntity> {

    /**
     * 创建实体
     * @param payload：创建载荷
     * @return：创建后实体
     */
    @Create
    @ApiOperation("创建")
    @PostMapping("/create")
    Result<T> create(@Valid @RequestBody T payload);

    /**
     * 更新实体
     * @param payload：更新载荷
     * @return：更新后实体
     */
    @Update
    @ApiOperation("更新")
    @PutMapping("/update")
    Result<T> update(@RequestBody T payload);

    /**
     * 删除数据
     * @param payload
     * @return
     */
    @Delete
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    Result delete(@RequestBody T payload);

    /**
     * 批量删除数据
     * @param ids：主键列表
     * @return
     */
    @Delete
    @ApiOperation("批量删除")
    @DeleteMapping("/delete/batch")
    Result batchDelete(@RequestBody List<Long> ids);

    @Import
    @PostMapping("/import")
    @ApiOperation("导入")
    Result importData(@RequestParam @ApiParam("导入文件") MultipartFile[] files);

    @Export
    @PostMapping("/export")
    @ApiOperation("导出")
    void exportData(
            @RequestParam(required = false) @ApiParam("排序字段") String sort,
            @ApiParam(hidden = true) Sort sortable,
            T payload);

    @Read
    @GetMapping("/exist")
    @ApiOperation("存在查询")
    Result<Boolean> exist(T payload);

    @Read
    @GetMapping("/{id}")
    @ApiOperation("ID查询")
    Result<T> getById(@PathVariable @ApiParam(value = "ID",example = "1") Long id);

    @Read
    @GetMapping("/page")
    @ApiOperation("分页查询")
    Result<Page<T>> page(
            @RequestParam @ApiParam(value = "分页页码",example = "1") int page,
            @RequestParam @ApiParam(value = "分页大小",example = "1") int size,
            @RequestParam(required = false) @ApiParam("排序字段") String sort,
            @ApiParam(hidden = true) Pageable pageable,
            @ApiParam(hidden = true) Sort sortable,
            T payload);

}
