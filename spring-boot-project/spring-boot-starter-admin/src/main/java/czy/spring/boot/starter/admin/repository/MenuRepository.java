package czy.spring.boot.starter.admin.repository;

import czy.spring.boot.starter.admin.entity.Application;
import czy.spring.boot.starter.admin.entity.Menu;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/* 菜单仓库 */
@Repository
public interface MenuRepository extends JpaRepositoryImplementation<Menu,Long> {

    /* 根据路径以及所属应用查找数量，同一应用下一个路径只能有一个 */
    int countByPathAndApplication(String path, Application application);

    /* 根据菜单路径和所属应用判断是否存在 */
    boolean existsByPathAndApplication(String path, Application application);

    /* 根据菜单名称和所属应用判断是否存在 */
    boolean existsByNameAndApplication(String path, Application application);

    /* 根据菜单标题和所属应用判断是否存在 */
    boolean existsByTitleAndApplication(String path, Application application);

    /* 根据应用查找 */
    List<Menu> findByApplication(Application application);

}
