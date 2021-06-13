package czy.spring.boot.starter.common.service;

import czy.spring.boot.starter.jpa.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service基础接口
 * @param <T>：操作实体类
 */
public interface IService<T extends BaseEntity>{

    /**
     * 创建数据
     * @param payload：创建载荷
     * @return：创建后的实体
     */
    T create(T payload);

    /**
     * 更新数据
     * @param payload：更新载荷
     * @return：更新后的实体
     */
    T update(T payload);

    /**
     * 删除数据
     * @param payload：删除载荷
     */
    void delete(T payload);

    /**
     * 批量删除数据
     * @param ids：id列表
     */
    void batchDelete(List<Long> ids);

    /**
     * 存在查询
     * @param payload：查询参数
     * @return：是否存在
     */
    Boolean exist(T payload);

    /**
     * 根据id查找
     * @param id：数据主键
     * @return：实体
     */
    T getById(Long id);

    /**
     * 分页查询
     * @param payload：查询数据实体
     * @param pageable：分页对象
     * @return：分页结果
     */
    Page<T> page(T payload, Pageable pageable);

}
