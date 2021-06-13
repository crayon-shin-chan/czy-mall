package czy.spring.boot.starter.common.controller;

import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.ienum.CommonError;
import czy.spring.boot.starter.common.pojo.Result;
import czy.spring.boot.starter.common.service.IService;
import czy.spring.boot.starter.common.validate.group.CreateGroup;
import czy.spring.boot.starter.common.validate.group.UpdateGroup;
import czy.spring.boot.starter.jpa.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Objects;

/**
 * 控制器基类
 * @param <T>：实体类型
 */
public class BaseController<T extends BaseEntity> implements IController<T>{

    /** 泛型注入指定Service */
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected IService<T> service;

    @Autowired
    protected Validator validator;

    /**
     * 创建前参数验证
     * @param payload：创建参数对象
     */
    protected void createVerify(T payload){
        /* 创建时验证 */
        this.validator.validate(payload, Default.class, CreateGroup.class);
    }

    /**
     * 更新前参数验证
     * @param payload
     */
    protected void updateVerify(T payload){
        if(Objects.isNull(payload.getId())){
            throw new HttpException(CommonError.NEED_ID);
        }
        /* 更新时验证 */
        this.validator.validate(payload, Default.class, UpdateGroup.class);
    }

    /**
     * 删除前参数验证
     * @param payload
     */
    protected void deleteVerify(T payload){
        if(Objects.isNull(payload.getId())){
            throw new HttpException(CommonError.NEED_ID);
        }
    }

    @Override
    public Result<T> create(T payload) {
        this.createVerify(payload);//创建前参数验证
        return Result.success(this.service.create(payload));
    }

    @Override
    public Result<T> update(T payload) {
        this.updateVerify(payload);//更新前参数验证
        return Result.success(this.service.update(payload));
    }

    @Override
    public Result delete(T payload) {
        this.deleteVerify(payload);//删除前参数验证
        this.service.delete(payload);
        return Result.success();
    }

    @Override
    public Result batchDelete(List<Long> ids) {
        this.service.batchDelete(ids);
        return Result.success();
    }

    @Override
    public Result<Void> importData(MultipartFile[] files) {
        return null;
    }

    @Override
    public void exportData(String sort, Sort sortable, T payload) {

    }

    @Override
    public Result<Boolean> exist(T payload) {
        return Result.success(this.service.exist(payload));
    }

    @Override
    public Result<T> getById(Long id) {
        if(Objects.isNull(id)){
            throw new HttpException(CommonError.NEED_ID);
        }
        return Result.success(this.service.getById(id));
    }

    @Override
    public Result<Page<T>> page(int page, int size, String sort, Pageable pageable, Sort sortable, T payload) {
        pageable.getSortOr(sortable);
        return Result.success(this.service.page(payload,pageable));
    }
}
