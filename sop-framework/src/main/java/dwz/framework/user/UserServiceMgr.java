package dwz.framework.user;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.SysUser;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:16
 * @Package: dwz.framework.user
 */

public interface UserServiceMgr {
    String SERVICE_NAME = "userServiceMgr";

    void updUser(SysUser user);

    void delUser(SysUser user);


    User getUserByUsername(String username);

    User getUserByUsernameAndCocode(String username, String coCode);

    List<String> getCoCodes();

    List<SysUser> getAllUsers();

    void insertUsr(SysUser user);

    List<SysUser> searchUser(BaseConditionVO vo);

    int searchUserNum(BaseConditionVO vo);
}
