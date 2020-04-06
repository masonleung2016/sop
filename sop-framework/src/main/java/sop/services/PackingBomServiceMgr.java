package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.PackingBom;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:05
 * @Package: sop.services
 */

public interface PackingBomServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "packingBomServiceMgr";

    List<PackingBom> getPackingBomListByCondition(BaseConditionVO vo);

    void getPackingBomListNum(BaseConditionVO vo);

    Checker checkPackingBom(PackingBom PackingBom);

    boolean addPackingBom(PackingBom PackingBom) throws ServiceException;

    PackingBom getPackingBomByNo(String pbNo);

    boolean updatePackingBom(PackingBom currentPackingBom) throws ServiceException;

    boolean deletePackingBom(String pbNo) throws ServiceException;

    List<PackingBom> getAllPackingBoms();
}
