package czy.spring.boot.starter.admin.controller;

import czy.spring.boot.starter.admin.entity.Application;
import czy.spring.boot.starter.common.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "应用操作")
@RequestMapping("/application")
public class ApplicationController extends BaseController<Application> {

}
