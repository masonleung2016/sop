package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.PackingDetail;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:05
 * @Package: sop.services
 */

public interface PackingDetailServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "packingDetailServiceMgr";

    List<PackingDetail> getPackingDetailListByCondition(BaseConditionVO vo);

    void getPackingDetailListNum(BaseConditionVO vo);

    Checker checkPackingDetail(PackingDetail PackingDetail);

    boolean addPackingDetail(PackingDetail PackingDetail) throws ServiceException;

    PackingDetail getPackingDetailByNo(String pbNo);

    boolean updatePackingDetail(PackingDetail currentPackingDetail) throws ServiceException;

    boolean deletePackingDetail(String pbNo) throws ServiceException;

    List<PackingDetail> getAllPackingDetails();
}
