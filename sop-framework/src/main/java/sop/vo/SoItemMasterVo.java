package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:38
 * @Package: sop.vo
 */

public class SoItemMasterVo extends OffItemMasterVo {

    private String soNo;

    public SoItemMasterVo() {

    }

    public SoItemMasterVo(String soNo, String itCat,
                          String itNo, String itSuffix) {
        this.soNo = soNo;
        setItCat(itCat);
        setItNo(itNo);
        setItSuffix(itSuffix);
    }

    public SoItemMasterVo(String offPre, String offNo, String itCat,
                          String itNo, String itSuffix) {
        setOffShNoPfix(offPre);
        setOffShNo(offNo);
        setItCat(itCat);
        setItNo(itNo);
        setItSuffix(itSuffix);
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }
}
