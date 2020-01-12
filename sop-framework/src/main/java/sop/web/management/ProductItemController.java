package sop.web.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import sop.persistence.beans.ProductItem;
import sop.services.ProductItemServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:57
 * @Package: sop.web.management
 */

@Controller("management.prductItemController")
@RequestMapping(value = "/management/productitem")
public class ProductItemController extends BaseController {
    @Autowired
    private ProductItemServiceMgr productItemMgr;

    @RequestMapping("/searchproduct")
    public String searchProduct(BaseConditionVO vo, Model model) {

        List<ProductItem> items = productItemMgr.searchProductItem(vo);

        model.addAttribute("items", items);
        return "/management/order/searchProduct";
    }

    @RequestMapping("/navsearchproduct")
    public String navSearchProduct() {

        return "/management/order/searchProduct";
    }

}
