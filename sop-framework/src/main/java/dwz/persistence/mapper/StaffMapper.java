package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Staff;
import sop.vo.StaffVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:31
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface StaffMapper extends BaseMapper<Staff, Integer> {

    List<StaffVo> getStaffListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getStaffListNumByCondition(BaseConditionVO vo);

    Staff getStaffByFkSfCode(String fkSfCode);

    void updateStaff(Staff currentStaff);

    Integer getCountByFkSfCode(String sfCode);

    Integer insertStaff(Staff currentStaff);

    Integer deleteStaff(String fkSfCode);

    List<StaffVo> getAllStaffs();

}
