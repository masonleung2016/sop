package sop.web.management;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.SaleOrderCharge;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.SaleOrderLc;
import sop.persistence.beans.SaleOrderSbk;
import sop.services.ChargeServiceMgr;
import sop.services.ItemServiceMgr;
import sop.services.SaleOrderServiceMgr;
import sop.vo.ChargeVo;
import sop.vo.SaleOrderCombos;
import sop.vo.SaleOrderDetailsVo;
import sop.vo.SaleOrderVo;
import sop.vo.SearchItemConditionVo;
import sop.vo.SoItemMasterVo;
import sop.vo.SoPoConditionVo;
import sop.vo.SoSearchItemConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:59
 * @Package: sop.web.management
 */

@Controller("management.saleOrderController")
@RequestMapping(value = "/management/saleorder")
@SessionAttributes({"currentSaleOrder"})
public class SaleOrderController extends BaseController {

    @Autowired
    private SaleOrderServiceMgr saleOrderMgr;

    @Autowired
    private ItemServiceMgr itemMgr;

    @Autowired
    private ChargeServiceMgr chargeMgr;

    @RequestMapping("/list")
    public String list(SoPoConditionVo vo, Model model) {

        List<SaleOrderVo> saleOrderVoList = saleOrderMgr.searchSaleOrderVo(vo);
        Integer totalCount = saleOrderMgr.searchSaleOrderVoNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("saleOrderVoList", saleOrderVoList);

        return "/management/saleorder/list";
    }

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        SaleOrderDetailsVo currentSaleOrder = new SaleOrderDetailsVo();
        if (currentUser != null) {
            currentSaleOrder.setCoCode(currentUser.getCoCode());
        } else {
            currentSaleOrder.setCoCode("tst");
        }
        currentSaleOrder.setCrtDate(new Date());
        currentSaleOrder.setSoDate(new Date());
        currentSaleOrder.setSoStatus("O");
        SaleOrderCombos saleOrderCombos = saleOrderMgr.getSaleOrderCombos();
        model.addAttribute("currentSaleOrder", currentSaleOrder);
        model.addAttribute("saleOrderCombos", saleOrderCombos);
        return "/management/saleorder/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        Checker checker = saleOrderMgr.checkSaleOrder(currentSaleOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = saleOrderMgr.checkDecimal(currentSaleOrder);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentSaleOrder.setCrtUsr(currentUser.getUsrId());
                    currentSaleOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentSaleOrder.setCrtUsr("test");
                    currentSaleOrder.setModUsr("test");
                }
                try {
                    if (saleOrderMgr.addSaleOrder(currentSaleOrder)) {
                        return ajaxDoneSuccess(getMessage("msg.operation.success"));
                    } else {
                        return ajaxDoneError(getMessage("msg.operation.failure"));
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                    return ajaxDoneError(e.getMessage());
                }
            } else {
                return ajaxDoneError(decimalChecker.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{fkSoNo}")
    public String edit(@PathVariable("fkSoNo") String fkSoNo, Model model) {
        fkSoNo = Base64.decode(fkSoNo, "utf-8");
        SaleOrderCombos saleOrderCombos = saleOrderMgr.getSaleOrderCombos();
        SaleOrderDetailsVo currentSaleOrder = saleOrderMgr.getSaleOrderDetailVoByFkSoNo(fkSoNo);

        model.addAttribute("saleOrderCombos", saleOrderCombos);
        model.addAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/edit";
    }

    @RequestMapping("/copy/{fkSoNo}")
    public String copy(@PathVariable("fkSoNo") String fkSoNo, Model model) {
        fkSoNo = Base64.decode(fkSoNo, "utf-8");
        SaleOrderCombos saleOrderCombos = saleOrderMgr.getSaleOrderCombos();
        SaleOrderDetailsVo currentSaleOrder = saleOrderMgr.getSaleOrderDetailVoByFkSoNo(fkSoNo);
        saleOrderMgr.clearSo(currentSaleOrder);
        model.addAttribute("saleOrderCombos", saleOrderCombos);
        model.addAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/add";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentSaleOrder.setModUsr(currentUser.getUsrId());
            if (currentSaleOrder.getCoCode().equals("")) {
                currentSaleOrder.setCoCode(currentUser.getCoCode());
            }
        } else {
            currentSaleOrder.setModUsr("test");
            if (currentSaleOrder.getCoCode().equals("")) {
                currentSaleOrder.setCoCode("tst");
            }
        }
        try {
            if (saleOrderMgr.updateSaleOrder(currentSaleOrder)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
    }

    @RequestMapping("/delete/{fkSoNo}")
    public ModelAndView delete(@PathVariable("fkSoNo") String fkSoNo, Model model, HttpServletRequest request) {
        fkSoNo = Base64.decode(fkSoNo, "utf-8");
        boolean flag = saleOrderMgr.delSaleOrderByFkSoNo(fkSoNo);
        if (flag) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    //for test
    @RequestMapping("/lookupCustomer")
    public String lookupCutomer() {
        return "/management/offersheet/lookupCustomer";
    }

    @RequestMapping("/suggestCustomer")
    public String suggestCustomer() {
        return "/management/offersheet/suggestCustomer";
    }

    @RequestMapping("/lookupItem")
    public String lookupItem() {
        return "/management/offersheet/lookupItem";
    }

    @RequestMapping("/chargelist")
    public String chargelist(SearchItemConditionVo vo, Model model, HttpServletRequest request) {
        List<ChargeVo> saleOrderChargeVoList = chargeMgr.getAllCharges();
        model.addAttribute("saleOrderChargeVoList", saleOrderChargeVoList);
        model.addAttribute("vo", vo);
        return "/management/saleorder/getcharge";
    }

    @RequestMapping(value = "/getcharges", method = RequestMethod.GET)
    public String getCharges(HttpServletRequest request, @RequestParam(value = "soChargeCodes") String[] soChargeCodes, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        String[] preChargeCodes = saleOrderMgr.filterGetSaleOrderChargeCodes(soChargeCodes, currentSaleOrder.getSaleOrderCharges());
        Map<String, SaleOrderCharge> getSaleOrderCharges = saleOrderMgr.getSaleOrderCharges(preChargeCodes);
        if (getSaleOrderCharges != null && getSaleOrderCharges.size() > 0) {
            Map<String, SaleOrderCharge> saleOrderCharges = saleOrderMgr.updateSaleOrderCharges(currentSaleOrder.getSaleOrderCharges(), getSaleOrderCharges);
            currentSaleOrder.setSaleOrderCharges(saleOrderCharges);
            saleOrderMgr.updateChargeAmount(currentSaleOrder);
        }
        request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/sochargetable";
    }

    @RequestMapping(value = "/deletesocharge", method = RequestMethod.GET)
    public String deleteSoCharge(HttpServletRequest request, @RequestParam(value = "soChargeCode") String soChargeCode, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        Map<String, SaleOrderCharge> saleOrderCharges = saleOrderMgr.removeSoCharge(soChargeCode, currentSaleOrder.getSaleOrderCharges());
        currentSaleOrder.setSaleOrderCharges(saleOrderCharges);
        saleOrderMgr.updateChargeAmount(currentSaleOrder);
        request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/sochargetable";
    }

    @RequestMapping(value = "/insertcharge", method = RequestMethod.POST)
    public ModelAndView insertCharge(SaleOrderCharge saleOrderCharge, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        Checker checker = saleOrderMgr.checkSaleOrderCharge(saleOrderCharge, currentSaleOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = saleOrderMgr.checkSaleOrderChargeDecimal(saleOrderCharge);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentSaleOrder.setCrtUsr(currentUser.getUsrId());
                    currentSaleOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentSaleOrder.setCrtUsr("test");
                    currentSaleOrder.setModUsr("test");
                }
                Map<String, SaleOrderCharge> saleOrderCharges = currentSaleOrder.getSaleOrderCharges();
                if (saleOrderCharges == null) saleOrderCharges = new LinkedHashMap<String, SaleOrderCharge>();
                saleOrderCharges.put(saleOrderCharge.getEncodeSo5ChgCode(), saleOrderCharge);
                currentSaleOrder.setSaleOrderCharges(saleOrderCharges);
                saleOrderMgr.updateChargeAmount(currentSaleOrder);
                request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(decimalChecker.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/addcharge")
    public String addCharge(Model model, HttpServletRequest request) {
        return "/management/saleorder/soaddcharge";
    }

    @RequestMapping("/refleshcharges")
    public String refleshCharges(@ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model, HttpServletRequest request) {
        model.addAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/sochargetable";
    }

    @RequestMapping("/editcharge/{fkChgCode}")
    public String editCharge(@PathVariable("fkChgCode") String fkChgCode, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model, HttpServletRequest request) {
        SaleOrderCharge saleOrderCharge = currentSaleOrder.getSaleOrderCharges().get(fkChgCode);
        model.addAttribute("soChargeEditMode", true);
        model.addAttribute("saleOrderCharge", saleOrderCharge);
        return "/management/saleorder/soeditcharge";
    }

    @RequestMapping(value = "/updatecharge", method = RequestMethod.POST)
    public ModelAndView updateCharge(SaleOrderCharge saleOrderCharge, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        Checker decimalChecker = saleOrderMgr.checkSaleOrderChargeDecimal(saleOrderCharge);
        if (decimalChecker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentSaleOrder.setCrtUsr(currentUser.getUsrId());
                currentSaleOrder.setModUsr(currentUser.getUsrId());
            } else {
                currentSaleOrder.setCrtUsr("test");
                currentSaleOrder.setModUsr("test");
            }
            Map<String, SaleOrderCharge> saleOrderCharges = currentSaleOrder.getSaleOrderCharges();
            if (saleOrderCharges == null) saleOrderCharges = new LinkedHashMap<String, SaleOrderCharge>();
            saleOrderCharges.put(saleOrderCharge.getEncodeSo5ChgCode(), saleOrderCharge);
            currentSaleOrder.setSaleOrderCharges(saleOrderCharges);
            saleOrderMgr.updateChargeAmount(currentSaleOrder);
            request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(decimalChecker.getReturnStr());
        }
    }

    @RequestMapping("/addsbk")
    public String addSbk(Model model, HttpServletRequest request) {
        return "/management/saleorder/soaddsbk";
    }

    @RequestMapping(value = "/insertsbk", method = RequestMethod.POST)
    public ModelAndView insertSbk(SaleOrderSbk saleOrderSbk, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        Checker checker = saleOrderMgr.checkSaleOrderSbk(saleOrderSbk, currentSaleOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = saleOrderMgr.checkSaleOrderSbkDecimal(saleOrderSbk);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentSaleOrder.setCrtUsr(currentUser.getUsrId());
                    currentSaleOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentSaleOrder.setCrtUsr("test");
                    currentSaleOrder.setModUsr("test");
                }
                Map<String, SaleOrderSbk> saleOrderSbks = currentSaleOrder.getSaleOrderSbks();
                if (saleOrderSbks == null) saleOrderSbks = new LinkedHashMap<String, SaleOrderSbk>();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(saleOrderSbk.getSo3ExpShpDate());
                saleOrderSbks.put(dateString, saleOrderSbk);
                currentSaleOrder.setSaleOrderSbks(saleOrderSbks);
                request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(decimalChecker.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/refleshsbks")
    public String refleshSbks(@ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model, HttpServletRequest request) {
        model.addAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/sosbktable";
    }

    @RequestMapping("/editsbk/{fkExpShpDate}")
    public String editSbk(@PathVariable("fkExpShpDate") String fkExpShpDate, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model, HttpServletRequest request) {
        SaleOrderSbk saleOrderSbk = currentSaleOrder.getSaleOrderSbks().get(fkExpShpDate);
        model.addAttribute("soSbkEditMode", true);
        model.addAttribute("saleOrderSbk", saleOrderSbk);
        return "/management/saleorder/soeditsbk";
    }

    @RequestMapping(value = "/updatesbk", method = RequestMethod.POST)
    public ModelAndView updateSbk(SaleOrderSbk saleOrderSbk, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        Checker decimalChecker = saleOrderMgr.checkSaleOrderSbkDecimal(saleOrderSbk);
        if (decimalChecker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentSaleOrder.setCrtUsr(currentUser.getUsrId());
                currentSaleOrder.setModUsr(currentUser.getUsrId());
            } else {
                currentSaleOrder.setCrtUsr("test");
                currentSaleOrder.setModUsr("test");
            }
            Map<String, SaleOrderSbk> saleOrderSbks = currentSaleOrder.getSaleOrderSbks();
            if (saleOrderSbks == null) saleOrderSbks = new LinkedHashMap<String, SaleOrderSbk>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(saleOrderSbk.getSo3ExpShpDate());
            saleOrderSbks.put(dateString, saleOrderSbk);
            currentSaleOrder.setSaleOrderSbks(saleOrderSbks);
            request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(decimalChecker.getReturnStr());
        }
    }

    @RequestMapping(value = "/deletesosbk", method = RequestMethod.GET)
    public String deleteSoSbk(HttpServletRequest request, @RequestParam(value = "fkExpShpDate") String fkExpShpDate, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        Map<String, SaleOrderSbk> saleOrderSbks = saleOrderMgr.removeSoSbk(fkExpShpDate, currentSaleOrder.getSaleOrderSbks());
        currentSaleOrder.setSaleOrderSbks(saleOrderSbks);
        request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/sosbktable";
    }

    @RequestMapping("/addlc")
    public String addLc(Model model, HttpServletRequest request) {
        return "/management/saleorder/soaddlc";
    }

    @RequestMapping(value = "/insertlc", method = RequestMethod.POST)
    public ModelAndView insertLc(SaleOrderLc saleOrderLc, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        Checker checker = saleOrderMgr.checkSaleOrderLc(saleOrderLc, currentSaleOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = saleOrderMgr.checkSaleOrderLcDecimal(saleOrderLc);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentSaleOrder.setCrtUsr(currentUser.getUsrId());
                    currentSaleOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentSaleOrder.setCrtUsr("test");
                    currentSaleOrder.setModUsr("test");
                }
                Map<String, SaleOrderLc> saleOrderLcs = currentSaleOrder.getSaleOrderLcs();
                if (saleOrderLcs == null) saleOrderLcs = new LinkedHashMap<String, SaleOrderLc>();
                saleOrderLcs.put(saleOrderLc.getEncodeSo4LcNo(), saleOrderLc);
                currentSaleOrder.setSaleOrderLcs(saleOrderLcs);
                request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(decimalChecker.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/refleshlcs")
    public String refleshLcs(@ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model, HttpServletRequest request) {
        model.addAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/solctable";
    }

    @RequestMapping("/editlc/{fkLcNo}")
    public String editLc(@PathVariable("fkLcNo") String fkLcNo, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model, HttpServletRequest request) {
        SaleOrderLc saleOrderLc = currentSaleOrder.getSaleOrderLcs().get(fkLcNo);
        model.addAttribute("soLcEditMode", true);
        model.addAttribute("saleOrderLc", saleOrderLc);
        return "/management/saleorder/soeditlc";
    }

    @RequestMapping(value = "/updatelc", method = RequestMethod.POST)
    public ModelAndView updateLc(SaleOrderLc saleOrderLc, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        Checker decimalChecker = saleOrderMgr.checkSaleOrderLcDecimal(saleOrderLc);
        if (decimalChecker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentSaleOrder.setCrtUsr(currentUser.getUsrId());
                currentSaleOrder.setModUsr(currentUser.getUsrId());
            } else {
                currentSaleOrder.setCrtUsr("test");
                currentSaleOrder.setModUsr("test");
            }
            Map<String, SaleOrderLc> saleOrderLcs = currentSaleOrder.getSaleOrderLcs();
            if (saleOrderLcs == null) saleOrderLcs = new LinkedHashMap<String, SaleOrderLc>();
            saleOrderLcs.put(saleOrderLc.getEncodeSo4LcNo(), saleOrderLc);
            currentSaleOrder.setSaleOrderLcs(saleOrderLcs);
            request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(decimalChecker.getReturnStr());
        }
    }

    @RequestMapping(value = "/deletesolc", method = RequestMethod.GET)
    public String deleteSoLc(HttpServletRequest request, @RequestParam(value = "fkLcNo") String fkLcNo, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        Map<String, SaleOrderLc> saleOrderLcs = saleOrderMgr.removeSoLc(fkLcNo, currentSaleOrder.getSaleOrderLcs());
        currentSaleOrder.setSaleOrderLcs(saleOrderLcs);
        request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/solctable";
    }

    @RequestMapping("/getitemindex")
    public String getItemIndex(HttpServletRequest request, Model model) {
        return "/management/saleorder/getitem";
    }

    @RequestMapping("/itemlist")
    public String itemlist(SoSearchItemConditionVo vo, Model model, HttpServletRequest request) {
        List<SoItemMasterVo> saleOrderItemVoList = saleOrderMgr.getItemListByCondition(vo);
        model.addAttribute("saleOrderItemVoList", saleOrderItemVoList);
        model.addAttribute("vo", vo);
        return "/management/saleorder/getitem";
    }

    @RequestMapping(value = "/getitems", method = RequestMethod.GET)
    public String getItems(HttpServletRequest request, @RequestParam(value = "soItemIds") String[] soItemIds, @RequestParam(value = "module") String module, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        String[] preSoItemIds = saleOrderMgr.filterGetSoItemIds(soItemIds, currentSaleOrder.getSaleOrderItems());
        Map<String, SaleOrderItem> getSaleOrderItems = saleOrderMgr.getSaleOrderItems(module, preSoItemIds);
        if (getSaleOrderItems != null && getSaleOrderItems.size() > 0) {
            Map<String, SaleOrderItem> saleOrderItems = saleOrderMgr.updateSaleOrderItems(currentSaleOrder.getSaleOrderItems(), getSaleOrderItems);
            currentSaleOrder.setSaleOrderItems(saleOrderItems);
        }
        saleOrderMgr.updateItemAmount(currentSaleOrder);
        request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/soitemtable";
    }

    @RequestMapping("/editsoitem/{soItCatNoSuffix}")
    public String editSoItem(@PathVariable("soItCatNoSuffix") String soItCatNoSuffix, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        SaleOrderItem currentSaleOrderItem = currentSaleOrder.getSaleOrderItems().get(soItCatNoSuffix);
        String templateName = itemMgr.getTemplateNameByItCatNoSuffix(soItCatNoSuffix);
        String detailVoStr = currentSaleOrderItem.getSo2ItDetails();
        if (detailVoStr == null || !detailVoStr.startsWith("{")) {
            detailVoStr = "{}";
            currentSaleOrderItem.setSo2ItDetails(detailVoStr);
        }
        Map<String, Object> detailVo = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            detailVo = mapper.readValue(detailVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("detailVo", detailVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("templateName", templateName);
        model.addAttribute("currentSaleOrderItem", currentSaleOrderItem);
        return "/management/saleorder/editsoitem";
    }

    @RequestMapping(value = "/updatesoitem", method = RequestMethod.POST)
    public ModelAndView updateSoItem(@ModelAttribute("currentSaleOrderItem") SaleOrderItem currentSaleOrderItem, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentSaleOrderItem.setModUsr(currentUser.getUsrId());
        } else {
            currentSaleOrderItem.setModUsr("test");
        }
        Map<String, SaleOrderItem> saleOrderItems = (LinkedHashMap<String, SaleOrderItem>) currentSaleOrder.getSaleOrderItems();
        saleOrderItems.put(currentSaleOrderItem.getSo2ItCatNoSuffix(), currentSaleOrderItem);
        currentSaleOrder.setSaleOrderItems(saleOrderItems);
        saleOrderMgr.updateItemAmount(currentSaleOrder);
        request.getSession().setAttribute("currentSaleOrderItem", null);
        request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping(value = "/refleshitems", method = RequestMethod.GET)
    public String refleshSoItemsTable(@ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        model.addAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/soitemtable";
    }

    @RequestMapping(value = "/deletesoitem", method = RequestMethod.GET)
    public String deleteOffItem(HttpServletRequest request, @RequestParam(value = "soItCatNoSuffix") String soItCatNoSuffix, @ModelAttribute("currentSaleOrder") SaleOrderDetailsVo currentSaleOrder, Model model) {
        Map<String, SaleOrderItem> saleOrderItems = saleOrderMgr.removeSoItem(soItCatNoSuffix, currentSaleOrder.getSaleOrderItems());
        currentSaleOrder.setSaleOrderItems(saleOrderItems);
        saleOrderMgr.updateItemAmount(currentSaleOrder);
        request.getSession().setAttribute("currentSaleOrder", currentSaleOrder);
        return "/management/saleorder/soitemtable";
    }
}
