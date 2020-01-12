package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Autogen;
import sop.vo.AutogenVo;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:56
 * @Package: sop.services.impl
 */

public interface AutogenServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "autogenServiceMgr";

    List<AutogenVo> getAutogenListByCondition(BaseConditionVO vo);

    void getAutogenListNum(BaseConditionVO vo);

    Checker checkAutogen(Autogen autogen, boolean isUpdate);

    boolean addAutogen(Autogen autogen) throws ServiceException;

    List<CompanyVo> getAllCompany();

    Autogen getAutogen(String coCode, String autTxType);

    boolean deleteAutogen(String coCode, String autTxType) throws ServiceException;


    boolean updateAutogen(Autogen autogen) throws ServiceException;
}
