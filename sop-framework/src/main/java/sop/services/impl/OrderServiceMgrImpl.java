package sop.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.OrderMapper;
import sop.persistence.beans.Order;
import sop.persistence.beans.OrderProduct;
import sop.persistence.beans.ProductItem;
import sop.services.OrderServiceMgr;
import sop.vo.OrderProductVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:47
 * @Package: sop.services.impl
 */

@Service(OrderServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class OrderServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements OrderServiceMgr {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> searchOrder(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Order> orderList = orderMapper.findPageBreakByCondition(vo,
                rb);
        return orderList;
    }

    @Override
    public Integer searchOrderNum(BaseConditionVO vo) {
        Integer count = orderMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public Order getOrderById(int fkOrder) {
        Order order = orderMapper.load(fkOrder);
        if (order == null) {
            return null;
        } else {
            return order;
        }
    }

    @Override
    public List<OrderProduct> getProductsByOrderId(int id) {
        List<OrderProduct> products = orderMapper.getProducdtsByOrderId(id);
        if (products == null) {
            return null;
        } else {
            return products;
        }
    }

    @Override
    public void addOrderProduct(OrderProductVo orderProductVo)
            throws ServiceException {

        Order order = orderProductVo.getOrder();
        List<ProductItem> items = orderProductVo.getProductItems();
        //生成order表的数据
        Date now = new Date();
        order.setImportdatetime(now);
        orderMapper.insert(order);
        //生成order_product中间表的数据
        for (Iterator<ProductItem> iterator = items.iterator(); iterator.hasNext(); ) {
            ProductItem item = iterator.next();
            OrderProduct op = new OrderProduct();
            op.setFkOrder(order.getId());

            op.setFkProduct(item.getId());
            op.setProductNo(item.getProductNo());
            op.setProductChnName(item.getProductChnName());
            op.setImportdatetime(now);

            orderMapper.insertOrderProduct(op);
            Integer fkOrderProduct = op.getId();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("fkOrderProduct", fkOrderProduct);
            params.put("fkProduct", item.getId());
            Integer fkchecklist = orderMapper.addChecklist(params);
        }
    }
}
