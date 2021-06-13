package czy.spring.boot.starter.auth.repository;

import czy.spring.boot.starter.auth.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User,Long> {

    /** 获取{@link org.springframework.security.core.userdetails.UserDetails}，关联查询权限 */
    @Query("select u from User u left join fetch u.roles r left join fetch r.authorities where u.username = ?1 or u.phone = ?1 or u.email = ?1")
    User findUserDetails(String name);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
