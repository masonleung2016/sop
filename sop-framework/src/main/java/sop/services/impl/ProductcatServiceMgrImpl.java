package sop.services.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.ProductcatMapper;
import sop.persistence.beans.ProductCategory;
import sop.services.ProductcatServiceMgr;
import sop.vo.ProductCategoryVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:50
 * @Package: sop.services.impl
 */

@Service(ProductcatServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ProductcatServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ProductcatServiceMgr {

    @Autowired
    private ProductcatMapper productcatMapper;

    @Override
    public List<ProductCategoryVo> getProductcatListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ProductCategoryVo> productcatList = productcatMapper.getProductcatListByCondition(vo, rb);
        return productcatList;
    }

    @Override
    public void getProductcatListNum(BaseConditionVO vo) {
        Integer count = productcatMapper.getProductcatListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public ProductCategory getProductcatById(String fkCatCode) {
        ProductCategory currentProductcat = productcatMapper.getProductcatByFkCatCode(fkCatCode);
        return currentProductcat;
    }

    @Override
    public boolean updateProductcat(ProductCategory currentProductcat) {
        try {
            productcatMapper.updateProductcat(currentProductcat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Checker checkProductcat(ProductCategory currentProductcat) {
        Checker checker = new Checker();
        if (currentProductcat != null) {
            if (currentProductcat.getCatCode() == null || currentProductcat.getCatCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("Product Category不能为空,保存失败");
            } else {
                Integer count = productcatMapper.getCountByFkCatCode(currentProductcat.getCatCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Product Category已存在,请使用其他ID");
                } else {
                    checker.setSuccess(true);
                    checker.setReturnStr("信息无误");
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean insertProductcat(ProductCategory currentProductcat) {
        try {
            Integer flag = productcatMapper.insertProductcat(currentProductcat);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteProductcat(String fkCatCode) throws ServiceException {
        Integer flag = productcatMapper.deleteProductcat(fkCatCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
}
