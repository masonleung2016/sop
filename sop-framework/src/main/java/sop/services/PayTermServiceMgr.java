package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.PayTerm;
import sop.vo.PayTermVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:07
 * @Package: sop.services
 */

public interface PayTermServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "payTermServiceMgr";

    List<PayTermVo> searchPayTerm(BaseConditionVO vo);

    Integer searchPayTermNum(BaseConditionVO vo);

    boolean addPayTerm(PayTerm payTerm) throws ServiceException;

    PayTerm getPayTerm(String code);

    boolean updatePayTerm(PayTerm payTerm) throws ServiceException;

    Checker checkPaytm(PayTerm payTerm);

    boolean deletePaymentTerm(String payCode) throws ServiceException;

    Checker checkDecimal(PayTerm payTerm);

}
