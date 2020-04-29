package sop.vo;

import java.util.Map;

import sop.persistence.beans.SaleOrder;
import sop.persistence.beans.SaleOrderCharge;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.SaleOrderLc;
import sop.persistence.beans.SaleOrderSbk;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:56
 * @Package: sop.vo
 */

public class SaleOrderDetailsVo extends SaleOrder {

    private static final long serialVersionUID = -7730194593419089855L;
    
    private Map<String, SaleOrderItem> saleOrderItems;
    
    private boolean haveLc = false;
    
    private SaleOrderLc saleOrderLc;
    
    private Map<String, SaleOrderLc> saleOrderLcs;
    
    private boolean haveCharge = false;
    
    private SaleOrderCharge saleOrderCharge;
    
    private Map<String, SaleOrderCharge> saleOrderCharges;
    
    private boolean haveSbk = false;
    
    private SaleOrderSbk saleOrderSbk;
    
    private Map<String, SaleOrderSbk> saleOrderSbks;

    public SaleOrderDetailsVo() {

    }

    public SaleOrderDetailsVo(SaleOrder saleOrder) {
        
        if (saleOrder != null) {
            
            setCoCode(saleOrder.getCoCode());
            
            setSoBnkElc(saleOrder.getSoBnkElc());
            
            setSoBnkTf(saleOrder.getSoBnkTf());
            
            setSoCnf(saleOrder.getSoCnf());
            
            setSoCnfPort(saleOrder.getSoCnfPort());
            
            setSoContReq(saleOrder.getSoContReq());
            
            setSoCuCode(saleOrder.getSoCuCode());
            
            setSoCuName(saleOrder.getSoCuName());
            
            setSoCuPoNo(saleOrder.getSoCuPoNo());
            
            setSoCurr(saleOrder.getSoCurr());
            
            setSoDate(saleOrder.getSoDate());
            
            setSoDelDetails(saleOrder.getSoDelDetails());
            
            setSoDepDate(saleOrder.getSoDepDate());
            
            setSoDepPaid(saleOrder.getSoDepPaid());
            
            setSoDepRatio(saleOrder.getSoDepRatio());
            
            setSoDest(saleOrder.getSoDest());
            
            setSoDestFinal(saleOrder.getSoDestFinal());
            
            setSoEtd(saleOrder.getSoEtd());
            
            setSoFob(saleOrder.getSoFob());
            
            setSoFobPort(saleOrder.getSoFobPort());
            
            setSoHandle(saleOrder.getSoHandle());
            
            setSoLshpDate(saleOrder.getSoLshpDate());
            
            setSoNo(saleOrder.getSoNo());
            
            setSoOdtlProf(saleOrder.getSoOdtlProf());
            
            setSoOrdAmtWord(saleOrder.getSoOrdAmtWord());
            
            setSoOrdTotAmt(saleOrder.getSoOrdTotAmt());
            
            setSoOrdTotChg(saleOrder.getSoOrdTotChg());
            
            setSoOrdTotNet(saleOrder.getSoOrdTotNet());
            
            setSoPaidStatus(saleOrder.getSoPaidStatus());
            
            setSoPayTerm(saleOrder.getSoPayTerm());
            
            setSoPterm(saleOrder.getSoPterm());
            
            setSoRouting(saleOrder.getSoRouting());
            
            setSoStatus(saleOrder.getSoStatus());
            
            setSoSuCode(saleOrder.getSoSuCode());
            
            setSoSuConNo(saleOrder.getSoSuConNo());
            
            setSoSuName(saleOrder.getSoSuName());
            
            setSoSuPterm(saleOrder.getSoSuPterm());
            
            setSoPtermDays(saleOrder.getSoPtermDays());
            
            setCrtDate(saleOrder.getCrtDate());
            
            setCrtUsr(saleOrder.getCrtUsr());
            
            setModDate(saleOrder.getModDate());
            
            setModUsr(saleOrder.getModUsr());
        }
    }

    public Map<String, SaleOrderItem> getSaleOrderItems() {
        return saleOrderItems;
    }

    public void setSaleOrderItems(Map<String, SaleOrderItem> saleOrderItems) {
        this.saleOrderItems = saleOrderItems;
    }

    public boolean isHaveLc() {
        return haveLc;
    }

    public void setHaveLc(boolean haveLc) {
        this.haveLc = haveLc;
    }

    public SaleOrderLc getSaleOrderLc() {
        return saleOrderLc;
    }

    public void setSaleOrderLc(SaleOrderLc saleOrderLc) {
        this.saleOrderLc = saleOrderLc;
    }

    public boolean isHaveCharge() {
        return haveCharge;
    }

    public void setHaveCharge(boolean haveCharge) {
        this.haveCharge = haveCharge;
    }

    public SaleOrderCharge getSaleOrderCharge() {
        return saleOrderCharge;
    }

    public void setSaleOrderCharge(SaleOrderCharge saleOrderCharge) {
        this.saleOrderCharge = saleOrderCharge;
    }

    public boolean isHaveSbk() {
        return haveSbk;
    }

    public void setHaveSbk(boolean haveSbk) {
        this.haveSbk = haveSbk;
    }

    public SaleOrderSbk getSaleOrderSbk() {
        return saleOrderSbk;
    }

    public void setSaleOrderSbk(SaleOrderSbk saleOrderSbk) {
        this.saleOrderSbk = saleOrderSbk;
    }

    public Map<String, SaleOrderLc> getSaleOrderLcs() {
        return saleOrderLcs;
    }

    public void setSaleOrderLcs(Map<String, SaleOrderLc> saleOrderLcs) {
        this.saleOrderLcs = saleOrderLcs;
    }

    public Map<String, SaleOrderCharge> getSaleOrderCharges() {
        return saleOrderCharges;
    }

    public void setSaleOrderCharges(Map<String, SaleOrderCharge> saleOrderCharges) {
        this.saleOrderCharges = saleOrderCharges;
    }

    public Map<String, SaleOrderSbk> getSaleOrderSbks() {
        return saleOrderSbks;
    }

    public void setSaleOrderSbks(Map<String, SaleOrderSbk> saleOrderSbks) {
        this.saleOrderSbks = saleOrderSbks;
    }
}
