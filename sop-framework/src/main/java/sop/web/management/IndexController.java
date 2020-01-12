package sop.web.management;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:49
 * @Package: sop.web.management
 */

@Controller("management.indexController")
@RequestMapping("/management")
public class IndexController extends BaseController {

    @RequestMapping("/index")
    public String index(BaseConditionVO vo, Model model) {

        model.addAttribute("now", new Date());

        return "/management/index";
    }
}
