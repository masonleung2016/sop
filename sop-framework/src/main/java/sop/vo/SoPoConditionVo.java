package sop.vo;

import dwz.persistence.BaseConditionVO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:38
 * @Package: sop.vo
 */

public class SoPoConditionVo extends BaseConditionVO {
    
    private String fromNo;
    
    private String toNo;

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getToNo() {
        return toNo;
    }

    public void setToNo(String toNo) {
        this.toNo = toNo;
    }
}
