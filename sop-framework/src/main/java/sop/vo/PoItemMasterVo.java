package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:45
 * @Package: sop.vo
 */


public class PoItemMasterVo extends SoItemMasterVo {

    private String poNo;

    public PoItemMasterVo() {

    }

    public PoItemMasterVo(String no, String itCat,
                          String itNo, String itSuffix, String module) {
        if (module.equals("so")) {
            setSoNo(no);
        } else if (module.equals("po")) {
            setPoNo(no);
        }

        setItCat(itCat);
        setItNo(itNo);
        setItSuffix(itSuffix);
    }

    public PoItemMasterVo(String offPre, String offNo, String itCat,
                          String itNo, String itSuffix, String module) {
        setOffShNoPfix(offPre);
        setOffShNo(offNo);
        setItCat(itCat);
        setItNo(itNo);
        setItSuffix(itSuffix);
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

}
