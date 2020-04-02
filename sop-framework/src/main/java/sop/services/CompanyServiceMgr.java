package sop.services;

import java.util.List;
import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Company;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:59
 * @Package: sop.services
 */

public interface CompanyServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "comServiceMgr";

    List<CompanyVo> getComListByCondition(BaseConditionVO vo);

    void getComListNum(BaseConditionVO vo);

    Company getCompanyById(String fkCoCode);

    boolean updateCompany(Company currentCom);

    Checker checkCompany(Company currentCom);

    boolean insertCompany(Company currentCom);

    boolean deleteCompany(String fkCoCode);
}
