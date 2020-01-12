package sop.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.SupplierMapper;
import sop.persistence.beans.Supplier;
import sop.services.SupplierServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:55
 * @Package: sop.services.impl
 */

@Transactional(rollbackFor = Exception.class)
@Service(SupplierServiceMgr.SERVICE_NAME)
public class SupplierServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements SupplierServiceMgr {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public void addSupplier(Supplier supplier) throws ServiceException {
        Date now = new Date();
        supplier.setCrtDate(now);
        supplierMapper.insert(supplier);
    }

    @Override
    public List<Supplier> searchSupplier(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Supplier> supplierList = supplierMapper.findPageBreakByCondition(vo,
                rb);
        return supplierList;
    }

    @Override
    public Integer searchSupplierNum(BaseConditionVO vo) {
        Integer count = supplierMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public Supplier getSupplierByCode(String code) {
        Supplier supplier = supplierMapper.getSupplierByCode(code);
        if (supplier == null) return null;
        return supplier;
    }

    @Override
    public void updateSupplier(Supplier supplier) throws ServiceException {
        Date now = new Date();
        //TODO: set modUsr
        supplier.setModDate(now);
        supplierMapper.updateSelective(supplier);
    }

    @Override
    public void deleteSupplier(String code) {
        //TODO: set modUsr
        supplierMapper.updateStatus(code, true, "David", new Date());
    }
}
