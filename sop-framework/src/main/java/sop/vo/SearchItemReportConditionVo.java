package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:37
 * @Package: sop.vo
 */

public class SearchItemReportConditionVo extends PoSoSearchItemReportConditionVo {
    /**
     * search list condition
     */

    private String fromItCat;
    private String fromItNo;
    private String fromItSuffix;

    private String toItCat;
    private String toItNo;
    private String toItSuffix;

    private String fromItCatNoSuffix;
    private String toItCatNoSuffix;

    public String getFromItCat() {
        return fromItCat;
    }

    public void setFromItCat(String fromItCat) {
        this.fromItCat = fromItCat;
    }

    public String getFromItNo() {
        return fromItNo;
    }

    public void setFromItNo(String fromItNo) {
        this.fromItNo = fromItNo;
    }

    public String getFromItSuffix() {
        return fromItSuffix;
    }

    public void setFromItSuffix(String fromItSuffix) {
        this.fromItSuffix = fromItSuffix;
    }

    public String getToItCat() {
        return toItCat;
    }

    public void setToItCat(String toItCat) {
        this.toItCat = toItCat;
    }

    public String getToItNo() {
        return toItNo;
    }

    public void setToItNo(String toItNo) {
        this.toItNo = toItNo;
    }

    public String getToItSuffix() {
        return toItSuffix;
    }

    public void setToItSuffix(String toItSuffix) {
        this.toItSuffix = toItSuffix;
    }

    public String getFromItCatNoSuffix() {
        return fromItCatNoSuffix;
    }

    public void setFromItCatNoSuffix(String fromItCatNoSuffix) {
        this.fromItCatNoSuffix = fromItCatNoSuffix;
    }

    public String getToItCatNoSuffix() {
        return toItCatNoSuffix;
    }

    public void setToItCatNoSuffix(String toItCatNoSuffix) {
        this.toItCatNoSuffix = toItCatNoSuffix;
    }
}
