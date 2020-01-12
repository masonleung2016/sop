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
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.PurchaseOrderCharge;
import sop.persistence.beans.PurchaseOrderItem;
import sop.persistence.beans.PurchaseOrderLc;
import sop.persistence.beans.PurchaseOrderSbk;
import sop.services.ChargeServiceMgr;
import sop.services.ItemServiceMgr;
import sop.services.PoServiceMgr;
import sop.services.SaleOrderServiceMgr;
import sop.vo.ChargeVo;
import sop.vo.PoItemMasterVo;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.PurchaseOrderCombos;
import sop.vo.PurchaseOrderDetailsVo;
import sop.vo.PurchaseOrderVo;
import sop.vo.SaleOrderVo;
import sop.vo.SearchItemConditionVo;
import sop.vo.SoPoConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:56
 * @Package: sop.web.management
 */

@Controller("management.poController")
@RequestMapping(value = "/management/po")
@SessionAttributes({"currentPurchaseOrder"})
public class PoController extends BaseController {

    @Autowired
    private PoServiceMgr poMgr;

    @Autowired
    private ItemServiceMgr itemMgr;

    @Autowired
    private ChargeServiceMgr chargeMgr;

    @Autowired
    private SaleOrderServiceMgr saleOrderMgr;

    @RequestMapping("/list")
    public String list(SoPoConditionVo vo, Model model, HttpServletRequest request) {

        List<PurchaseOrderVo> purchaseOrderVo = poMgr.searchPurchaseOrderVo(vo);
        Integer totalCount = poMgr.searchPurchaseOrderVoNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("purchaseOrderVo", purchaseOrderVo);

        return "/management/po/list";
    }

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        PurchaseOrderDetailsVo currentPurchaseOrder = new PurchaseOrderDetailsVo();
        if (currentUser != null) {
            currentPurchaseOrder.setCoCode(currentUser.getCoCode());
        } else {
            currentPurchaseOrder.setCoCode("tst");
        }
        currentPurchaseOrder.setPoDate(new Date());
        PurchaseOrderCombos purchaseOrderCombos = poMgr.getPurchaseOrderCombos();
        model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
        model.addAttribute("purchaseOrderCombos", purchaseOrderCombos);
        return "/management/po/add";
    }

