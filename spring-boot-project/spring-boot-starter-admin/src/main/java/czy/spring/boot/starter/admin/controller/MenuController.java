package czy.spring.boot.starter.admin.controller;

import czy.spring.boot.starter.admin.entity.Menu;
import czy.spring.boot.starter.common.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "菜单操作")
@RequestMapping("/menu")
public class MenuController extends BaseController<Menu> {

}
