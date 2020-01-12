package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Supplier;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:13
 * @Package: sop.services
 */

public interface SupplierServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "supplierServiceMgr";

    List<Supplier> searchSupplier(BaseConditionVO vo);

    Integer searchSupplierNum(BaseConditionVO vo);

    void addSupplier(Supplier supplier) throws ServiceException;

    Supplier getSupplierByCode(String code);

    void updateSupplier(Supplier supplier) throws ServiceException;

    void deleteSupplier(String ccyCode) throws ServiceException;


}
