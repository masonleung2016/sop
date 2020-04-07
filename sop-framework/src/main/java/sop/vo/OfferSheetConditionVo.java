package sop.vo;

import dwz.persistence.BaseConditionVO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:43
 * @Package: sop.vo
 */

public class OfferSheetConditionVo extends BaseConditionVO {
    
    private String fromPrefix;
    
    private String fromNo;
    
    private String toPrefix;
    
    private String toNo;
    
    private String from;
    
    private String to;

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
