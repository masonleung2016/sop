package sop.web.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dwz.framework.config.Constants;
import dwz.framework.user.User;
import dwz.framework.user.UserServiceMgr;
import dwz.web.BaseController;
import sop.persistence.beans.SysUser;
import sop.reports.vo.ReportResult;
import sop.services.OfferSheetServiceMgr;
import sop.services.ReportServiceMgr;
import sop.vo.ItemMasterConditionVo;
import sop.vo.OfferSheetCombos;
import sop.vo.OfferSheetConditionVo;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.PoSoSearchItemReportConditionVo;
import sop.vo.SearchItemReportConditionVo;
import sop.vo.SoPoConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:58
 * @Package: sop.web.management
 */

@Controller("management.reportController")
@RequestMapping(value = "/management/reports")
public class ReportController extends BaseController {

    @Autowired
    private ReportServiceMgr reportMgr;

    @Autowired
    private OfferSheetServiceMgr offerSheetMgr;

    @Autowired
    private UserServiceMgr userMgr;

    @RequestMapping("/offabcostprice")
    public String offAbCostPrice(ModelMap model) {
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        model.addAttribute("offerSheetCombos", offerSheetCombos);

        return "/management/reports/offabcostprice";
    }

    @RequestMapping(value = "/offabcostpricereports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> offAbCostPriceReports(OfferSheetConditionVo vo, HttpServletRequest request) {

        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult offAbCostPriceResult = reportMgr.getOffAbCostPriceReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", offAbCostPriceResult);
        return result;
    }

    //itemmaster list page
    @RequestMapping("/itemmaster")
    public String itemMaster() {
        return "/management/reports/itemmaster";
    }

