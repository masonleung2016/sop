package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:43
 * @Package: sop.vo
 */


public class OffItemMasterVo extends ItemMasterVo {

    private String offShNoPfix;
    private String offShNo;

    public OffItemMasterVo() {

    }

    public OffItemMasterVo(String offPre, String offNo, String itCat,
                           String itNo, String itSuffix) {
        this.offShNoPfix = offPre;
        this.offShNo = offNo;
        setItCat(itCat);
        setItNo(itNo);
        setItSuffix(itSuffix);
    }

    public String getOffShNoPfix() {
        return offShNoPfix;
    }

    public void setOffShNoPfix(String offShNoPfix) {
        this.offShNoPfix = offShNoPfix;
    }

    public String getOffShNo() {
        return offShNo;
    }

    public void setOffShNo(String offShNo) {
        this.offShNo = offShNo;
    }
}
