package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:46
 * @Package: sop.vo
 */

public class PoSearchItemConditionVo extends SoSearchItemConditionVo {

    private String fromPoNo;

    private String toPoNo;

    public String getFromPoNo() {
        return fromPoNo;
    }

    public void setFromPoNo(String fromPoNo) {
        this.fromPoNo = fromPoNo;
    }

    public String getToPoNo() {
        return toPoNo;
    }

    public void setToPoNo(String toPoNo) {
        this.toPoNo = toPoNo;
    }
}
