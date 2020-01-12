package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Order;
import sop.persistence.beans.OrderProduct;
import sop.vo.OrderProductVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:04
 * @Package: sop.services
 */

public interface OrderServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "orderServiceMgr";

    List<Order> searchOrder(BaseConditionVO vo);

    Integer searchOrderNum(BaseConditionVO vo);

    Order getOrderById(int fkOrder);

    List<OrderProduct> getProductsByOrderId(int id);

    void addOrderProduct(OrderProductVo orderProductVo) throws ServiceException;
}
