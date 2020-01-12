package sop.services.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.ProductItemMapper;
import sop.persistence.beans.ProductItem;
import sop.services.ProductItemServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:50
 * @Package: sop.services.impl
 */


@Service(ProductItemServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ProductItemServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ProductItemServiceMgr {

    @Autowired
    private ProductItemMapper productItemMapper;

    @Override
    public List<ProductItem> searchProductItem(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ProductItem> items = productItemMapper.findPageBreakByCondition(vo,
                rb);
        return items;
    }


}