//	private PurchaseOrderDetailsVo testAdd(){
//		PurchaseOrderDetailsVo currentPurchaseOrder = new PurchaseOrderDetailsVo();
//		//item
//		PurchaseOrderItem item = new PurchaseOrderItem();
//		item.setPo2ItCat("CL");
//		item.setPo2ItNo("10005");
//		item.setPo2ItSuffix("00");
//		item.setPo2ItName("test");
//		Map<String, PurchaseOrderItem> purchaseOrderItems = new HashMap<String, PurchaseOrderItem>();
//		purchaseOrderItems.put("CL1000500", item);
//		currentPurchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
//		//charge
//		PurchaseOrderCharge charge = new PurchaseOrderCharge();
//		charge.setPo5ChgCode("test");
//		charge.setPo5Ccy("test");
//		Map<String, PurchaseOrderCharge> purchaseOrderCharges = new HashMap<String, PurchaseOrderCharge>();
//		purchaseOrderCharges.put("test", charge);
//		currentPurchaseOrder.setPurchaseOrderCharges(purchaseOrderCharges);
//		return currentPurchaseOrder;
//	}

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        Checker checker = poMgr.checkPurchaseOrder(currentPurchaseOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = poMgr.checkDecimal(currentPurchaseOrder);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentPurchaseOrder.setCrtUsr(currentUser.getUsrId());
                    currentPurchaseOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentPurchaseOrder.setCrtUsr("test");
                    currentPurchaseOrder.setModUsr("test");
                }
                if (poMgr.addPurchaseOrder(currentPurchaseOrder)) {
                    return ajaxDoneSuccess(getMessage("msg.operation.success"));
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            } else {
                return ajaxDoneError(decimalChecker.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{fkPoNo}")
    public String edit(@PathVariable("fkPoNo") String fkPoNo, Model model, HttpServletRequest request) {
        fkPoNo = Base64.decode(fkPoNo, "utf-8");
        PurchaseOrderCombos purchaseOrderCombos = poMgr.getPurchaseOrderCombos();
        PurchaseOrderDetailsVo currentPurchaseOrder = poMgr.getPurchaseOrderDetailVoByFkPoNo(fkPoNo);

        model.addAttribute("purchaseOrderCombos", purchaseOrderCombos);
        model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/edit";
    }

    @RequestMapping("/copy/{fkPoNo}")
    public String copy(@PathVariable("fkPoNo") String fkPoNo, Model model, HttpServletRequest request) {
        fkPoNo = Base64.decode(fkPoNo, "utf-8");
        PurchaseOrderCombos purchaseOrderCombos = poMgr.getPurchaseOrderCombos();
        PurchaseOrderDetailsVo currentPurchaseOrder = poMgr.getPurchaseOrderDetailVoByFkPoNo(fkPoNo);
        poMgr.clearPo(currentPurchaseOrder);
        model.addAttribute("purchaseOrderCombos", purchaseOrderCombos);
        model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/add";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentPurchaseOrder.setModUsr(currentUser.getUsrId());
            if (currentPurchaseOrder.getCoCode().equals("")) {
                currentPurchaseOrder.setCoCode(currentUser.getCoCode());
            }
        } else {
            currentPurchaseOrder.setModUsr("test");
            if (currentPurchaseOrder.getCoCode().equals("")) {
                currentPurchaseOrder.setCoCode("tst");
            }
        }
        if (poMgr.updatePurchaseOrder(currentPurchaseOrder)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping("/chargelist")
    public String chargelist(SearchItemConditionVo vo, Model model, HttpServletRequest request) {
        List<ChargeVo> purchaseOrderChargeVoList = chargeMgr.getAllCharges();
        model.addAttribute("purchaseOrderChargeVoList", purchaseOrderChargeVoList);
        model.addAttribute("vo", vo);
        return "/management/po/getcharge";
    }

    @RequestMapping(value = "/getcharges", method = RequestMethod.GET)
    public String getCharges(HttpServletRequest request, @RequestParam(value = "poChargeCodes") String[] poChargeCodes, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model) {
        String[] preChargeCodes = poMgr.filterGetPurchaseOrderChargeCodes(poChargeCodes, currentPurchaseOrder.getPurchaseOrderCharges());
        Map<String, PurchaseOrderCharge> getPurchaseOrderCharges = poMgr.getPurchaseOrderCharges(preChargeCodes);
        if (getPurchaseOrderCharges != null && getPurchaseOrderCharges.size() > 0) {
            Map<String, PurchaseOrderCharge> purchaseOrderCharges = poMgr.updatePurchaseOrderCharges(currentPurchaseOrder.getPurchaseOrderCharges(), getPurchaseOrderCharges);
            currentPurchaseOrder.setPurchaseOrderCharges(purchaseOrderCharges);
            poMgr.updateChargeAmount(currentPurchaseOrder);
        }
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/pochargetable";
    }

    @RequestMapping(value = "/deletepocharge", method = RequestMethod.GET)
    public String deletePoCharge(HttpServletRequest request, @RequestParam(value = "poChargeCode") String poChargeCode, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model) {
        Map<String, PurchaseOrderCharge> purchaseOrderCharges = poMgr.removePoCharge(poChargeCode, currentPurchaseOrder.getPurchaseOrderCharges());
        currentPurchaseOrder.setPurchaseOrderCharges(purchaseOrderCharges);
        poMgr.updateChargeAmount(currentPurchaseOrder);
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/pochargetable";
    }

    @RequestMapping(value = "/insertcharge", method = RequestMethod.POST)
    public ModelAndView insertCharge(PurchaseOrderCharge purchaseOrderCharge, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        Checker checker = poMgr.checkPurchaseOrderCharge(purchaseOrderCharge, currentPurchaseOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = poMgr.checkPurchaseOrderChargeDecimal(purchaseOrderCharge);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentPurchaseOrder.setCrtUsr(currentUser.getUsrId());
                    currentPurchaseOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentPurchaseOrder.setCrtUsr("test");
                    currentPurchaseOrder.setModUsr("test");
                }
                Map<String, PurchaseOrderCharge> purchaseOrderCharges = currentPurchaseOrder.getPurchaseOrderCharges();
                if (purchaseOrderCharges == null)
                    purchaseOrderCharges = new LinkedHashMap<String, PurchaseOrderCharge>();
                purchaseOrderCharges.put(purchaseOrderCharge.getEncodePo5ChgCode(), purchaseOrderCharge);
                currentPurchaseOrder.setPurchaseOrderCharges(purchaseOrderCharges);
                poMgr.updateChargeAmount(currentPurchaseOrder);
                request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
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
        model.addAttribute("purchaseOrderCharge", null);
        return "/management/po/poaddcharge";
    }

    @RequestMapping("/refleshcharges")
    public String refleshCharges(@ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model, HttpServletRequest request) {
        model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/pochargetable";
    }

    @RequestMapping("/editcharge/{fkChgCode}")
    public String editCharge(@PathVariable("fkChgCode") String fkChgCode, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model, HttpServletRequest request) {
        PurchaseOrderCharge purchaseOrderCharge = currentPurchaseOrder.getPurchaseOrderCharges().get(fkChgCode);
        model.addAttribute("poChargeEditMode", true);
        model.addAttribute("purchaseOrderCharge", purchaseOrderCharge);
        return "/management/po/poeditcharge";
    }

    @RequestMapping(value = "/updatecharge", method = RequestMethod.POST)
    public ModelAndView updateCharge(PurchaseOrderCharge purchaseOrderCharge, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        Checker decimalChecker = poMgr.checkPurchaseOrderChargeDecimal(purchaseOrderCharge);
        if (decimalChecker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentPurchaseOrder.setCrtUsr(currentUser.getUsrId());
                currentPurchaseOrder.setModUsr(currentUser.getUsrId());
            } else {
                currentPurchaseOrder.setCrtUsr("test");
                currentPurchaseOrder.setModUsr("test");
            }
            Map<String, PurchaseOrderCharge> purchaseOrderCharges = currentPurchaseOrder.getPurchaseOrderCharges();
            if (purchaseOrderCharges == null) purchaseOrderCharges = new LinkedHashMap<String, PurchaseOrderCharge>();
            purchaseOrderCharges.put(purchaseOrderCharge.getEncodePo5ChgCode(), purchaseOrderCharge);
            currentPurchaseOrder.setPurchaseOrderCharges(purchaseOrderCharges);
            poMgr.updateChargeAmount(currentPurchaseOrder);
            request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(decimalChecker.getReturnStr());
        }
    }

    @RequestMapping("/addsbk")
    public String addSbk(Model model, HttpServletRequest request) {
        return "/management/po/poaddsbk";
    }

    @RequestMapping(value = "/insertsbk", method = RequestMethod.POST)
    public ModelAndView insertSbk(PurchaseOrderSbk purchaseOrderSbk, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        Checker checker = poMgr.checkPurchaseOrderSbk(purchaseOrderSbk, currentPurchaseOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = poMgr.checkPurchaseOrderSbkDecimal(purchaseOrderSbk);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentPurchaseOrder.setCrtUsr(currentUser.getUsrId());
                    currentPurchaseOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentPurchaseOrder.setCrtUsr("test");
                    currentPurchaseOrder.setModUsr("test");
                }
                Map<String, PurchaseOrderSbk> purchaseOrderSbks = currentPurchaseOrder.getPurchaseOrderSbks();
                if (purchaseOrderSbks == null) purchaseOrderSbks = new LinkedHashMap<String, PurchaseOrderSbk>();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(purchaseOrderSbk.getPo3ExpShpDate());
                purchaseOrderSbks.put(dateString, purchaseOrderSbk);
                currentPurchaseOrder.setPurchaseOrderSbks(purchaseOrderSbks);
                request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(decimalChecker.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/refleshsbks")
    public String refleshSbks(@ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model, HttpServletRequest request) {
        model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/posbktable";
    }

    @RequestMapping("/editsbk/{fkExpShpDate}")
    public String editSbk(@PathVariable("fkExpShpDate") String fkExpShpDate, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model, HttpServletRequest request) {
        PurchaseOrderSbk purchaseOrderSbk = currentPurchaseOrder.getPurchaseOrderSbks().get(fkExpShpDate);
        model.addAttribute("poSbkEditMode", true);
        model.addAttribute("purchaseOrderSbk", purchaseOrderSbk);
        return "/management/po/poeditsbk";
    }

    @RequestMapping(value = "/updatesbk", method = RequestMethod.POST)
    public ModelAndView updateSbk(PurchaseOrderSbk purchaseOrderSbk, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        Checker decimalChecker = poMgr.checkPurchaseOrderSbkDecimal(purchaseOrderSbk);
        if (decimalChecker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentPurchaseOrder.setCrtUsr(currentUser.getUsrId());
                currentPurchaseOrder.setModUsr(currentUser.getUsrId());
            } else {
                currentPurchaseOrder.setCrtUsr("test");
                currentPurchaseOrder.setModUsr("test");
            }
            Map<String, PurchaseOrderSbk> purchaseOrderSbks = currentPurchaseOrder.getPurchaseOrderSbks();
            if (purchaseOrderSbks == null) purchaseOrderSbks = new LinkedHashMap<String, PurchaseOrderSbk>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(purchaseOrderSbk.getPo3ExpShpDate());
            purchaseOrderSbks.put(dateString, purchaseOrderSbk);
            currentPurchaseOrder.setPurchaseOrderSbks(purchaseOrderSbks);
            request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(decimalChecker.getReturnStr());
        }
    }

    @RequestMapping(value = "/deleteposbk", method = RequestMethod.GET)
    public String deletePoSbk(HttpServletRequest request, @RequestParam(value = "fkExpShpDate") String fkExpShpDate, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model) {
        Map<String, PurchaseOrderSbk> purchaseOrderSbks = poMgr.removePoSbk(fkExpShpDate, currentPurchaseOrder.getPurchaseOrderSbks());
        currentPurchaseOrder.setPurchaseOrderSbks(purchaseOrderSbks);
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/posbktable";
    }

    @RequestMapping("/addlc")
    public String addLc(Model model, HttpServletRequest request) {
        return "/management/po/poaddlc";
    }

    @RequestMapping(value = "/insertlc", method = RequestMethod.POST)
    public ModelAndView insertLc(PurchaseOrderLc purchaseOrderLc, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        Checker checker = poMgr.checkPurchaseOrderLc(purchaseOrderLc, currentPurchaseOrder);
        if (checker.isSuccess()) {
            Checker decimalChecker = poMgr.checkPurchaseOrderLcDecimal(purchaseOrderLc);
            if (decimalChecker.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentPurchaseOrder.setCrtUsr(currentUser.getUsrId());
                    currentPurchaseOrder.setModUsr(currentUser.getUsrId());
                } else {
                    currentPurchaseOrder.setCrtUsr("test");
                    currentPurchaseOrder.setModUsr("test");
                }
                Map<String, PurchaseOrderLc> purchaseOrderLcs = currentPurchaseOrder.getPurchaseOrderLcs();
                if (purchaseOrderLcs == null) purchaseOrderLcs = new LinkedHashMap<String, PurchaseOrderLc>();
                purchaseOrderLcs.put(purchaseOrderLc.getEncodePo4LcNo(), purchaseOrderLc);
                currentPurchaseOrder.setPurchaseOrderLcs(purchaseOrderLcs);
                request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(decimalChecker.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/refleshlcs")
    public String refleshLcs(@ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model, HttpServletRequest request) {
        model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/polctable";
    }

    @RequestMapping("/editlc/{fkLcNo}")
    public String editLc(@PathVariable("fkLcNo") String fkLcNo, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model, HttpServletRequest request) {
        PurchaseOrderLc purchaseOrderLc = currentPurchaseOrder.getPurchaseOrderLcs().get(fkLcNo);
        model.addAttribute("poLcEditMode", true);
        model.addAttribute("purchaseOrderLc", purchaseOrderLc);
        return "/management/po/poeditlc";
    }

    @RequestMapping(value = "/updatelc", method = RequestMethod.POST)
    public ModelAndView updateLc(PurchaseOrderLc purchaseOrderLc, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        Checker decimalChecker = poMgr.checkPurchaseOrderLcDecimal(purchaseOrderLc);
        if (decimalChecker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentPurchaseOrder.setCrtUsr(currentUser.getUsrId());
                currentPurchaseOrder.setModUsr(currentUser.getUsrId());
            } else {
                currentPurchaseOrder.setCrtUsr("test");
                currentPurchaseOrder.setModUsr("test");
            }
            Map<String, PurchaseOrderLc> purchaseOrderLcs = currentPurchaseOrder.getPurchaseOrderLcs();
            if (purchaseOrderLcs == null) purchaseOrderLcs = new LinkedHashMap<String, PurchaseOrderLc>();
            purchaseOrderLcs.put(purchaseOrderLc.getEncodePo4LcNo(), purchaseOrderLc);
            currentPurchaseOrder.setPurchaseOrderLcs(purchaseOrderLcs);
            request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(decimalChecker.getReturnStr());
        }
    }

    @RequestMapping(value = "/deletepolc", method = RequestMethod.GET)
    public String deletePoLc(HttpServletRequest request, @RequestParam(value = "fkLcNo") String fkLcNo, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model) {
        Map<String, PurchaseOrderLc> purchaseOrderLcs = poMgr.removePoLc(fkLcNo, currentPurchaseOrder.getPurchaseOrderLcs());
        currentPurchaseOrder.setPurchaseOrderLcs(purchaseOrderLcs);
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/polctable";
    }

    @RequestMapping("/editpoitem/{poItCatNoSuffix}")
    public String editPoItem(@PathVariable("poItCatNoSuffix") String poItCatNoSuffix, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model) {
        PurchaseOrderItem currentPurchaseOrderItem = currentPurchaseOrder.getPurchaseOrderItems().get(poItCatNoSuffix);
        String templateName = itemMgr.getTemplateNameByItCatNoSuffix(poItCatNoSuffix);
        String detailVoStr = currentPurchaseOrderItem.getPo2ItDetails();
        if (detailVoStr == null || !detailVoStr.startsWith("{")) {
            detailVoStr = "{}";
            currentPurchaseOrderItem.setPo2ItDetails(detailVoStr);
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
        model.addAttribute("currentPurchaseOrderItem", currentPurchaseOrderItem);
        return "/management/po/editpoitem";
    }

    @RequestMapping(value = "/updatepoitem", method = RequestMethod.POST)
    public ModelAndView updatePoItem(@ModelAttribute("currentPurchaseOrderItem") PurchaseOrderItem currentPurchaseOrderItem, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentPurchaseOrderItem.setModUsr(currentUser.getUsrId());
        } else {
            currentPurchaseOrderItem.setModUsr("test");
        }
        Map<String, PurchaseOrderItem> purchaseOrderItems = (LinkedHashMap<String, PurchaseOrderItem>) currentPurchaseOrder.getPurchaseOrderItems();
        purchaseOrderItems.put(currentPurchaseOrderItem.getPo2ItCatNoSuffix(), currentPurchaseOrderItem);
        currentPurchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
        poMgr.updateItemAmount(currentPurchaseOrder);
        request.getSession().setAttribute("currentPurchaseOrderItem", null);
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping(value = "/refleshitems", method = RequestMethod.GET)
    public String refleshPoItemsTable(@ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model, HttpServletRequest request) {
        model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/poitemtable";
    }

    @RequestMapping(value = "/deletepoitem", method = RequestMethod.GET)
    public String deletePoItem(HttpServletRequest request, @RequestParam(value = "poItCatNoSuffix") String poItCatNoSuffix, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model) {
        Map<String, PurchaseOrderItem> purchaseOrderItems = poMgr.removeSoItem(poItCatNoSuffix, currentPurchaseOrder.getPurchaseOrderItems());
        currentPurchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
        poMgr.updateItemAmount(currentPurchaseOrder);
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/poitemtable";
    }

    @RequestMapping("/getitemindex")
    public String getItemIndex(HttpServletRequest request, Model model) {
        return "/management/po/getitem";
    }

    @RequestMapping("/itemlist")
    public String itemlist(PoSearchItemConditionVo vo, Model model, HttpServletRequest request) {
        List<PoItemMasterVo> purchaseOrderItemVoList = poMgr.getItemListByCondition(vo);
        model.addAttribute("purchaseOrderItemVoList", purchaseOrderItemVoList);
        model.addAttribute("vo", vo);
        return "/management/po/getitem";
    }

    @RequestMapping(value = "/getitems", method = RequestMethod.GET)
    public String getItems(HttpServletRequest request, @RequestParam(value = "poItemIds") String[] poItemIds, @RequestParam(value = "module") String module, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder, Model model) {
        String[] prePoItemIds = poMgr.filterGetPoItemIds(poItemIds, currentPurchaseOrder.getPurchaseOrderItems());
        Map<String, PurchaseOrderItem> getPurchaseOrderItems = poMgr.getPurchaseOrderItems(module, prePoItemIds);
        if (getPurchaseOrderItems != null && getPurchaseOrderItems.size() > 0) {
            Map<String, PurchaseOrderItem> purchaseOrderItems = poMgr.updatePurchaseOrderItems(currentPurchaseOrder.getPurchaseOrderItems(), getPurchaseOrderItems);
            currentPurchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
        }
        poMgr.updateItemAmount(currentPurchaseOrder);
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        return "/management/po/poitemtable";
    }

    @RequestMapping("/solist")
    public String soList(SoPoConditionVo vo, Model model) {

        List<SaleOrderVo> saleOrderVoList = saleOrderMgr.searchSaleOrderVo(vo);
        Integer totalCount = saleOrderMgr.searchSaleOrderVoNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("saleOrderVoList", saleOrderVoList);

        return "/management/po/getso";
    }

    @RequestMapping(value = "/selectso", method = RequestMethod.GET)
    public String selectSo(HttpServletRequest request, @RequestParam(value = "selectSoId") String selectSoId, Model model, @ModelAttribute("currentPurchaseOrder") PurchaseOrderDetailsVo currentPurchaseOrder) {
        selectSoId = Base64.decode(selectSoId, "utf-8");
        poMgr.getPurchaseOrderBySelectSoId(currentPurchaseOrder, selectSoId);
        PurchaseOrderCombos purchaseOrderCombos = poMgr.getPurchaseOrderCombos();
        request.getSession().setAttribute("currentPurchaseOrder", currentPurchaseOrder);
        model.addAttribute("purchaseOrderCombos", purchaseOrderCombos);
        return "/management/po/po";
    }

    @RequestMapping("/delete/{fkPoNo}")
    public ModelAndView delete(@PathVariable("fkPoNo") String fkPoNo, Model model, HttpServletRequest request) {
        fkPoNo = Base64.decode(fkPoNo, "utf-8");
        boolean flag = poMgr.delPurchaseOrderByFkPoNo(fkPoNo);
        if (flag) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }
}
