package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Uom;
import sop.vo.UomVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:15
 * @Package: sop.services
 */

public interface UomServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "uomServiceMgr";

    List<UomVo> getUomListByCondition(BaseConditionVO vo);

    void getUomListNum(BaseConditionVO vo);

    Checker checkUom(Uom uom);

    boolean addUom(Uom uom) throws ServiceException;

    Uom getUomByFkuCode(String fkuCode);

    boolean updateUom(Uom uom) throws ServiceException;

    boolean deleteUom(String fkuCode) throws ServiceException;
    
}
