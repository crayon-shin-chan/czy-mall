package czy.spring.boot.starter.auth.action.impl;

import czy.spring.boot.starter.auth.AuthProperties;
import czy.spring.boot.starter.auth.constant.RoleConstant;
import czy.spring.boot.starter.auth.constant.UserConstant;
import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.auth.entity.Role;
import czy.spring.boot.starter.auth.repository.AuthorityRepository;
import czy.spring.boot.starter.auth.repository.RoleRepository;
import czy.spring.boot.starter.auth.util.RunAsUtil;
import czy.spring.boot.starter.common.interfaces.InitAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Slf4j
@Order(1)
@Component
public class RoleInitAction implements InitAction {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public String getName() {
        return "角色初始化";
    }

    @Override
    public Object doAction() {

        RunAsUtil.runAs(UserConstant.ADMIN_USER_ID,authProperties.getAdmin().getUsername());

        List<Authority> authorities = this.authorityRepository.findAll();

        Optional<Role> admin = this.roleRepository.findById(RoleConstant.ADMIN_ROLE_ID);

        if(admin.isPresent()){
            this.authProperties.getAdminRole().update(admin.get(),new HashSet<Authority>(authorities));
        }else{
            admin = Optional.of(this.authProperties.getAdminRole().create(RoleConstant.ADMIN_ROLE_ID,new HashSet<Authority>(authorities)));
        }

        this.roleRepository.save(admin.get());

        Optional<Role> general =  this.roleRepository.findById(RoleConstant.DEFAULT_ROLE_ID);

        if(general.isPresent()){
            this.authProperties.getDefaultRole().update(general.get(),new HashSet<Authority>());
        }else{
            general = Optional.of(this.authProperties.getDefaultRole().create(RoleConstant.DEFAULT_ROLE_ID,new HashSet<Authority>()));
        }

        this.roleRepository.save(general.get());
        return null;
    }
}
