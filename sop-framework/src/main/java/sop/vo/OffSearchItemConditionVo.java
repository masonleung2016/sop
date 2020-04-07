package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:44
 * @Package: sop.vo
 */


public class OffSearchItemConditionVo extends SearchItemConditionVo {
    /**
     * search list condition
     */
    private String module;
    
    private String fromPrefix;
    
    private String fromNo;
    
    private String toPrefix;
    
    private String toNo;

    public String getFromPrefix() {
        return fromPrefix;
    }

    public void setFromPrefix(String fromPrefix) {
        this.fromPrefix = fromPrefix;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getToPrefix() {
        return toPrefix;
    }

    public void setToPrefix(String toPrefix) {
        this.toPrefix = toPrefix;
    }

    public String getToNo() {
        return toNo;
    }

    public void setToNo(String toNo) {
        this.toNo = toNo;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
