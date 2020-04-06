package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Staff;
import sop.vo.StaffVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:13
 * @Package: sop.services
 */

public interface StaffServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "staffServiceMgr";

    List<StaffVo> getStaffListByCondition(BaseConditionVO vo);

    void getStaffListNum(BaseConditionVO vo);

    Staff getStaffById(String fkSfCode);

    boolean updateStaff(Staff currentStaff);

    Checker checkStaff(Staff currentStaff);

    boolean insertStaff(Staff currentStaff);

    boolean deleteStaff(String fkSfCode);
    
}
