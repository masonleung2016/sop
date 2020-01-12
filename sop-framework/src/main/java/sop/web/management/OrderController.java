package sop.web.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.sys.exception.ServiceException;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import sop.persistence.beans.Order;
import sop.persistence.beans.OrderProduct;
import sop.services.OrderServiceMgr;
import sop.vo.OrderProductVo;


/**
 * @Author: LCF
 * @Date: 2020/1/9 12:53
 * @Package: sop.web.management
 */


@Controller("management.orderController")
@RequestMapping(value = "/management/order")
public class OrderController extends BaseController {
    @Autowired
    private OrderServiceMgr orderMgr;

    @RequestMapping("/list")
    public String list(BaseConditionVO vo, Model model) {
        List<Order> orderList = orderMgr.searchOrder(vo);
        Integer totalCount = orderMgr.searchOrderNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("orderList", orderList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);

        return "/management/order/list";
    }

    @RequestMapping("/viewproductsbyorderid/{id}")
    public String viewProductsByOrderId(@PathVariable("id") int id, Model model) {
        Order order = orderMgr.getOrderById(id);
        model.addAttribute("order", order);

        List<OrderProduct> products = orderMgr.getProductsByOrderId(id);
        model.addAttribute("products", products);
        return "/management/order/view";
    }

    @RequestMapping("/add")
    public String add() {
        return "/management/order/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(@RequestBody OrderProductVo orderProductVo, HttpServletRequest request) {
        try {
            orderMgr.addOrderProduct(orderProductVo);

        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping(value = "/testinsert", method = RequestMethod.POST)
    public ModelAndView testInsert(HttpServletRequest request, OrderProductVo orderProductVo) {
        System.out.println("test");
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }


}