    //itemmaster list report
    @RequestMapping(value = "/itemmasterreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> itemMasterReports(ItemMasterConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult itemMasterResult = reportMgr.getItemMasterReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", itemMasterResult);
        return result;
    }

    //OFFERSHEET page
    @RequestMapping("/offersheet")
    public String offerSheet(ModelMap model) {
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        model.addAttribute("offerSheetCombos", offerSheetCombos);
        return "/management/reports/simplifiedoffersheet";
    }

    //OFFERSHEET report
    @RequestMapping(value = "/offersheetreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> offerSheetReports(ItemMasterConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult itemMasterResult = reportMgr.getItemMasterReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", itemMasterResult);
        return result;
    }

    //A&B price list page
    @RequestMapping("/abpricelist")
    public String abPrice(ModelMap model) {
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        model.addAttribute("offerSheetCombos", offerSheetCombos);
        return "/management/reports/abpricelist";
    }

    //A&B price list report
    @RequestMapping(value = "/abpricelistreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> abPriceListReports(OfferSheetConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult abPriceListResult = reportMgr.getABPriceListReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", abPriceListResult);
        return result;
    }

    //COST PRICE LIST page
    @RequestMapping("/costpricelist")
    public String costPriceList(ModelMap model) {
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        model.addAttribute("offerSheetCombos", offerSheetCombos);
        return "/management/reports/costpricelist";
    }

    //COST PRICE LIST report
    @RequestMapping(value = "/costpricelistreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> costPriceListReports(OfferSheetConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult costPriceListResult = reportMgr.getCostPriceListReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", costPriceListResult);
        return result;
    }

    //offersheet list item page
    @RequestMapping("/offlistitem")
    public String offListItem() {
        return "/management/reports/offlistitem";
    }

    //offersheet list item report
    @RequestMapping(value = "/offlistitemreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> offListItemReports(ItemMasterConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult offListItemResult = reportMgr.getOffListItemReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", offListItemResult);
        return result;
    }

    //P/I page
    @RequestMapping("/proformainvoice")
    public String proformaInvoice() {
        return "/management/reports/proformainvoice";
    }

    //P/I report
    @RequestMapping(value = "/proformainvoicereports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> proformaInvoiceReports(SoPoConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult itemMasterResult = reportMgr.getProformaInvoiceReport(vo);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", itemMasterResult);
        return result;
    }

    //P/O page
    @RequestMapping("/purchaseorder")
    public String purchaseorder() {
        return "/management/reports/purchaseorder";
    }

    //P/O report
    @RequestMapping(value = "/purchaseorderreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> purchaseOrderReports(PoSearchItemConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult purchaseOrderResult = reportMgr.getPurchaseOrderReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", purchaseOrderResult);
        return result;
    }

    //Simpliefied offersheet (2 photo) page
    @RequestMapping("/simplifiedoffersheet")
    public String simplifiedOfferSheet() {
        return "/management/reports/simplifiedoffersheet";
    }

    //Simpliefied offersheet (2 photo) report
    @RequestMapping(value = "/simplifiedoffersheetreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> simplifiedOfferSheetReports(OfferSheetConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult simplfiedOffResult = reportMgr.getSimplfiedOffReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", simplfiedOffResult);
        return result;
    }

    //PROFORMA- CO.HEADER 3 page
    @RequestMapping("/proformacoheader3")
    public String proformaCoHeader3() {
        return "/management/reports/proformacoheader3";
    }

    //PROFORMA- CO.HEADER 3 report
    @RequestMapping(value = "/proformacoheader3reports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> proformaCoHeader3Reports(SoPoConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult itemMasterResult = reportMgr.getProformaCoHeaderReport(vo);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", itemMasterResult);
        return result;
    }

    //SALES ITEM OVERVIEW page
    @RequestMapping("/salesitemoverview")
    public String salesItemOverview() {
        return "/management/reports/salesitemoverview";
    }

    //SALES ITEM OVERVIEW report
    @RequestMapping(value = "/salesitemoverviewreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> salesItemOverviewReports(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        String p_year = request.getParameter("year");
        String p_handle = request.getParameter("handle");
        String p_cu_code = request.getParameter("cu_code");
        ReportResult itemMasterResult = reportMgr.getSalesItemOverviewReport(p_year, p_handle, p_cu_code);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", itemMasterResult);
        return result;
    }

    //Order follow shipment schedule ( P/O NO.) page
    @RequestMapping("/orderfollowshipmentschedulepo")
    public String orderFollowShipmentSchedulePo(Model model) {
        List<SysUser> allHandlers = userMgr.getAllUsers();
        model.addAttribute("allHandlers", allHandlers);
        return "/management/reports/orderfollowshipmentschedulepo";
    }

    //Order follow shipment schedule ( P/O NO.) report
    @RequestMapping(value = "/orderfollowshipmentscheduleporeports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderFollowShipmentSchedulePoReports(PoSoSearchItemReportConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult orderFollowShipmentSchedulePoResult = reportMgr.getOrderFollowShipmentSchedulePoReports(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", orderFollowShipmentSchedulePoResult);
        return result;
    }

    //Order follow shipment schedule ( S/O NO.) page
    @RequestMapping("/orderfollowshipmentscheduleso")
    public String orderFollowShipmentScheduleSo(Model model) {
        List<SysUser> allHandlers = userMgr.getAllUsers();
        model.addAttribute("allHandlers", allHandlers);
        return "/management/reports/orderfollowshipmentscheduleso";
    }

    //Order follow shipment schedule ( S/O NO.) report
    @RequestMapping(value = "/orderfollowshipmentschedulesoreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderFollowShipmentScheduleSoReports(PoSoSearchItemReportConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult itemMasterResult = reportMgr.getOrderFollowShipmentScheduleSoReports(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", itemMasterResult);
        return result;
    }

    //P/O shipmnet schedule (purchase order NO.) page
    @RequestMapping("/poshipmentschedule")
    public String poShipmentSchedule() {
        return "/management/reports/poshipmentschedule";
    }

    //P/O shipmnet schedule (purchase order NO.) report
    @RequestMapping(value = "/poshipmentschedulereports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> poShipmentScheduleReports(PoSoSearchItemReportConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult reportResult = reportMgr.getPoShipmentScheduleReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", reportResult);
        return result;
    }

    //shipment schedule (customer NO.) page
    @RequestMapping("/shipmentschedulecustomer")
    public String shipmentScheduleCustomer() {
        return "/management/reports/shipmentschedulecustomer";
    }

    //shipment schedule (customer NO.) report
    @RequestMapping(value = "/shipmentschedulecustomerreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shipmentScheduleCustomerReports(PoSoSearchItemReportConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult reportResult = reportMgr.getShipmentScheduleCustomerReports(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", reportResult);
        return result;
    }

    //	//Shipment schedule - Confirmed ( customer NO.) page
//	@RequestMapping("/shipmentscheduleconfirmed")
//	public String shipmentScheduleConfirmed(){
//		return "/management/reports/shipmentscheduleconfirmed";
//	}
//	//Shipment schedule - Confirmed ( customer NO.) report
//	@RequestMapping(value = "/shipmentscheduleconfirmedreports", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> shipmentScheduleConfirmedReports(ItemMasterConditionVo vo,HttpServletRequest request){
//		User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
//		String coCode = "";
//		if(currentUser != null){
//			coCode = currentUser.getCoCode();
//		}else{
//			coCode = "RRR";
//		}
//		ReportResult reportResult = reportMgr.getItemMasterReport(vo, coCode);
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("result", reportResult);
//		return result;
//	}
    //Shipment schedule - factory ( factory NO.) page
    @RequestMapping("/shipmentschedulefactory")
    public String shipmentScheduleFactory() {
        return "/management/reports/shipmentschedulefactory";
    }

    //Shipment schedule - factory ( factory NO.) report
    @RequestMapping(value = "/shipmentschedulefactoryreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shipmentScheduleFactoryReports(PoSoSearchItemReportConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult reportResult = reportMgr.getShipmentScheduleFactoryReports(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", reportResult);
        return result;
    }

    //	//Shipment schedule - Finished ( customer NO.) page
