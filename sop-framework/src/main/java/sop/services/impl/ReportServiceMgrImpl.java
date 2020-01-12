package sop.services.impl;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.persistence.mapper.ComMapper;
import dwz.persistence.mapper.CustomerMapper;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.OfferSheetMapper;
import dwz.persistence.mapper.PoMapper;
import dwz.persistence.mapper.SaleOrderMapper;
import sop.filegen.FileGenerationException;
import sop.filegen.GenFileFunction;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;
import sop.reports.vo.ProformaCoHeader;
import sop.reports.vo.ProformaInvoice;
import sop.reports.vo.ReportDownload;
import sop.reports.vo.ReportResult;
import sop.services.ReportServiceMgr;
import sop.utils.DollarNumberFormat;
import sop.vo.ItemMasterConditionVo;
import sop.vo.OfferSheetConditionVo;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.PoSoSearchItemReportConditionVo;
import sop.vo.SearchItemReportConditionVo;
import sop.vo.SoPoConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:52
 * @Package: sop.services.impl
 */


@Service(ReportServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ReportServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ReportServiceMgr {

    @Autowired
    private OfferSheetMapper offerSheetMapper;
    @Autowired
    private ItemMapper itemMasterMapper;

    @Autowired
    private ComMapper comMapper;

    @Autowired
    private GenFileFunction genFileDispatcherEndpoint;

    @Autowired
    private PoMapper poMapper;

    @Autowired
    private SaleOrderMapper soMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ReportResult getOffAbCostPriceReport(OfferSheetConditionVo vo, String coCode) {
        vo.setFrom(vo.getFromPrefix() + vo.getFromNo());
        ReportResult result = new ReportResult();
        Integer offerSheetCount = offerSheetMapper.findNumberByCondition(vo);
        if (offerSheetCount > 0) {
            result.setStatus("1");
            result.setDataCount(offerSheetCount);
            List<Object> offAbCostPriceBeans = offerSheetMapper.getOffAbCostPrice(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            List<ReportDownload> reportDownloads = this.getReportDownloads("off_ab_cost_price", offAbCostPriceBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(offerSheetCount);
        }
        return result;
    }

    private List<ReportDownload> getReportDownloads(String reportId, List<Object> dataList, String[] fileTypes, Map<String, Object> params) {
        List<ReportDownload> reportDownloads = null;
        if (reportId != null && !reportId.trim().equals("") && dataList != null && dataList.size() > 0 && fileTypes != null && fileTypes.length > 0) {
            reportDownloads = new ArrayList<ReportDownload>();
            for (String fileType : fileTypes) {
                ReportDownload download = new ReportDownload();
                download.setFileType(fileType);
                GenRequest request = new GenRequest();
                GenResult genResult = null;
                //jdbc or not
                request.setFillType("");
                //genfiletype
                request.setOutputType(fileType);
                //report id
                request.setReportId(reportId);
                //parameters
                request.setParams(params);
                //datas
                request.setDataList(dataList);

                try {
                    genResult = genFileDispatcherEndpoint.generateFile(request);
                    File file = new File(genResult.getReportFile());
                    download.setFileName(file.getName());
                    download.setRemoteDownloadURL(genResult.getRemoteDownloadURL());
                    reportDownloads.add(download);
                } catch (FileGenerationException e) {
                    e.printStackTrace();
                }
            }
        }
        return reportDownloads;
    }

    @Override
    public ReportResult getItemMasterReport(ItemMasterConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        List<Object> itemMasterBeans = itemMasterMapper.getItemMasterReport(vo);
        if (itemMasterBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(itemMasterBeans.size());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            List<ReportDownload> reportDownloads = this.getReportDownloads("item_master_list", itemMasterBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(itemMasterBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getOffListItemReport(ItemMasterConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        List<Object> offListItemBeans = offerSheetMapper.getOffListItem(vo);
        if (offListItemBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(offListItemBeans.size());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            List<ReportDownload> reportDownloads = this.getReportDownloads("offersheet_list_item", offListItemBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(offListItemBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getCostPriceListReport(OfferSheetConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        List<Object> costPriceListBeans = offerSheetMapper.getCostPriceList(vo);
        if (costPriceListBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(costPriceListBeans.size());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            List<ReportDownload> reportDownloads = this.getReportDownloads("cost_price_list", costPriceListBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(costPriceListBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getABPriceListReport(OfferSheetConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        List<Object> abPriceListBeans = offerSheetMapper.getABPriceList(vo);
        if (abPriceListBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(abPriceListBeans.size());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            List<ReportDownload> reportDownloads = this.getReportDownloads("A&B_price_list", abPriceListBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(abPriceListBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getSimplfiedOffReport(OfferSheetConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        List<Object> simplifiedOffBeans = offerSheetMapper.getSimplifiedOff(vo);
        if (simplifiedOffBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(simplifiedOffBeans.size());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            List<ReportDownload> reportDownloads = this.getReportDownloads("simplified_offersheet", simplifiedOffBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(simplifiedOffBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getOrderFollowShipmentSchedulePoReports(
            PoSoSearchItemReportConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        Integer poCount = poMapper.findNumberByCondition(vo);
        if (poCount > 0) {
            result.setStatus("1");
            result.setDataCount(poCount);
            List<Object> orderFollowShipmentSchedulePoBeans = poMapper.getOrderFollowShipmentSchedulePo(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Date currentDate = new Date();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            params.put("currentDate", currentDate);
            List<ReportDownload> reportDownloads = this.getReportDownloads("order_follow_shipment_schedule_po", orderFollowShipmentSchedulePoBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(poCount);
        }
        return result;
    }

    @Override
    public ReportResult getOrderFollowShipmentScheduleSoReports(
            PoSoSearchItemReportConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        Integer soCount = soMapper.findNumberByCondition(vo);
        if (soCount > 0) {
            result.setStatus("1");
            result.setDataCount(soCount);
            List<Object> orderFollowShipmentScheduleSoBeans = soMapper.getOrderFollowShipmentScheduleSo(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Date currentDate = new Date();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            params.put("currentDate", currentDate);
            List<ReportDownload> reportDownloads = this.getReportDownloads("order_follow_shipment_schedule_so", orderFollowShipmentScheduleSoBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(soCount);
        }
        return result;
    }

    @Override
    public ReportResult getPoShipmentScheduleReport(
            PoSoSearchItemReportConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        Integer poCount = poMapper.getPoReportsCount(vo);
        if (poCount > 0) {
            result.setStatus("1");
            result.setDataCount(poCount);
            List<Object> poShipmentScheduleReportBeans = poMapper.getPoShipmentScheduleReport(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Date currentDate = new Date();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            params.put("currentDate", currentDate);
            List<ReportDownload> reportDownloads = this.getReportDownloads("po_shipment_schedule_po_no", poShipmentScheduleReportBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(poCount);
        }
        return result;
    }

    @Override
    public ReportResult getPurchaseOrderReport(PoSearchItemConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        List<Object> reportBeans = poMapper.getPurchaseOrderReport(vo);
        if (reportBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(reportBeans.size());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            List<ReportDownload> reportDownloads = this.getReportDownloads("purchase_order", reportBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(reportBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getProformaInvoiceReport(SoPoConditionVo vo) {
        ReportResult result = new ReportResult();
        List<Object> reportBeans = soMapper.getProformaInvoiceReport(vo);
        if (reportBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(reportBeans.size());
            Double amt = (double) 0;
            for (Object object : reportBeans) {
                ProformaInvoice pch = (ProformaInvoice) object;
                amt += pch.getSo2_amt().doubleValue();
            }
            DollarNumberFormat df = new DollarNumberFormat();
            String amtSay = "SAY U.S.Dollar " + df.format(amt).toUpperCase();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("amtSay", amtSay);
            List<ReportDownload> reportDownloads = this.getReportDownloads("proforma_invoice", reportBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(reportBeans.size());
        }
        return result;
    }

    public ReportResult getProformaCoHeaderReport(SoPoConditionVo vo) {
        ReportResult result = new ReportResult();
        List<Object> reportBeans = soMapper.getProformaCoHeaderReport(vo);
        if (reportBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(reportBeans.size());
            Double amt = (double) 0;
            for (Object object : reportBeans) {
                ProformaCoHeader pch = (ProformaCoHeader) object;
                amt += pch.getSo2_amt().doubleValue();
            }
            DollarNumberFormat df = new DollarNumberFormat();
            String amtSay = "SAY U.S.Dollar " + df.format(amt).toUpperCase();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("amtSay", amtSay);
            List<ReportDownload> reportDownloads = this.getReportDownloads("proforma-coheader3", reportBeans, new String[]{"pdf"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(reportBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getSalesItemOverviewReport(String p_year, String p_handle, String p_cu_code) {
        ReportResult result = new ReportResult();
        Map paramsMap = new HashMap<String, String>();
        paramsMap.put("p_year", p_year);
        paramsMap.put("p_handle", p_handle);
        paramsMap.put("p_cu_code", p_cu_code);
        List<Object> reportBeans = soMapper.getSalesItemOverviewReport(paramsMap);
        if (reportBeans.size() > 0) {
            result.setStatus("1");
            result.setDataCount(reportBeans.size());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            List<ReportDownload> reportDownloads = this.getReportDownloads("saleitemoverview", reportBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(reportBeans.size());
        }
        return result;
    }

    @Override
    public ReportResult getShipmentScheduleCustomerReports(
            PoSoSearchItemReportConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        Integer soCount = soMapper.findNumberByCustomer(vo);
        if (soCount > 0) {
            result.setStatus("1");
            result.setDataCount(soCount);
            List<Object> shipmentScheduleCustomerBeans = soMapper.getShipmentScheduleCustomerReports(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Date currentDate = new Date();
            String customerName = customerMapper.getCustomerNameByCuCode(vo.getFromNo());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            params.put("customerName", customerName);
            params.put("currentDate", currentDate);
            List<ReportDownload> reportDownloads = this.getReportDownloads("shipment_schedule_customer_no", shipmentScheduleCustomerBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(soCount);
        }
        return result;
    }

    @Override
    public ReportResult getShipmentScheduleFactoryReports(
            PoSoSearchItemReportConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        Integer soCount = soMapper.findNumberByFactory(vo);
        if (soCount > 0) {
            result.setStatus("1");
            result.setDataCount(soCount);
            List<Object> shipmentScheduleFactoryBeans = soMapper.getShipmentScheduleFactoryReports(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Date currentDate = new Date();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            params.put("currentDate", currentDate);
            List<ReportDownload> reportDownloads = this.getReportDownloads("shipment_schedule_factory_no", shipmentScheduleFactoryBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(soCount);
        }
        return result;
    }

    @Override
    public ReportResult getShipmentScheduleWEtdEtaVesselReports(
            PoSoSearchItemReportConditionVo vo, String coCode) {
        ReportResult result = new ReportResult();
        Integer soCount = soMapper.findNumberByCustomer(vo);
        if (soCount > 0) {
            result.setStatus("1");
            result.setDataCount(soCount);
            List<Object> shipmentScheduleCustomerBeans = soMapper.getShipmentScheduleWEtdEtaVesselReports(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Date currentDate = new Date();
            String customerName = customerMapper.getCustomerNameByCuCode(vo.getFromNo());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            params.put("customerName", customerName);
            params.put("currentDate", currentDate);
            List<ReportDownload> reportDownloads = this.getReportDownloads("shipment_schedule_w_ETD_ETA_vessel_etc", shipmentScheduleCustomerBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(soCount);
        }
        return result;
    }

    @Override
    public ReportResult getSoldItemSummaryBySoWPhotoReports(
            SearchItemReportConditionVo vo, String coCode) {
        handleSearchItemReportConditionVoItemNo(vo);
        ReportResult result = new ReportResult();
        Integer soCount = soMapper.findNumberByCustomerItems(vo);
        if (soCount > 0) {
            result.setStatus("1");
            result.setDataCount(soCount);
            List<Object> soldItemSummaryBySoWPhotoBeans = soMapper.getSoldItemSummaryBySoWPhotoReports(vo);
            String coName = comMapper.getCompanyByFkCoCode(coCode).getCoName();
            Date currentDate = new Date();
            String customerName = customerMapper.getCustomerNameByCuCode(vo.getFromNo());
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("coName", coName);
            params.put("customerName", customerName);
            params.put("customerNo", vo.getFromNo());
            params.put("soFromDate", vo.getFromDate());
            params.put("soToDate", vo.getToDate());
            params.put("currentDate", currentDate);
            List<ReportDownload> reportDownloads = this.getReportDownloads("sold_item_summary_so_with_photo", soldItemSummaryBySoWPhotoBeans, new String[]{"pdf", "xls"}, params);
            result.setReportDownloads(reportDownloads);
        } else {
            result.setStatus("0");
            result.setDataCount(soCount);
        }
        return result;
    }

    private void handleSearchItemReportConditionVoItemNo(
            SearchItemReportConditionVo vo) {
        if (vo != null) {
            String fromItCat = vo.getFromItCat();
            String fromItNo = vo.getFromItNo();
            String fromItSuffix = vo.getFromItSuffix();

            String toItCat = vo.getToItCat();
            String toItNo = vo.getToItNo();
            String toItSuffix = vo.getToItSuffix();

            if (fromItCat != null && !fromItCat.trim().equals("")
                    && fromItNo != null && !fromItNo.trim().equals("")
                    && fromItSuffix != null && !fromItSuffix.trim().equals("")
                    && toItCat != null && !toItCat.trim().equals("")
                    && toItNo != null && !toItNo.trim().equals("")
                    && toItSuffix != null && !toItSuffix.trim().equals("")) {
                vo.setFromItSuffix(fromItCat + fromItNo + fromItSuffix);
                vo.setToItSuffix(toItCat + toItNo + toItSuffix);
            }
        }
    }
}
