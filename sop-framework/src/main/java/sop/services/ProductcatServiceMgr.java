package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.ProductCategory;
import sop.vo.ProductCategoryVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:09
 * @Package: sop.services
 */


public interface ProductcatServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "productcatServiceMgr";

    List<ProductCategoryVo> getProductcatListByCondition(BaseConditionVO vo);

    void getProductcatListNum(BaseConditionVO vo);

    ProductCategory getProductcatById(String fkCatCode);

    boolean updateProductcat(ProductCategory currentProductcat);

    Checker checkProductcat(ProductCategory currentProductcat);

    boolean insertProductcat(ProductCategory currentProductcat);

    boolean deleteProductcat(String fkCatCode) throws ServiceException;
}
