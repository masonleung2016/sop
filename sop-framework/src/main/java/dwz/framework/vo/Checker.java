package dwz.framework.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:17
 * @Package: dwz.framework.vo
 */

public class Checker {
    private boolean success;
    private String returnStr;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnStr() {
        return returnStr;
    }

    public void setReturnStr(String returnStr) {
        this.returnStr = returnStr;
    }
}
