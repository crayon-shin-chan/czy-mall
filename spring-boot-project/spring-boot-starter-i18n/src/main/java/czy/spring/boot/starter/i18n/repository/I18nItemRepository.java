package czy.spring.boot.starter.i18n.repository;

import czy.spring.boot.starter.i18n.entity.I18nItem;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface I18nItemRepository extends JpaRepositoryImplementation<I18nItem,Long> {

    /** 根据code和lang判断是否存在 */
    boolean existsByCodeAndLang(String code,String lang);

}
