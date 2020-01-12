package sop.services;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import sop.reports.vo.ReportResult;
import sop.vo.ItemMasterConditionVo;
import sop.vo.OfferSheetConditionVo;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.PoSoSearchItemReportConditionVo;
import sop.vo.SearchItemReportConditionVo;
import sop.vo.SoPoConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:11
 * @Package: sop.services
 */


public interface ReportServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "reportServiceMgr";

    ReportResult getOffAbCostPriceReport(OfferSheetConditionVo vo, String coCode);

    ReportResult getItemMasterReport(ItemMasterConditionVo vo, String coCode);

    ReportResult getOffListItemReport(ItemMasterConditionVo vo, String coCode);

    ReportResult getCostPriceListReport(OfferSheetConditionVo vo, String coCode);

    ReportResult getABPriceListReport(OfferSheetConditionVo vo, String coCode);

    ReportResult getSimplfiedOffReport(OfferSheetConditionVo vo, String coCode);

    ReportResult getOrderFollowShipmentSchedulePoReports(
            PoSoSearchItemReportConditionVo vo, String coCode);

    ReportResult getOrderFollowShipmentScheduleSoReports(
            PoSoSearchItemReportConditionVo vo, String coCode);

    ReportResult getPoShipmentScheduleReport(
            PoSoSearchItemReportConditionVo vo, String coCode);

    ReportResult getPurchaseOrderReport(PoSearchItemConditionVo vo, String coCode);

    ReportResult getProformaInvoiceReport(SoPoConditionVo vo);

    ReportResult getProformaCoHeaderReport(SoPoConditionVo vo);

    ReportResult getSalesItemOverviewReport(String p_year, String p_handle, String p_cu_code);

    //ReportDownload getOffAbCostPriceReportDownload(OfferSheetConditionVo vo,
//			String fileType, String coCode);
    ReportResult getShipmentScheduleCustomerReports(
            PoSoSearchItemReportConditionVo vo, String coCode);

    ReportResult getShipmentScheduleFactoryReports(
            PoSoSearchItemReportConditionVo vo, String coCode);

    ReportResult getShipmentScheduleWEtdEtaVesselReports(
            PoSoSearchItemReportConditionVo vo, String coCode);

    ReportResult getSoldItemSummaryBySoWPhotoReports(
            SearchItemReportConditionVo vo, String coCode);

}
