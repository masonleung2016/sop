package sop.persistence.beans;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:09
 * @Package: sop.persistence.beans
 */

public class SysUser extends AbstractDO {
    /**
     * table users
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String coCode;
    private String usrId;
    private String usrName;
    private String usrPass;
    private String usrLevel;

    private String usrRole;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrPass() {
        return usrPass;
    }

    public void setUsrPass(String usrPass) {
        this.usrPass = usrPass;
    }

    public String getUsrLevel() {
        return usrLevel;
    }

    public void setUsrLevel(String usrLevel) {
        this.usrLevel = usrLevel;
    }

    public String getUsrRole() {
        return usrRole;
    }

    public void setUsrRole(String usrRole) {
        this.usrRole = usrRole;
    }


}
