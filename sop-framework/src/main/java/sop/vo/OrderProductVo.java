package sop.vo;

import java.util.List;

import sop.persistence.beans.Order;
import sop.persistence.beans.ProductItem;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:44
 * @Package: sop.vo
 */

public class OrderProductVo {
    
    private Order order;
    
    private List<ProductItem> productItems;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }
}
