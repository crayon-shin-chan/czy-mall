package czy.spring.boot.starter.auth.action.impl;

import czy.spring.boot.starter.auth.AuthProperties;
import czy.spring.boot.starter.auth.constant.RoleConstant;
import czy.spring.boot.starter.auth.constant.UserConstant;
import czy.spring.boot.starter.auth.entity.Role;
import czy.spring.boot.starter.auth.entity.User;
import czy.spring.boot.starter.auth.repository.RoleRepository;
import czy.spring.boot.starter.auth.repository.UserRepository;
import czy.spring.boot.starter.auth.util.RunAsUtil;
import czy.spring.boot.starter.common.interfaces.InitAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * 用户初始化
 */
@Slf4j
@Order(2)
@Component
public class UserInitAction implements InitAction {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthProperties authProperties;

    @Override
    public String getName() {
        return "用户初始化";
    }

    @Override
    public Object doAction() {
        RunAsUtil.runAs(UserConstant.ADMIN_USER_ID,authProperties.getAdmin().getUsername());

        log.info("初始化默认用户：");

        Role admin = this.roleRepository.findById(RoleConstant.ADMIN_ROLE_ID).get();

        Optional<User> administrator = this.userRepository.findById(UserConstant.ADMIN_USER_ID);

        if(administrator.isPresent()){
            this.authProperties.getAdmin().update(administrator.get(),new HashSet<>(Arrays.asList(admin)),encoder);
        }else{
            administrator = Optional.of(this.authProperties.getAdmin().create(UserConstant.ADMIN_USER_ID,new HashSet<>(Arrays.asList(admin)),encoder));
        }

        this.userRepository.save(administrator.get());
        return null;
    }
}
