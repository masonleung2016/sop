package dwz.common.util.encrypt;

import java.util.Date;

import dwz.framework.services.impl.LicenseServiceMgrImpl;
import dwz.framework.vo.AuthorizationVo;
import dwz.persistence.beans.License;
import dwz.persistence.mapper.LicenseMapper;

/**
 * 这个为是为检测是否已经注册，过期，提示警告信息
 *
 * @Author: LCF
 * @Date: 2020/1/8 15:23
 * @Package: dwz.common.util.encrypt
 */

public class AuthorizationUtil {

    private static AuthorizationUtil instance = null;
    public Date installDate;
    public String limitedTime;
    public Date expiryDate;

    public String licenseValue;

    /**
     * 私有默认构造方法
     */
    private AuthorizationUtil() {
    }

    /**
     * 静态工厂方法
     */
    public static synchronized AuthorizationUtil getInstance() {
        if (instance == null) {
            instance = new AuthorizationUtil();
        }
        return instance;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public String getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(String limitedTime) {
        this.limitedTime = limitedTime;
    }

    public String getLicenseValue() {
        return licenseValue;
    }

    public void setLicenseValue(String licenseValue) {
        this.licenseValue = licenseValue;
    }


    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * server启动的时候会初始化
     */
    public void init() {
        String licenseValue = getLicenseFromDb();
        this.setLicenseValue(licenseValue);

        Date expiryDate = getExpiryDateFromDb(licenseValue);
        this.setExpiryDate(expiryDate);
    }

    /**
     * @return licenseValue
     */
    public String getLicenseFromDb() {
        //从db拿数据出来是否有license
        LicenseServiceMgrImpl licenseMgr = new LicenseServiceMgrImpl();
        LicenseMapper licenseMapper = new LicenseServiceMgrImpl();
        License licenseVo = licenseMgr.getLatestLicenseVo();
        if (licenseVo == null) {
            return null;
        } else {
            String licenseValue = licenseVo.getValue();
            return licenseValue;
        }
    }

    /**
     * 从LicenseValue中拿到expireDate.
     *
     * @param licenseValue
     * @return expiryDate
     */
    private Date getExpiryDateFromDb(String licenseValue) {
        Date expiryDate;
        LicneseGeneratorUtil licenseGeneratorUtil = new LicneseGeneratorUtil();
        expiryDate = licenseGeneratorUtil.getExpiryDateFromDb(licenseValue);

        return expiryDate;
    }

    private boolean isRegister() {
        if (this.licenseValue == null) {
//			还没有注册过
            return false;
        } else {
//			说明之前注册过
            return true;
        }
    }

    private boolean isExpired() {
//		if(this.expiryDate.before(now))
        return false;
    }

    private boolean isNotiFyInAdvance() {
        //判断是否在通知时间内
        return true;
    }

    private int getLeavingDate() {
        this.expiryDate - now
        return 2;
    }

    /**
     * 返回授权信息给前台处理
     *
     * @return AuthorizationVo
     */
    public AuthorizationVo getAuthorizationMsg() {
        AuthorizationVo av = new AuthorizationVo();
        if (!isRegister()) {
            av.setbRegister(false);
            av.setMsg("你还没有注册，请注册");
        } else {
            av.setbRegister(true);
            if (isExpired()) {
                av.setbExpired(true);
                av.setMsg("你已经注册过期，请重新注册");
            } else {
                av.setbExpired(false);
                if (!isNotiFyInAdvance()) {
                    av.setbNotified(false);

                    av.setbAvailable(true);
                } else {
                    av.setbNotified(true);
                    av.setLeavingDate(getLeavingDate());
                    av.setMsg("你还剩下" + av.getLeavingDate());
                }
            }
        }
        return av;

    }

}
