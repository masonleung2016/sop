package sop.vo;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:41
 * @Package: sop.vo
 */

public class ItemMasterVo {
    
    private Integer id;
    
    /**
     * it_cat_no_suffix
     */
    private String itCatNoSuffix;

    /**
     * it_cat
     */
    private String itCat;

    /**
     * it_no
     */
    private String itNo;

    /**
     * it_suffix
     */
    private String itSuffix;

    /**
     * it_name
     */
    private String itName;
    /**
     * it_image_1
     */
    private String itImage1;

    /**
     * it_image_2
     */
    private String itImage2;

    /**
     * it_image_3
     */
    private String itImage3;

    /**
     * it_image_4
     */
    private String itImage4;
    
    private String itDelTime;
    
    private BigDecimal itBasePrice;
    
    private String itCurr;
    
    private String itHandle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItCatNoSuffix() {
        return itCatNoSuffix;
    }

    public void setItCatNoSuffix(String itCatNoSuffix) {
        this.itCatNoSuffix = itCatNoSuffix;
    }

    public String getItCat() {
        return itCat;
    }

    public void setItCat(String itCat) {
        this.itCat = itCat;
    }

    public String getItNo() {
        return itNo;
    }

    public void setItNo(String itNo) {
        this.itNo = itNo;
    }

    public String getItSuffix() {
        return itSuffix;
    }

    public void setItSuffix(String itSuffix) {
        this.itSuffix = itSuffix;
    }

    public String getItName() {
        return itName;
    }

    public void setItName(String itName) {
        this.itName = itName;
    }

    public String getItImage1() {
        return itImage1;
    }

    public void setItImage1(String itImage1) {
        this.itImage1 = itImage1;
    }

    public String getItImage2() {
        return itImage2;
    }

    public void setItImage2(String itImage2) {
        this.itImage2 = itImage2;
    }

    public String getItImage3() {
        return itImage3;
    }

    public void setItImage3(String itImage3) {
        this.itImage3 = itImage3;
    }

    public String getItImage4() {
        return itImage4;
    }

    public void setItImage4(String itImage4) {
        this.itImage4 = itImage4;
    }

    public String getItDelTime() {
        return itDelTime;
    }

    public void setItDelTime(String itDelTime) {
        this.itDelTime = itDelTime;
    }

    public BigDecimal getItBasePrice() {
        return itBasePrice;
    }

    public void setItBasePrice(BigDecimal itBasePrice) {
        this.itBasePrice = itBasePrice;
    }

    public String getItCurr() {
        return itCurr;
    }

    public void setItCurr(String itCurr) {
        this.itCurr = itCurr;
    }

    public String getItHandle() {
        return itHandle;
    }

    public void setItHandle(String itHandle) {
        this.itHandle = itHandle;
    }
}
