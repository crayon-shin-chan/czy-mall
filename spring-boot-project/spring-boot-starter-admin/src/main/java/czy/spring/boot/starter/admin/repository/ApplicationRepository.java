package czy.spring.boot.starter.admin.repository;

import czy.spring.boot.starter.admin.entity.Application;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepositoryImplementation<Application,Long> {

    Application findByName(String name);

    Application findByAppId(String appId);

    int countByName(String name);

    int countByAppId(String appId);

}
