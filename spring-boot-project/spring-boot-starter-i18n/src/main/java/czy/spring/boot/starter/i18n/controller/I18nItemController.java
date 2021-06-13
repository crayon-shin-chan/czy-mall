package czy.spring.boot.starter.i18n.controller;

import czy.spring.boot.starter.common.controller.BaseController;
import czy.spring.boot.starter.i18n.entity.I18nItem;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/i18n")
@Api(tags = "国际化操作")
public class I18nItemController extends BaseController<I18nItem> {


}
