package sop.vo;

import java.util.List;

import sop.persistence.beans.Currency;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:56
 * @Package: sop.vo
 */


public class SaleOrderCombos {
    private List<CustomerVo> customers;
    private List<Currency> currs;
    private List<FactoryVo> factories;
    private List<PayTermVo> payTerms;
    private List<StaffVo> staffs;

    public List<CustomerVo> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerVo> customers) {
        this.customers = customers;
    }

    public List<Currency> getCurrs() {
        return currs;
    }

    public void setCurrs(List<Currency> currs) {
        this.currs = currs;
    }

    public List<FactoryVo> getFactories() {
        return factories;
    }

    public void setFactories(List<FactoryVo> factories) {
        this.factories = factories;
    }

    public List<PayTermVo> getPayTerms() {
        return payTerms;
    }

    public void setPayTerms(List<PayTermVo> payTerms) {
        this.payTerms = payTerms;
    }

    public List<StaffVo> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<StaffVo> staffs) {
        this.staffs = staffs;
    }
}
