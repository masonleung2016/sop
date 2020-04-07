package sop.vo;

import java.util.List;

import sop.persistence.beans.CustAttn;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:42
 * @Package: sop.vo
 */

public class OfferSheetCombos {
    
    private List<TxnVo> txns;
    
    private List<CustomerVo> customers;
    
    private List<CustAttn> custAttns;

    public List<TxnVo> getTxns() {
        return txns;
    }

    public void setTxns(List<TxnVo> txns) {
        this.txns = txns;
    }

    public List<CustomerVo> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerVo> customers) {
        this.customers = customers;
    }

    public List<CustAttn> getCustAttns() {
        return custAttns;
    }

    public void setCustAttns(List<CustAttn> custAttns) {
        this.custAttns = custAttns;
    }
}
