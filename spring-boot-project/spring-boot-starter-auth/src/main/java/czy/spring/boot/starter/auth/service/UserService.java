package czy.spring.boot.starter.auth.service;

import czy.spring.boot.starter.auth.constant.UserConstant;
import czy.spring.boot.starter.auth.entity.User;
import czy.spring.boot.starter.auth.entity.User_;
import czy.spring.boot.starter.auth.ienum.AuthError;
import czy.spring.boot.starter.auth.repository.UserRepository;
import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService extends BaseService<User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void createCheck(User payload) {
        super.createCheck(payload);
        if(this.userRepository.existsByUsername(payload.getUsername())){
            throw new HttpException(AuthError.USER_NAME_EXIST);
        }
        if(this.userRepository.existsByPhone(payload.getPhone())){
            throw new HttpException(AuthError.PHONE_EXIST);
        }
        if(this.userRepository.existsByEmail(payload.getEmail())){
            throw new HttpException(AuthError.EMAIL_EXIST);
        }
        payload.setPassword(this.passwordEncoder.encode(payload.getPassword()));
    }

    @Override
    protected User updateCheck(User payload) {
        User exist =  super.updateCheck(payload);
        /* 设置原有密码，更新不能更新密码 */
        payload.setPassword(exist.getPassword());
        if(payload.getId().equals(UserConstant.ADMIN_USER_ID)){
            throw new HttpException(AuthError.ADMIN_USER_READONLY);
        }
        if(payload.getId().equals(UserConstant.SCHEDULE_USER_ID)){
            throw new HttpException(AuthError.SCHEDULE_USER_READONLY);
        }
        if( Objects.nonNull(payload.getUsername()) && !payload.getUsername().equals(exist.getUsername()) && this.userRepository.existsByUsername(payload.getUsername())){
            throw new HttpException(AuthError.USER_NAME_EXIST);
        }
        if( Objects.nonNull(payload.getPhone()) && !payload.getPhone().equals(exist.getPhone()) && this.userRepository.existsByPhone(payload.getPhone())){
            throw new HttpException(AuthError.PHONE_EXIST);
        }
        if( Objects.nonNull(payload.getEmail()) && !payload.getEmail().equals(exist.getEmail()) && this.userRepository.existsByEmail(payload.getEmail())){
            throw new HttpException(AuthError.EMAIL_EXIST);
        }
        return exist;
    }

    @Override
    protected User deleteCheck(User payload) {
        User exist = super.deleteCheck(payload);
        if(payload.getId().equals(UserConstant.ADMIN_USER_ID)){
            throw new HttpException(AuthError.ADMIN_USER_READONLY);
        }
        if(payload.getId().equals(UserConstant.SCHEDULE_USER_ID)){
            throw new HttpException(AuthError.SCHEDULE_USER_READONLY);
        }
        return exist;
    }

    @Override
    protected List<User> batchDeleteCheck(List<Long> ids) {
        List<User> list =  super.batchDeleteCheck(ids);
        for(User user: list){
            if(user.getId().equals(UserConstant.ADMIN_USER_ID)){
                throw new HttpException(AuthError.ADMIN_USER_READONLY);
            }
            if(user.getId().equals(UserConstant.SCHEDULE_USER_ID)){
                throw new HttpException(AuthError.SCHEDULE_USER_READONLY);
            }
        }
        return list;
    }

    @Override
    public Boolean exist(User payload) {
        boolean result = false;
        if(StringUtils.hasText(payload.getUsername())){
            result = this.userRepository.existsByUsername(payload.getUsername());
            if(result){
                return result;
            }
        }
        if(StringUtils.hasText(payload.getPhone())){
            result = this.userRepository.existsByPhone(payload.getPhone());
            if(result){
                return result;
            }
        }
        if(StringUtils.hasText(payload.getEmail())){
            result = this.userRepository.existsByEmail(payload.getEmail());
            if(result){
                return result;
            }
        }
        return result;
    }

    /**
     * 分页查询条件
     * @param payload
     * @return
     */
    @Override
    public Specification<User> pageSpecification(User payload) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();//条件列表
                if(StringUtils.hasText(payload.getUsername())){
                    predicates.add(criteriaBuilder.like(root.get(User_.username),payload.getUsername()));//添加用户名like条件
                }
                if(StringUtils.hasText(payload.getEmail())){
                    predicates.add(criteriaBuilder.like(root.get(User_.email),payload.getEmail()));//添加邮箱like条件
                }
                if(Objects.nonNull(payload.getPhone())){
                    predicates.add(criteriaBuilder.like(root.get(User_.phone),payload.getPhone()));//添加电话号码like条件
                }
                if(Objects.nonNull(payload.getEnable())){
                    predicates.add(criteriaBuilder.equal(root.get(User_.enable),payload.getEnable()));//添加应用enable equal条件
                }
                /* 返回一个且条件 */
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    /**
     * 更改用户密码
     * @param user：当前登陆用户
     * @param oldPassword：旧密码
     * @param newPassword：新密码
     */
    public void changePassword(User user,String oldPassword,String newPassword){
        User exist = this.userRepository.findById(user.getId()).get();
        boolean match = this.passwordEncoder.matches(oldPassword,exist.getPassword());
        if(!match){
            throw new HttpException(AuthError.USER_PASSWORD_INCORRECT);
        }
        exist.setPassword(this.passwordEncoder.encode(newPassword));
        this.repository.save(exist);
    }

    public User register(String username, String password, String email, String phone, String code){
        if(this.userRepository.existsByUsername(username)){
            throw new HttpException(AuthError.USER_NAME_EXIST);
        }

        if(this.userRepository.existsByPhone(phone)){
            throw new HttpException(AuthError.PHONE_EXIST);
        }

        if(this.userRepository.existsByEmail(email)){
            throw new HttpException(AuthError.EMAIL_EXIST);
        }

        return this.userRepository.save(new User()
                .setUsername(username)
                .setPassword(passwordEncoder.encode(password))
                .setEmail(email)
                .setPhone(phone)
                .setEnable(true));
    }

}
