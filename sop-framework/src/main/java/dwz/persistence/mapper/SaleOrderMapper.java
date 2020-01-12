package dwz.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import sop.persistence.beans.SaleOrder;
import sop.persistence.beans.SaleOrderCharge;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.SaleOrderLc;
import sop.persistence.beans.SaleOrderSbk;
import sop.vo.CustomerVo;
import sop.vo.PoSoSearchItemReportConditionVo;
import sop.vo.SaleOrderVo;
import sop.vo.SearchItemReportConditionVo;
import sop.vo.SoPoConditionVo;
import sop.vo.TxnVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:31
 * @Package: dwz.persistence.mapper
 */


@Repository
public interface SaleOrderMapper extends BaseMapper<SaleOrder, Integer> {

    // 查询
    List<SaleOrderVo> findPageBreakByCondition(SoPoConditionVo vo, RowBounds rb);

    Integer findNumberByCondition(SoPoConditionVo vo);

    List<CustomerVo> getAllCustomers();

    List<TxnVo> getAllTxns();

    Integer getCountBySaleOrder(SaleOrder currentSaleOrder);

    Integer insertSaleOrder(SaleOrder currentSaleOrder);

    void insertSaleOrderItem(SaleOrderItem saleOrderItem);

    SaleOrder getSaleOrderBySaleOrderVo(SaleOrderVo saleOrderVo);

    List<SaleOrderItem> getSaleOrderItemListByFkSoNo(
            String fkSoNo);

    Integer updateSaleOrder(SaleOrder currentSaleOrder);

    void deleteSaleOrderItems(String fkSoNo);

    Integer deleteSaleOrder(String fkSoNo);

    SaleOrder getSaleOrderByFkSoNo(String fkSoNo);

    SaleOrderCharge getSaleOrderChargeByFkSoNo(String fkSoNo);

    SaleOrderSbk getSaleOrderSbkByFkSoNo(String fkSoNo);

    SaleOrderLc getSaleOrderLcByFkSoNo(String fkSoNo);

    void deleteSaleOrderCharge(String fkSoNo);

    void insertSaleOrderCharge(SaleOrderCharge saleOrderCharge);

    void deleteSaleOrderSbk(String fkSoNo);

    void insertSaleOrderSbk(SaleOrderSbk saleOrderSbk);

    void deleteSaleOrderLc(String fkSoNo);

    void insertSaleOrderLc(SaleOrderLc saleOrderLc);

    List<SaleOrderCharge> getSaleOrderChargeListByFkSoNo(String fkSoNo);

    List<SaleOrderSbk> getSaleOrderSbkListByFkSoNo(String fkSoNo);

    List<SaleOrderLc> getSaleOrderLcListByFkSoNo(String fkSoNo);

    List<Object> getOrderFollowShipmentScheduleSo(
            PoSoSearchItemReportConditionVo vo);

    List<Object> getProformaInvoiceReport(SoPoConditionVo vo);

    List<Object> getProformaCoHeaderReport(SoPoConditionVo vo);

    List<Object> getSalesItemOverviewReport(Map<String, String> map);

    Integer findNumberByCustomer(PoSoSearchItemReportConditionVo vo);

    List<Object> getShipmentScheduleCustomerReports(
            PoSoSearchItemReportConditionVo vo);

    Integer findNumberByFactory(PoSoSearchItemReportConditionVo vo);

    List<Object> getShipmentScheduleFactoryReports(
            PoSoSearchItemReportConditionVo vo);

    List<Object> getShipmentScheduleWEtdEtaVesselReports(
            PoSoSearchItemReportConditionVo vo);

    Integer findNumberByCustomerItems(SearchItemReportConditionVo vo);

    List<Object> getSoldItemSummaryBySoWPhotoReports(
            SearchItemReportConditionVo vo);
}
