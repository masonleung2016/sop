package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.ProductItem;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:10
 * @Package: sop.services
 */

public interface ProductItemServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "productItemServiceMgr";

    List<ProductItem> searchProductItem(BaseConditionVO vo);
}
