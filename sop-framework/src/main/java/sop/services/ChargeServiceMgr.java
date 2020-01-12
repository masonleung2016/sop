package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Charge;
import sop.vo.ChargeVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:58
 * @Package: sop.services
 */

public interface ChargeServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "chargeServiceMgr";

    List<ChargeVo> getChargeListByCondition(BaseConditionVO vo);

    void getChargeListNum(BaseConditionVO vo);

    Checker checkCharge(Charge charge);

    boolean addCharge(Charge charge) throws ServiceException;

    Charge getChargeByFkChgCode(String fkChgCode);

    boolean updateCharge(Charge currentCharge) throws ServiceException;

    boolean deleteCharge(String fkChgCode) throws ServiceException;

    Checker checkChargeDecimal(Charge currentCharge);

    List<ChargeVo> getAllCharges();
}
