package sop.services;

import java.util.List;
import java.util.Map;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import sop.persistence.beans.SaleOrder;
import sop.persistence.beans.SaleOrderCharge;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.SaleOrderLc;
import sop.persistence.beans.SaleOrderSbk;
import sop.vo.SaleOrderCombos;
import sop.vo.SaleOrderDetailsVo;
import sop.vo.SaleOrderVo;
import sop.vo.SoItemMasterVo;
import sop.vo.SoPoConditionVo;
import sop.vo.SoSearchItemConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:12
 * @Package: sop.services
 */

public interface SaleOrderServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "saleOrderServiceMgr";

    SaleOrderCombos getSaleOrderCombos();

    List<SoItemMasterVo> getItemListByCondition(SoSearchItemConditionVo vo);

    Map<String, SaleOrderItem> getSaleOrderItems(String[] offItemIds);

    List<SaleOrderVo> searchSaleOrderVo(SoPoConditionVo vo);

    Integer searchSaleOrderVoNum(SoPoConditionVo vo);

    Map<String, SaleOrderItem> updateSaleOrderItems(
            Map<String, SaleOrderItem> SaleOrderItems,
            Map<String, SaleOrderItem> getSaleOrderItems);

    Checker checkSaleOrder(SaleOrderDetailsVo currentSaleOrder);

    boolean addSaleOrder(SaleOrderDetailsVo currentSaleOrder) throws ServiceException;

    SaleOrder getSaleOrderBySaleOrderVo(SaleOrderVo SaleOrderVo);

    boolean updateSaleOrder(SaleOrderDetailsVo currentSaleOrder) throws ServiceException;

    Map<String, SaleOrderItem> removeOffItem(String offItCatNoSuffix,
                                             Map<String, SaleOrderItem> SaleOrderItems);

    String[] filterGetOffItemIds(String[] offItemIds,
                                 Map<String, SaleOrderItem> SaleOrderItems);

    boolean delSaleOrderBySaleOrderVo(SaleOrderVo SaleOrderVo);

    SaleOrderDetailsVo getSaleOrderDetailVoByFkSoNo(String fkSoNo);

    Checker checkDecimal(SaleOrderDetailsVo currentSaleOrder);

    boolean delSaleOrderByFkSoNo(String fkSoNo);

    String[] filterGetSaleOrderChargeCodes(String[] soChargeCodes,
                                           Map<String, SaleOrderCharge> saleOrderCharges);

    Map<String, SaleOrderCharge> getSaleOrderCharges(String[] soChargeCodes);

    Map<String, SaleOrderCharge> updateSaleOrderCharges(
            Map<String, SaleOrderCharge> saleOrderCharges,
            Map<String, SaleOrderCharge> getSaleOrderCharges);

    Map<String, SaleOrderCharge> removeSoCharge(String soChargeCode,
                                                Map<String, SaleOrderCharge> saleOrderCharges);

    Checker checkSaleOrderCharge(SaleOrderCharge saleOrderCharge,
                                 SaleOrderDetailsVo currentSaleOrder);

    Checker checkSaleOrderChargeDecimal(SaleOrderCharge saleOrderCharge);

    Checker checkSaleOrderSbk(SaleOrderSbk saleOrderSbk,
                              SaleOrderDetailsVo currentSaleOrder);

    Checker checkSaleOrderSbkDecimal(SaleOrderSbk saleOrderSbk);

    Map<String, SaleOrderSbk> removeSoSbk(String fkExpShpDate,
                                          Map<String, SaleOrderSbk> saleOrderSbks);

    Checker checkSaleOrderLc(SaleOrderLc saleOrderLc,
                             SaleOrderDetailsVo currentSaleOrder);

    Checker checkSaleOrderLcDecimal(SaleOrderLc saleOrderLc);

    Map<String, SaleOrderLc> removeSoLc(String fkLcNo,
                                        Map<String, SaleOrderLc> saleOrderLcs);

    String[] filterGetSoItemIds(String[] soItemIds,
                                Map<String, SaleOrderItem> saleOrderItems);

    Map<String, SaleOrderItem> getSaleOrderItems(String module,
                                                 String[] preSoItemIds);

    Map<String, SaleOrderItem> removeSoItem(String soItCatNoSuffix,
                                            Map<String, SaleOrderItem> saleOrderItems);

    void clearSo(SaleOrderDetailsVo currentSaleOrder);

    void updateItemAmount(SaleOrderDetailsVo currentSaleOrder);

    void updateChargeAmount(SaleOrderDetailsVo currentSaleOrder);
    
}
