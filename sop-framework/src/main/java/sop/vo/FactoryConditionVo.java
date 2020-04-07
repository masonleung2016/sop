package sop.vo;

import dwz.persistence.BaseConditionVO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:37
 * @Package: sop.vo
 */

public class FactoryConditionVo extends BaseConditionVO {
    /**
     * search list condition
     */
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
