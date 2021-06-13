package czy.spring.boot.starter.auth.repository;

import czy.spring.boot.starter.auth.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepositoryImplementation<Role,Long> {

    boolean existsByName(String name);

    /* 通过ID查询角色并且抓取权限 */
    @Query("select r from Role r left join fetch r.authorities where r.id = ?1")
    Role findByIdWithAuthorities(Long id);

    /* 查询所有角色并且抓取权限 */
    @Query("select r from Role r left join fetch r.authorities")
    List<Role> findAllWithAuthorities();

}