//	@RequestMapping("/shipmentschedulefinished")
//	public String shipmentScheduleFinished(){
//		return "/management/reports/shipmentschedulefinished";
//	}
//	//Shipment schedule - Finished ( customer NO.) report
//	@RequestMapping(value = "/shipmentschedulefinishedreports", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> shipmentScheduleFinishedReports(ItemMasterConditionVo vo,HttpServletRequest request){
//		User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
//		String coCode = "";
//		if(currentUser != null){
//			coCode = currentUser.getCoCode();
//		}else{
//			coCode = "RRR";
//		}
//		ReportResult reportResult = reportMgr.getItemMasterReport(vo, coCode);
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("result", reportResult);
//		return result;
//	}
//	//Shipment schedule - Outstanding ( customer NO.) page
//	@RequestMapping("/shipmentscheduleoutstanding")
//	public String shipmentScheduleOutstanding(){
//		return "/management/reports/shipmentscheduleoutstanding";
//	}
//	//Shipment schedule - Outstanding ( customer NO.) report
//	@RequestMapping(value = "/shipmentscheduleoutstandingreports", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> shipmentScheduleOutstandingReports(ItemMasterConditionVo vo,HttpServletRequest request){
//		User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
//		String coCode = "";
//		if(currentUser != null){
//			coCode = currentUser.getCoCode();
//		}else{
//			coCode = "RRR";
//		}
//		ReportResult reportResult = reportMgr.getItemMasterReport(vo, coCode);
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("result", reportResult);
//		return result;
//	}
    //Shipment schedule - w / ETD,ETA,Vessel etc. page
    @RequestMapping("/shipmentschedulewetdetavessel")
    public String shipmentScheduleWEtdEtaVessel() {
        return "/management/reports/shipmentschedulewetdetavessel";
    }

    //Shipment schedule - w / ETD,ETA,Vessel etc. report
    @RequestMapping(value = "/shipmentschedulewetdetavesselreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shipmentScheduleWEtdEtaVesselReports(PoSoSearchItemReportConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult reportResult = reportMgr.getShipmentScheduleWEtdEtaVesselReports(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", reportResult);
        return result;
    }

    //Sold item summary by S/O - W/photo page
    @RequestMapping("/solditemsummarybysowphoto")
    public String soldItemSummaryBySoWPhoto() {
        return "/management/reports/solditemsummarybysowphoto";
    }

    //Sold item summary by S/O - W/photo report
    @RequestMapping(value = "/solditemsummarybysowphotoreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> soldItemSummaryBySoWPhotoReports(SearchItemReportConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult reportResult = reportMgr.getSoldItemSummaryBySoWPhotoReports(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", reportResult);
        return result;
    }

    //Sold item summary by S/O-  W/photo (W/O. C.Art #) page
    @RequestMapping("/solditemsummarybysowphotowocart")
    public String soldItemSummaryBySoWPhotoWoCArt() {
        return "/management/reports/solditemsummarybysowphotowocart";
    }

    //Sold item summary by S/O-  W/photo (W/O. C.Art #) report
    @RequestMapping(value = "/solditemsummarybysowphotowocartreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> soldItemSummaryBySoWPhotoWoCArtReports(ItemMasterConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult reportResult = reportMgr.getItemMasterReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", reportResult);
        return result;
    }

    //PRICE HISTORY FOR MANY ITEMS page
    @RequestMapping("/pricehistoryformanyitems")
    public String priceHistoryForManyItems() {
        return "/management/reports/pricehistoryformanyitems";
    }

    //PRICE HISTORY FOR MANY ITEMS report
    @RequestMapping(value = "/pricehistoryformanyitemsreports", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> priceHistoryForManyItemsReports(ItemMasterConditionVo vo, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        String coCode = "";
        if (currentUser != null) {
            coCode = currentUser.getCoCode();
        } else {
            coCode = "RRR";
        }
        ReportResult reportResult = reportMgr.getItemMasterReport(vo, coCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", reportResult);
        return result;
    }
}
