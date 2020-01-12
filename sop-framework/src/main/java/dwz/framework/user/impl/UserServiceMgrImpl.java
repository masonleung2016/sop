package dwz.framework.user.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.user.User;
import dwz.framework.user.UserServiceMgr;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.SysUserMapper;
import sop.persistence.beans.SysUser;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:15
 * @Package: dwz.framework.user.impl
 */

@Transactional(rollbackFor = Exception.class)
@Service(UserServiceMgr.SERVICE_NAME)
public class UserServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements UserServiceMgr {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void updUser(SysUser user) {
        sysUserMapper.updateUsr(user);
    }

    @Override
    public void delUser(SysUser user) {
        sysUserMapper.deleteUser(user);
    }


    @Override
    public User getUserByUsername(String username) {
        SysUser po = sysUserMapper.getUserByUsernameAndCocode(username, "RRR");

        if (po == null) return null;

        return new User(po);
    }

    @Override
    public User getUserByUsernameAndCocode(String username, String coCode) {
        SysUser po = sysUserMapper.getUserByUsernameAndCocode(username, coCode);
        if (po == null) return null;
        return new User(po);
    }

    @Override
    public List<String> getCoCodes() {
        return sysUserMapper.getCoCodes();
    }

    @Override
    public List<SysUser> getAllUsers() {
        List<SysUser> allUsers = sysUserMapper.findAll();
        return allUsers;
    }

    @Override
    public void insertUsr(SysUser user) {
        sysUserMapper.insertUsr(user);
    }

    @Override
    public List<SysUser> searchUser(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());

        return sysUserMapper.findPageBreakByCondition(vo, rb);
    }

    @Override
    public int searchUserNum(BaseConditionVO vo) {
        return sysUserMapper.searchUserNum(vo);
    }


}
