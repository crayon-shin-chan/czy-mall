package czy.spring.boot.starter.auth.repository;

import czy.spring.boot.starter.auth.entity.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepositoryImplementation<Authority,Long> {

    /* 查询权限，抓取角色 */
    @Query("select a from Authority a left join fetch a.roles r left join fetch r.authorities")
    Set<Authority> findAllWithRoles();


}
