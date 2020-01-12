package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import sop.persistence.beans.Factory;
import sop.vo.FactoryComboVo;
import sop.vo.FactoryConditionVo;
import sop.vo.FactoryVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:01
 * @Package: sop.services
 */


public interface FactoryServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "factoryServiceMgr";

    List<FactoryVo> searchFactory(FactoryConditionVo vo);

    Integer searchFactoryNum(FactoryConditionVo vo);

    void addFactory(Factory factory) throws ServiceException;

    Factory getFactoryBySuCode(String suCode);

    void updateFactory(Factory factory) throws ServiceException;

    boolean deleteFactory(String fkSuCode) throws ServiceException;

    FactoryComboVo getFactoryComboVo();

    Checker checkFactoryDecimal(Factory factory);

    Checker checkFactory(Factory factory);

    Checker checkDecimal(Factory factory);

    String getLatestFactoryCodeByPrefix(String getCodeByPrefix);


}
