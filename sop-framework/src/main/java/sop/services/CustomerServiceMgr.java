package sop.services;

import java.util.List;
import java.util.Map;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.CustAttn;
import sop.persistence.beans.Customer;
import sop.vo.CustomerComboVo;
import sop.vo.CustomerVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:00
 * @Package: sop.services
 */

public interface CustomerServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "customerServiceMgr";

    List<CustomerVo> searchCustomer(BaseConditionVO vo);

    Integer searchCustomerNum(BaseConditionVO vo);

    void addCustomer(Customer customer) throws ServiceException;

    Customer getCustomerByCode(String cuCode);

    void updateCustomer(Customer customer) throws ServiceException;

    boolean deleteCustomer(String fkCuCode) throws ServiceException;

    CustomerComboVo getCustomerComboVo();

    Checker checkCustomerDecimal(Customer customer);

    Checker checkDecimal(Customer customer);

    Checker checkCustomer(Customer customer);

    Checker checkCustAttn(CustAttn custAttn, Customer customer);

    Map<String, CustAttn> removeCustAttn(String fkAttn, Map<String, CustAttn> custAttns);
}
