package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:39
 * @Package: sop.vo
 */

public class SoSearchItemConditionVo extends OffSearchItemConditionVo {

    private String fromSoNo;

    private String toSoNo;

    public String getFromSoNo() {
        return fromSoNo;
    }

    public void setFromSoNo(String fromSoNo) {
        this.fromSoNo = fromSoNo;
    }

    public String getToSoNo() {
        return toSoNo;
    }

    public void setToSoNo(String toSoNo) {
        this.toSoNo = toSoNo;
    }
}
