package sop.vo;

import java.util.List;

import sop.persistence.beans.Currency;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:48
 * @Package: sop.vo
 */

public class PurchaseOrderCombos {
    private List<Currency> currs;
    private List<FactoryVo> factories;
    private List<PayTermVo> payTerms;

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
}
