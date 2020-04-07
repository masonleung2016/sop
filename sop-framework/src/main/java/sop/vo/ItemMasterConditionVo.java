package sop.vo;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:40
 * @Package: sop.vo
 */

public class ItemMasterConditionVo implements Serializable {

    private static final long serialVersionUID = 6237430476528427602L;

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
