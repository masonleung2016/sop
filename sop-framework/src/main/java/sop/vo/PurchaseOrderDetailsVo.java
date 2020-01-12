package sop.vo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sop.persistence.beans.PurchaseOrder;
import sop.persistence.beans.PurchaseOrderCharge;
import sop.persistence.beans.PurchaseOrderItem;
import sop.persistence.beans.PurchaseOrderLc;
import sop.persistence.beans.PurchaseOrderSbk;
import sop.persistence.beans.SaleOrderCharge;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.SaleOrderLc;
import sop.persistence.beans.SaleOrderSbk;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:49
 * @Package: sop.vo
 */


public class PurchaseOrderDetailsVo extends PurchaseOrder {

    /**
     * PO 组合VO
     */
    private static final long serialVersionUID = -7730194593419089855L;
    private Map<String, PurchaseOrderItem> purchaseOrderItems;
    private boolean haveLc = false;
    private PurchaseOrderLc purchaseOrderLc;
    private Map<String, PurchaseOrderLc> purchaseOrderLcs;
    private boolean haveCharge = false;
    private PurchaseOrderCharge purchaseOrderCharge;
    private Map<String, PurchaseOrderCharge> purchaseOrderCharges;
    private boolean haveSbk = false;
    private PurchaseOrderSbk purchaseOrderSbk;
    private Map<String, PurchaseOrderSbk> purchaseOrderSbks;

    public PurchaseOrderDetailsVo() {

    }

    public PurchaseOrderDetailsVo(PurchaseOrder purchaseOrder) {
        if (purchaseOrder != null) {
            setPoArtArtNo(purchaseOrder.getPoArtArtNo());
            setPoArtEanNo(purchaseOrder.getPoArtEanNo());
            setPoArtGeneral(purchaseOrder.getPoArtGeneral());
            setPoArtGrnPoint(purchaseOrder.getPoArtGrnPoint());
            setPoArtLang(purchaseOrder.getPoArtLang());
            setPoArtOther(purchaseOrder.getPoArtOther());
            setPoArtResy(purchaseOrder.getPoArtResy());
            setPoCnf(purchaseOrder.getPoCnf());
            setPoCnfPort(purchaseOrder.getPoCnfPort());
            setPoContReq(purchaseOrder.getPoContReq());
            setPoCtnPkgDesc(purchaseOrder.getPoCtnPkgDesc());
            setPoCurr(purchaseOrder.getPoCurr());
            setPoDate(purchaseOrder.getPoDate());
            setPoDelDetails(purchaseOrder.getPoDelDetails());
            setPoDepDate(purchaseOrder.getPoDepDate());
            setPoDepPaid(purchaseOrder.getPoDepPaid());
            setPoDepRatio(purchaseOrder.getPoDepRatio());
            setPoDest(purchaseOrder.getPoDest());
            setPoDtlArtNo(purchaseOrder.getPoDtlArtNo());
            setPoDtlEanNo(purchaseOrder.getPoDtlEanNo());
            setPoDtlGeneral(purchaseOrder.getPoDtlGeneral());
            setPoDtlGrnPoint(purchaseOrder.getPoDtlGrnPoint());
            setPoDtlLang(purchaseOrder.getPoDtlLang());
            setPoDtlOther(purchaseOrder.getPoDtlOther());
            setPoDtlResy(purchaseOrder.getPoDtlResy());
            setPoEtdDate(purchaseOrder.getPoEtdDate());
            setPoFob(purchaseOrder.getPoFob());
            setPoFobPort(purchaseOrder.getPoFobPort());
            setPoIartInsSh(purchaseOrder.getPoIartInsSh());
            setPoIartLabel(purchaseOrder.getPoIartLabel());
            setPoIartOther(purchaseOrder.getPoIartOther());
            setPoIdtlInsSh(purchaseOrder.getPoIdtlInsSh());
            setPoIdtlLabel(purchaseOrder.getPoIdtlLabel());
            setPoIdtlOther(purchaseOrder.getPoIdtlOther());
            setPoIpkgInsSh(purchaseOrder.getPoIpkgInsSh());
            setPoIpkgLabel(purchaseOrder.getPoIpkgLabel());
            setPoIpkgOther(purchaseOrder.getPoIpkgOther());
            setPoLshpDate(purchaseOrder.getPoLshpDate());
            setPoNo(purchaseOrder.getPoNo());
            setPoOdtl(purchaseOrder.getPoOdtl());
            setPoOrdAmtWord(purchaseOrder.getPoOrdAmtWord());
            setPoOrdTotAmt(purchaseOrder.getPoOrdTotAmt());
            setPoOrdTotChg(purchaseOrder.getPoOrdTotChg());
            setPoOrdTotNet(purchaseOrder.getPoOrdTotNet());
            setPoPkgArtNo(purchaseOrder.getPoPkgArtNo());
            setPoPkgEanNo(purchaseOrder.getPoPkgEanNo());
            setPoPkgGeneral(purchaseOrder.getPoPkgGeneral());
            setPoPkgGrnPoint(purchaseOrder.getPoPkgGrnPoint());
            setPoPkgLang(purchaseOrder.getPoPkgLang());
            setPoPkgOther(purchaseOrder.getPoPkgOther());
            setPoPkgResy(purchaseOrder.getPoPkgResy());
            setPoPtermDays(purchaseOrder.getPoPtermDays());
            setPoRouting(purchaseOrder.getPoRouting());
            setPoSoNoRef(purchaseOrder.getPoSoNoRef());
            setPoSpMainMark(purchaseOrder.getPoSpMainMark());
            setPoSpSideMark(purchaseOrder.getPoSpSideMark());
            setPoSuCode(purchaseOrder.getPoSuCode());
            setPoSuPterm(purchaseOrder.getPoSuPterm());
            setPoPterm(purchaseOrder.getPoPterm());
            setCoCode(purchaseOrder.getCoCode());
            setCrtDate(purchaseOrder.getCrtDate());
            setCrtUsr(purchaseOrder.getCrtUsr());
            setModDate(purchaseOrder.getModDate());
            setModUsr(purchaseOrder.getModUsr());
        }
    }

