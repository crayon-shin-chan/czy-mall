package czy.spring.boot.starter.jpa.listener;

import czy.spring.boot.starter.jpa.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.Assert;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 审计人名称注入的实体监听器
 */
@Configurable(autowire = Autowire.BY_TYPE)
public class AuditingNameEntityListener {

    @Autowired(required = false)
    private AuditorAware<String> auditorNameAware;

    @PrePersist
    public void prePersist(Object target) {

        Assert.notNull(target, "Entity must not be null!");

        if (auditorNameAware != null && (target instanceof BaseEntity)) {
            ((BaseEntity) target).setCreateByName(auditorNameAware.getCurrentAuditor().get());
            ((BaseEntity) target).setUpdateByName(auditorNameAware.getCurrentAuditor().get());
        }
    }

    @PreUpdate
    public void preUpdate(Object target) {

        Assert.notNull(target, "Entity must not be null!");

        if (auditorNameAware != null && (target instanceof BaseEntity)) {
            ((BaseEntity) target).setUpdateByName(auditorNameAware.getCurrentAuditor().get());
        }
    }

}
