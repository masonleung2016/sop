package sop.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.StaffMapper;
import sop.persistence.beans.Staff;
import sop.services.StaffServiceMgr;
import sop.vo.StaffVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:54
 * @Package: sop.services.impl
 */


@Service(StaffServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class StaffServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements StaffServiceMgr {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<StaffVo> getStaffListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<StaffVo> staffVoList = staffMapper.getStaffListByCondition(vo, rb);
        return staffVoList;
    }

    @Override
    public void getStaffListNum(BaseConditionVO vo) {
        Integer count = staffMapper.getStaffListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Staff getStaffById(String fkSfCode) {
        Staff currentStaff = staffMapper.getStaffByFkSfCode(fkSfCode);
        return currentStaff;
    }

    @Override
    public boolean updateStaff(Staff currentStaff) {
        try {
            Date date = new Date();
            currentStaff.setModDate(date);
            staffMapper.updateStaff(currentStaff);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Checker checkStaff(Staff currentStaff) {
        Checker checker = new Checker();
        if (currentStaff != null) {
            if (currentStaff.getSfCode() == null || currentStaff.getSfCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("Staff Code不能为空,保存失败");
            } else {
                Integer count = staffMapper.getCountByFkSfCode(currentStaff.getSfCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Staff Code已存在,请使用其他ID");
                } else {
                    checker.setSuccess(true);
                    checker.setReturnStr("信息无误");
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean insertStaff(Staff currentStaff) {
        try {
            Date date = new Date();
            currentStaff.setCrtDate(date);
            currentStaff.setModDate(date);
            Integer flag = staffMapper.insertStaff(currentStaff);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteStaff(String fkSfCode) {
        Integer flag = staffMapper.deleteStaff(fkSfCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

}
