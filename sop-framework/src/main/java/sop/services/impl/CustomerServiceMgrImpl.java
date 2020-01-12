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
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.CustomerMapper;
import dwz.persistence.mapper.GlInterfaceMapper;
import dwz.persistence.mapper.PayTermMapper;
import sop.persistence.beans.CustAttn;
import sop.persistence.beans.Customer;
import sop.services.CustomerServiceMgr;
import sop.vo.CustomerComboVo;
import sop.vo.CustomerVo;
import sop.vo.GlInterfaceVo;
import sop.vo.PayTermVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:33
 * @Package: sop.services.impl
 */

@Transactional(rollbackFor = Exception.class)
@Service(CustomerServiceMgr.SERVICE_NAME)
public class CustomerServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements CustomerServiceMgr {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PayTermMapper payTermMapper;

    @Autowired
    private GlInterfaceMapper glinterfaceMapper;

    @Override
    public void addCustomer(Customer customer) throws ServiceException {
        Date now = new Date();
        if (customer != null) {
            customer.setCrtDate(now);
            customerMapper.insert(customer);
            Map<String, CustAttn> custAttns = customer.getCustAttns();
            if (custAttns != null && custAttns.size() > 0) {
                Iterator iter = custAttns.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    CustAttn custAttn = custAttns.get(key);
                    custAttn.setCuCode(customer.getCuCode());
                    customerMapper.insertCustAttn(custAttn);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerVo> searchCustomer(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<CustomerVo> customerList = customerMapper.findPageBreakByCondition(vo,
                rb);
        return customerList;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer searchCustomerNum(BaseConditionVO vo) {
        Integer count = customerMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByCode(String cuCode) {
        Customer customer = customerMapper.getCustomerByCode(cuCode);
        if (customer == null) {
            return null;
        } else {
            List<CustAttn> custAttnList = customerMapper.getAttnsByCuCode(customer.getCuCode());
            if (custAttnList != null && custAttnList.size() > 0) {
                Map<String, CustAttn> custAttns = new HashMap<String, CustAttn>();
                for (CustAttn custAttn : custAttnList) {
                    custAttns.put(custAttn.getEncodeAttention(), custAttn);
                }
                customer.setCustAttns(custAttns);
            }
        }
        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) throws ServiceException {
        Date now = new Date();
        if (customer != null) {
            customer.setModDate(now);
            customerMapper.updateSelective(customer);
            customerMapper.delCustAttns(customer.getCuCode());
            Map<String, CustAttn> custAttns = customer.getCustAttns();
            if (custAttns != null && custAttns.size() > 0) {
                Iterator iter = custAttns.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    CustAttn custAttn = custAttns.get(key);
                    custAttn.setCuCode(customer.getCuCode());
                    customerMapper.insertCustAttn(custAttn);
                }
            }
        }

    }

    @Override
    public boolean deleteCustomer(String fkCuCode) {
        Integer flag = customerMapper.deleteCustomer(fkCuCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerComboVo getCustomerComboVo() {
        CustomerComboVo customerComboVo = new CustomerComboVo();
        List<PayTermVo> payTerms = payTermMapper.getAllPayTermVo();
        List<GlInterfaceVo> glInterfaces = glinterfaceMapper.getAllGlInterfaceVo();
        customerComboVo.setPayTerms(payTerms);
        customerComboVo.setGlInterfaces(glInterfaces);
        return customerComboVo;
    }

    @Override
    public Checker checkCustomerDecimal(Customer customer) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (customer.getCuCredit() != null) {
            if (customer.getCuCredit().doubleValue() < -99999999.99 || customer.getCuCredit().doubleValue() > 99999999.99) {
                checker.setSuccess(false);
                checker.setReturnStr("Credit必须在[-99999999.99, 99999999.99]范围内");
                return checker;
            }
        }
        return checker;
    }

    @Override
    public Checker checkDecimal(Customer customer) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (customer.getCuCredit() != null) {
            if (customer.getCuCredit().doubleValue() < -99999999.99 || customer.getCuCredit().doubleValue() > 99999999.99) {
                checker.setSuccess(false);
                checker.setReturnStr("Credit必须在[-99999999.99, 99999999.99]范围内");
                return checker;
            }
        }
        return checker;
    }

    @Override
    public Checker checkCustomer(Customer customer) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (customer != null) {
            if (customer.getCuCode() == null || customer.getCuCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Customer Code, 保存失败");
            } else {
                Integer count = customerMapper.getCountByFkCuCode(customer.getCuCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Customer Code已存在");
                } else {

                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkCustAttn(CustAttn custAttn, Customer customer) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (custAttn != null) {
            if (custAttn.getAttention() == null || custAttn.getAttention().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Attn., 保存失败");
            } else {
                if (customer.getCustAttns() != null) {
                    if (customer.getCustAttns().containsKey(custAttn.getEncodeAttention())) {
                        checker.setSuccess(false);
                        checker.setReturnStr("该Customer已含有此Attn.");
                    }
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Map<String, CustAttn> removeCustAttn(String fkAttn,
                                                Map<String, CustAttn> custAttns) {
        if (custAttns != null && custAttns.size() > 0) {
            custAttns.remove(fkAttn);
        }
        return custAttns;
    }
}