    public PurchaseOrderDetailsVo(SaleOrderDetailsVo saleOrder) {
        setPoNo(saleOrder.getSoNo());
        setPoCnf(saleOrder.getSoCnf());
        setPoCnfPort(saleOrder.getSoCnfPort());
        setPoContReq(saleOrder.getSoContReq());
        setPoCurr(saleOrder.getSoCurr());
        setPoDelDetails(saleOrder.getSoDelDetails());
        setPoDepDate(saleOrder.getSoDepDate());
        setPoDepPaid(saleOrder.getSoDepPaid());
        setPoDepRatio(saleOrder.getSoDepRatio());
        setPoDest(saleOrder.getSoDest());
        setPoEtdDate(saleOrder.getSoEtd());
        setPoFob(saleOrder.getSoFob());
        setPoFobPort(saleOrder.getSoFobPort());
        setPoLshpDate(saleOrder.getSoLshpDate());
        setPoSoNoRef(saleOrder.getSoNo());
        setPoRouting(saleOrder.getSoRouting());
        if (saleOrder.getSaleOrderItems() != null && saleOrder.getSaleOrderItems().size() > 0) {
            setPurchaseOrderItems(saleOrderItemsToPurchaseOrderItems(saleOrder.getSaleOrderItems()));
        }
        if (saleOrder.getSaleOrderLcs() != null && saleOrder.getSaleOrderLcs().size() > 0) {
            setPurchaseOrderLcs(saleOrderLcsToPurchaseOrderLcs(saleOrder.getSaleOrderLcs()));
        }
        if (saleOrder.getSaleOrderCharges() != null && saleOrder.getSaleOrderCharges().size() > 0) {
            setPurchaseOrderCharges(saleOrderChargesToPurchaseOrderCharges(saleOrder.getSaleOrderCharges()));
        }
        if (saleOrder.getSaleOrderSbks() != null && saleOrder.getSaleOrderSbks().size() > 0) {
            setPurchaseOrderSbks(saleOrderSbksToPurchaseOrderSbks(saleOrder.getSaleOrderSbks()));
        }
    }

