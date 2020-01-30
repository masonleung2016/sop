package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import sop.persistence.beans.PurchaseOrder;
import sop.persistence.beans.PurchaseOrderCharge;
import sop.persistence.beans.PurchaseOrderItem;
import sop.persistence.beans.PurchaseOrderLc;
import sop.persistence.beans.PurchaseOrderSbk;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.PoSoSearchItemReportConditionVo;
import sop.vo.PurchaseOrderDetailsVo;
import sop.vo.PurchaseOrderVo;
import sop.vo.SoPoConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:27
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface PoMapper extends BaseMapper<PurchaseOrder, Integer> {

    List<PurchaseOrderVo> findPageBreakByCondition(SoPoConditionVo vo, RowBounds rb);

    Integer findNumberByCondition(SoPoConditionVo vo);

    PurchaseOrder getPurchaseOrderByFkPoNo(String fkPoNo);

    List<PurchaseOrderItem> getPurchaseOrderItemListByFkPoNo(String fkPoNo);

    List<PurchaseOrderCharge> getPurchaseOrderChargeListByFkPoNo(String fkPoNo);

    List<PurchaseOrderSbk> getPurchaseOrderSbkListByFkPoNo(String fkPoNo);

    List<PurchaseOrderLc> getPurchaseOrderLcListByFkPoNo(String fkPoNo);

    Integer updatePurchaseOrder(PurchaseOrder purchaseOrder);

    void deletePurchaseOrderItems(String fkPoNo);

    void insertPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);

    void deletePurchaseOrderCharges(String fkPoNo);

    void insertPurchaseOrderCharge(PurchaseOrderCharge purchaseOrderCharge);

    void deletePurchaseOrderSbks(String fkPoNo);

    void insertPurchaseOrderSbk(PurchaseOrderSbk purchaseOrderSbk);

    void deletePurchaseOrderLcs(String fkPoNo);

    void insertPurchaseOrderLc(PurchaseOrderLc purchaseOrderLc);

    Integer getCountByPurchaseOrder(PurchaseOrderDetailsVo currentPurchaseOrder);

    Integer insertPurchaseOrder(PurchaseOrder purchaseOrder);

    void deletePurchaseOrder(String fkPoNo);

    List<Object> getOrderFollowShipmentSchedulePo(PoSoSearchItemReportConditionVo vo);

    Integer getPoReportsCount(PoSoSearchItemReportConditionVo vo);

    List<Object> getPoShipmentScheduleReport(PoSoSearchItemReportConditionVo vo);

    List<Object> getPurchaseOrderReport(PoSearchItemConditionVo vo);
    
}
