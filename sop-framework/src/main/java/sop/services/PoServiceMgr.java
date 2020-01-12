package sop.services;

import java.util.List;
import java.util.Map;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.vo.Checker;
import sop.persistence.beans.PurchaseOrderCharge;
import sop.persistence.beans.PurchaseOrderItem;
import sop.persistence.beans.PurchaseOrderLc;
import sop.persistence.beans.PurchaseOrderSbk;
import sop.vo.PoItemMasterVo;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.PurchaseOrderCombos;
import sop.vo.PurchaseOrderDetailsVo;
import sop.vo.PurchaseOrderVo;
import sop.vo.SoPoConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:08
 * @Package: sop.services
 */


public interface PoServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "poServiceMgr";

    List<PurchaseOrderVo> searchPurchaseOrderVo(SoPoConditionVo vo);

    Integer searchPurchaseOrderVoNum(SoPoConditionVo vo);

    PurchaseOrderCombos getPurchaseOrderCombos();

    PurchaseOrderDetailsVo getPurchaseOrderDetailVoByFkPoNo(String fkPoNo);

    PurchaseOrderDetailsVo getPurchaseOrderDetailVoItemsByFkPoNo(String fkPoNo);

    boolean updatePurchaseOrder(PurchaseOrderDetailsVo currentPurchaseOrder);

    Checker checkPurchaseOrder(PurchaseOrderDetailsVo currentPurchaseOrder);

    Checker checkDecimal(PurchaseOrderDetailsVo currentPurchaseOrder);

    boolean addPurchaseOrder(PurchaseOrderDetailsVo currentPurchaseOrder);

    String[] filterGetPurchaseOrderChargeCodes(String[] poChargeCodes,
                                               Map<String, PurchaseOrderCharge> purchaseOrderCharges);

    Map<String, PurchaseOrderCharge> getPurchaseOrderCharges(
            String[] poChargeCodes);

    Map<String, PurchaseOrderCharge> updatePurchaseOrderCharges(
            Map<String, PurchaseOrderCharge> purchaseOrderCharges,
            Map<String, PurchaseOrderCharge> getPurchaseOrderCharges);

    Map<String, PurchaseOrderCharge> removePoCharge(String poChargeCode,
                                                    Map<String, PurchaseOrderCharge> purchaseOrderCharges);

    Checker checkPurchaseOrderCharge(PurchaseOrderCharge purchaseOrderCharge,
                                     PurchaseOrderDetailsVo currentPurchaseOrder);

    Checker checkPurchaseOrderChargeDecimal(
            PurchaseOrderCharge purchaseOrderCharge);

    Checker checkPurchaseOrderSbk(PurchaseOrderSbk purchaseOrderSbk,
                                  PurchaseOrderDetailsVo currentPurchaseOrder);

    Checker checkPurchaseOrderSbkDecimal(PurchaseOrderSbk purchaseOrderSbk);

    Map<String, PurchaseOrderSbk> removePoSbk(String fkExpShpDate,
                                              Map<String, PurchaseOrderSbk> purchaseOrderSbks);

    Checker checkPurchaseOrderLc(PurchaseOrderLc purchaseOrderLc,
                                 PurchaseOrderDetailsVo currentPurchaseOrder);

    Checker checkPurchaseOrderLcDecimal(PurchaseOrderLc purchaseOrderLc);

    Map<String, PurchaseOrderLc> removePoLc(String fkLcNo,
                                            Map<String, PurchaseOrderLc> purchaseOrderLcs);

    Map<String, PurchaseOrderItem> removeSoItem(String poItCatNoSuffix,
                                                Map<String, PurchaseOrderItem> purchaseOrderItems);

    List<PoItemMasterVo> getItemListByCondition(PoSearchItemConditionVo vo);

    String[] filterGetPoItemIds(String[] poItemIds,
                                Map<String, PurchaseOrderItem> purchaseOrderItems);

    Map<String, PurchaseOrderItem> getPurchaseOrderItems(String module,
                                                         String[] prePoItemIds);

    Map<String, PurchaseOrderItem> updatePurchaseOrderItems(
            Map<String, PurchaseOrderItem> purchaseOrderItems,
            Map<String, PurchaseOrderItem> getPurchaseOrderItems);

    void getPurchaseOrderBySelectSoId(PurchaseOrderDetailsVo currentPurchaseOrder, String selectSoId);

    boolean delPurchaseOrderByFkPoNo(String fkPoNo);

    void clearPo(PurchaseOrderDetailsVo currentPurchaseOrder);

    void updateItemAmount(PurchaseOrderDetailsVo currentPurchaseOrder);

    void updateChargeAmount(PurchaseOrderDetailsVo currentPurchaseOrder);

}