    private Map<String, PurchaseOrderSbk> saleOrderSbksToPurchaseOrderSbks(
            Map<String, SaleOrderSbk> saleOrderSbks) {
        Map<String, PurchaseOrderSbk> getPurchaseOrderSbks = new HashMap<String, PurchaseOrderSbk>();
        Iterator iter = saleOrderSbks.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderSbk saleOrderSbk = saleOrderSbks.get(key);
            PurchaseOrderSbk getPurchaseOrderSbk = new PurchaseOrderSbk(saleOrderSbk);
            getPurchaseOrderSbks.put(key, getPurchaseOrderSbk);
        }
        return getPurchaseOrderSbks;
    }

    private Map<String, PurchaseOrderCharge> saleOrderChargesToPurchaseOrderCharges(
            Map<String, SaleOrderCharge> saleOrderCharges) {
        Map<String, PurchaseOrderCharge> getPurchaseOrderCharges = new HashMap<String, PurchaseOrderCharge>();
        Iterator iter = saleOrderCharges.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderCharge saleOrderCharge = saleOrderCharges.get(key);
            PurchaseOrderCharge getPurchaseOrderCharge = new PurchaseOrderCharge(saleOrderCharge);
            getPurchaseOrderCharges.put(key, getPurchaseOrderCharge);
        }
        return getPurchaseOrderCharges;
    }

    private Map<String, PurchaseOrderLc> saleOrderLcsToPurchaseOrderLcs(
            Map<String, SaleOrderLc> saleOrderLcs) {
        Map<String, PurchaseOrderLc> getPurchaseOrderLcs = new HashMap<String, PurchaseOrderLc>();
        Iterator iter = saleOrderLcs.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderLc saleOrderLc = saleOrderLcs.get(key);
            PurchaseOrderLc getPurchaseOrderLc = new PurchaseOrderLc(saleOrderLc);
            getPurchaseOrderLcs.put(key, getPurchaseOrderLc);
        }
        return getPurchaseOrderLcs;
    }

    private Map<String, PurchaseOrderItem> saleOrderItemsToPurchaseOrderItems(
            Map<String, SaleOrderItem> saleOrderItems) {
        Map<String, PurchaseOrderItem> getPurchaseOrderItems = new HashMap<String, PurchaseOrderItem>();
        Iterator iter = saleOrderItems.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderItem saleOrderItem = saleOrderItems.get(key);
            PurchaseOrderItem getPurchaseOrderItem = new PurchaseOrderItem(saleOrderItem);
            getPurchaseOrderItems.put(key, getPurchaseOrderItem);
        }
        return getPurchaseOrderItems;
    }

    public Map<String, PurchaseOrderItem> getPurchaseOrderItems() {
        return purchaseOrderItems;
    }

    public void setPurchaseOrderItems(Map<String, PurchaseOrderItem> purchaseOrderItems) {
        this.purchaseOrderItems = purchaseOrderItems;
    }

    public boolean isHaveLc() {
        return haveLc;
    }

    public void setHaveLc(boolean haveLc) {
        this.haveLc = haveLc;
    }

    public PurchaseOrderLc getPurchaseOrderLc() {
        return purchaseOrderLc;
    }

    public void setPurchaseOrderLc(PurchaseOrderLc purchaseOrderLc) {
        this.purchaseOrderLc = purchaseOrderLc;
    }

    public boolean isHaveCharge() {
        return haveCharge;
    }

    public void setHaveCharge(boolean haveCharge) {
        this.haveCharge = haveCharge;
    }

    public PurchaseOrderCharge getPurchaseOrderCharge() {
        return purchaseOrderCharge;
    }

    public void setPurchaseOrderCharge(PurchaseOrderCharge purchaseOrderCharge) {
        this.purchaseOrderCharge = purchaseOrderCharge;
    }

    public boolean isHaveSbk() {
        return haveSbk;
    }

    public void setHaveSbk(boolean haveSbk) {
        this.haveSbk = haveSbk;
    }

    public PurchaseOrderSbk getPurchaseOrderSbk() {
        return purchaseOrderSbk;
    }

    public void setPurchaseOrderSbk(PurchaseOrderSbk purchaseOrderSbk) {
        this.purchaseOrderSbk = purchaseOrderSbk;
    }

    public Map<String, PurchaseOrderLc> getPurchaseOrderLcs() {
        return purchaseOrderLcs;
    }

    public void setPurchaseOrderLcs(Map<String, PurchaseOrderLc> purchaseOrderLcs) {
        this.purchaseOrderLcs = purchaseOrderLcs;
    }

    public Map<String, PurchaseOrderCharge> getPurchaseOrderCharges() {
        return purchaseOrderCharges;
    }

    public void setPurchaseOrderCharges(
            Map<String, PurchaseOrderCharge> purchaseOrderCharges) {
        this.purchaseOrderCharges = purchaseOrderCharges;
    }

    public Map<String, PurchaseOrderSbk> getPurchaseOrderSbks() {
        return purchaseOrderSbks;
    }

    public void setPurchaseOrderSbks(Map<String, PurchaseOrderSbk> purchaseOrderSbks) {
        this.purchaseOrderSbks = purchaseOrderSbks;
    }
}
