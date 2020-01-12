package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.GlInterface;
import sop.vo.GlInterfaceVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:01
 * @Package: sop.services
 */

public interface GlInterfaceServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "glinterfaceServiceMgr";

    List<GlInterfaceVo> getGlInterfaceListByCondition(BaseConditionVO vo);

    void getGlInterfaceListNum(BaseConditionVO vo);

    GlInterface getfkGlInfCodeById(String fkGlInfCode);

    boolean updateGlInterface(GlInterface currentGlInterface);

    Checker checkGlInterface(GlInterface currentGlInterface);

    boolean insertGlInterface(GlInterface currentGlInterface);

    boolean deleteGlInterface(String fkGlInfCode);

    Checker checkGlInterfaceDecimal(GlInterface currentGlInterface);
}
