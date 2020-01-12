package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.SysUser;
import sop.vo.CustomerVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:32
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface SysUserMapper {

    SysUser getUserByUsernameAndCocode(@Param("usrId") String username, @Param("coCode") String coCode);

    List<String> getCoCodes();

    void deleteUser(SysUser user);

    void insertUsr(SysUser user);

    void updateUsr(SysUser user);

    List<SysUser> findAll();

    List<SysUser> findAllByRole(String role);

    List<SysUser> searchUser(BaseConditionVO vo);

    List<SysUser> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    int searchUserNum(BaseConditionVO vo);
}
