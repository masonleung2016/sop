package sop.vo;

import java.util.List;

import sop.persistence.beans.Currency;
import sop.persistence.beans.ItemType;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:40
 * @Package: sop.vo
 */

public class ItemMasterComboVo {
    
    private List<ItemType> itemTypes;
    
    private List<UomVo> uoms;
    
    private List<Currency> currs;
    
    private List<StaffVo> staffs;
    
    private List<FactoryVo> factories;
    
    private List<ProductCategoryVo> productCats;

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public List<Currency> getCurrs() {
        return currs;
    }

    public void setCurrs(List<Currency> currs) {
        this.currs = currs;
    }

    public List<UomVo> getUoms() {
        return uoms;
    }

    public void setUoms(List<UomVo> uoms) {
        this.uoms = uoms;
    }

    public List<StaffVo> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<StaffVo> staffs) {
        this.staffs = staffs;
    }

    public List<FactoryVo> getFactories() {
        return factories;
    }

    public void setFactories(List<FactoryVo> factories) {
        this.factories = factories;
    }

    public List<ProductCategoryVo> getProductCats() {
        return productCats;
    }

    public void setProductCats(List<ProductCategoryVo> productCats) {
        this.productCats = productCats;
    }
}
