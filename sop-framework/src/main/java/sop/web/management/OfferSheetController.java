package sop.web.management;


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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.CustAttn;
import sop.persistence.beans.OfferSheet;
import sop.persistence.beans.OfferSheetItem;
import sop.services.ItemServiceMgr;
import sop.services.OfferSheetServiceMgr;
import sop.vo.OffItemMasterVo;
import sop.vo.OffSearchItemConditionVo;
import sop.vo.OfferSheetCombos;
import sop.vo.OfferSheetConditionVo;
import sop.vo.OfferSheetVo;


/**
 * @Author: LCF
 * @Date: 2020/1/9 12:52
 * @Package: sop.web.management
 */


@Controller("management.offerSheetController")
@RequestMapping(value = "/management/offersheet")
@SessionAttributes({"currentOfferSheet", "currentOfferSheetItem"})
public class OfferSheetController extends BaseController {

    @Autowired
    private OfferSheetServiceMgr offerSheetMgr;

    @Autowired
    private ItemServiceMgr itemMgr;

    @RequestMapping("/list")
    public String list(OfferSheetConditionVo vo, Model model) {
        offerSheetMgr.handleOffVo(vo);

        List<OfferSheetVo> offerSheetVoList = offerSheetMgr.searchOfferSheetVo(vo);
        Integer totalCount = offerSheetMgr.searchOfferSheetVoNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("offerSheetVoList", offerSheetVoList);

        return "/management/offersheet/list";
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest request, Model model) {
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        OfferSheet currentOfferSheet = new OfferSheet();
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentOfferSheet.setCoCode(currentUser.getCoCode());
        } else {
            currentOfferSheet.setCoCode("tst");
        }
        currentOfferSheet.setCrtDate(new Date());
        currentOfferSheet.setOffDate(new Date());
        Map<String, OfferSheetItem> offerSheetItems = new LinkedHashMap<String, OfferSheetItem>();
        currentOfferSheet.setOfferSheetItems(offerSheetItems);
        model.addAttribute("currentOfferSheet", currentOfferSheet);
        model.addAttribute("offerSheetCombos", offerSheetCombos);
        return "/management/offersheet/add";
    }

    @RequestMapping("/getitemindex")
    public String getItemIndex(HttpServletRequest request, Model model) {
        return "/management/offersheet/getitem";
    }

    @RequestMapping("/itemlist")
    public String itemlist(OffSearchItemConditionVo vo, Model model, HttpServletRequest request) {
        List<OffItemMasterVo> offerSheetItemVoList = offerSheetMgr.getItemListByCondition(vo);
        model.addAttribute("offerSheetItemVoList", offerSheetItemVoList);
        model.addAttribute("vo", vo);
        return "/management/offersheet/getitem";
    }

    @RequestMapping(value = "/getitems", method = RequestMethod.GET)
    public String getItems(HttpServletRequest request, @RequestParam(value = "offItemIds") String[] offItemIds, @RequestParam(value = "module") String module, @ModelAttribute("currentOfferSheet") OfferSheet currentOfferSheet, Model model) {
        String[] preOffItemIds = offerSheetMgr.filterGetOffItemIds(offItemIds, currentOfferSheet.getOfferSheetItems());
        Map<String, OfferSheetItem> getOfferSheetItems = offerSheetMgr.getOfferSheetItems(module, preOffItemIds);
        if (getOfferSheetItems != null && getOfferSheetItems.size() > 0) {
            Map<String, OfferSheetItem> offerSheetItems = offerSheetMgr.updateOfferSheetItems(currentOfferSheet.getOfferSheetItems(), getOfferSheetItems);
            currentOfferSheet.setOfferSheetItems(offerSheetItems);
        }
        request.getSession().setAttribute("currentOfferSheet", currentOfferSheet);
        return "/management/offersheet/offitemtable";
    }

    @RequestMapping("/editoffitem/{offItCatNoSuffix}")
    public String editOffItem(@PathVariable("offItCatNoSuffix") String offItCatNoSuffix, @ModelAttribute("currentOfferSheet") OfferSheet currentOfferSheet, Model model) {
        OfferSheetItem currentOfferSheetItem = currentOfferSheet.getOfferSheetItems().get(offItCatNoSuffix);
        String templateName = itemMgr.getTemplateNameByItCatNoSuffix(offItCatNoSuffix);
        String detailVoStr = currentOfferSheetItem.getOff2DescOfferSh();
        if (detailVoStr == null || !detailVoStr.startsWith("{")) {
            detailVoStr = "{}";
            currentOfferSheetItem.setOff2DescOfferSh(detailVoStr);
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
        model.addAttribute("currentOfferSheetItem", currentOfferSheetItem);
        return "/management/offersheet/editoffitem";
    }

    @RequestMapping(value = "/deleteoffitem", method = RequestMethod.GET)
    public String deleteOffItem(HttpServletRequest request, @RequestParam(value = "offItCatNoSuffix") String offItCatNoSuffix, @ModelAttribute("currentOfferSheet") OfferSheet currentOfferSheet, Model model) {
        Map<String, OfferSheetItem> offerSheetItems = offerSheetMgr.removeOffItem(offItCatNoSuffix, currentOfferSheet.getOfferSheetItems());
        currentOfferSheet.setOfferSheetItems(offerSheetItems);
        request.getSession().setAttribute("currentOfferSheet", currentOfferSheet);
        return "/management/offersheet/offitemtable";
    }

    @RequestMapping(value = "/updateoffitem", method = RequestMethod.POST)
    public ModelAndView updateOffItem(@ModelAttribute("currentOfferSheetItem") OfferSheetItem currentOfferSheetItem, @ModelAttribute("currentOfferSheet") OfferSheet currentOfferSheet, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentOfferSheetItem.setModUsr(currentUser.getUsrId());
        } else {
            currentOfferSheetItem.setModUsr("test");
        }
        Map<String, OfferSheetItem> offerSheetItems = (LinkedHashMap<String, OfferSheetItem>) currentOfferSheet.getOfferSheetItems();
        offerSheetItems.remove(currentOfferSheetItem.getOff2ItCatNoSuffix());
        offerSheetItems.put(currentOfferSheetItem.getOff2ItCatNoSuffix(), currentOfferSheetItem);
        currentOfferSheet.setOfferSheetItems(offerSheetItems);
        currentOfferSheetItem = null;
        request.getSession().setAttribute("currentOfferSheetItem", currentOfferSheetItem);
        request.getSession().setAttribute("currentOfferSheet", currentOfferSheet);


        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping(value = "/refleshitems", method = RequestMethod.GET)
    public String refleshOffItemsTable(@ModelAttribute("currentOfferSheet") OfferSheet currentOfferSheet, Model model) {
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        offerSheetMgr.getCustAttnsByCuCode(currentOfferSheet, offerSheetCombos);
        model.addAttribute("currentOfferSheet", currentOfferSheet);
        model.addAttribute("offerSheetCombos", offerSheetCombos);
        return "/management/offersheet/edit";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute("currentOfferSheet") OfferSheet currentOfferSheet, HttpServletRequest request) {
        Checker checker = offerSheetMgr.checkOfferSheet(currentOfferSheet);
        if (checker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentOfferSheet.setCrtUsr(currentUser.getUsrId());
                currentOfferSheet.setModUsr(currentUser.getUsrId());
            } else {
                currentOfferSheet.setCrtUsr("test");
                currentOfferSheet.setModUsr("test");
            }
            try {
                if (offerSheetMgr.addOfferSheet(currentOfferSheet)) {
                    return ajaxDoneSuccess(getMessage("msg.operation.success"));
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            } catch (ServiceException e) {
                e.printStackTrace();
                return ajaxDoneError(e.getMessage());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{encodeOffShNoPfixNo}")
    public String edit(@PathVariable("encodeOffShNoPfixNo") String encodeOffShNoPfixNo, Model model, HttpServletRequest request) {
        String offShNoPfixNo = Base64.decode(encodeOffShNoPfixNo, "utf-8");
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        OfferSheet currentOfferSheet = offerSheetMgr.getOfferSheetByOffShNoPfixNo(offShNoPfixNo);
        offerSheetMgr.getCustAttnsByCuCode(currentOfferSheet, offerSheetCombos);
        model.addAttribute("currentOfferSheet", currentOfferSheet);
        model.addAttribute("offerSheetCombos", offerSheetCombos);
        return "/management/offersheet/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentOfferSheet") OfferSheet currentOfferSheet, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentOfferSheet.setModUsr(currentUser.getUsrId());
        } else {
            currentOfferSheet.setModUsr("test");
        }
        try {
            if (offerSheetMgr.updateOfferSheet(currentOfferSheet)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
    }

    @RequestMapping("/copy/{encodeOffShNoPfixNo}")
    public String copy(@PathVariable("encodeOffShNoPfixNo") String encodeOffShNoPfixNo, Model model, HttpServletRequest request) {
        String offShNoPfixNo = Base64.decode(encodeOffShNoPfixNo, "utf-8");
        OfferSheetCombos offerSheetCombos = offerSheetMgr.getOfferSheetCombos();
        OfferSheet currentOfferSheet = offerSheetMgr.getOfferSheetByOffShNoPfixNo(offShNoPfixNo);
        offerSheetMgr.clearOff(currentOfferSheet);
        model.addAttribute("currentOfferSheet", currentOfferSheet);
        model.addAttribute("offerSheetCombos", offerSheetCombos);
        return "/management/offersheet/add";
    }

    @RequestMapping("/delete/{encodeOffShNoPfixNo}")
    public ModelAndView delete(@PathVariable("encodeOffShNoPfixNo") String encodeOffShNoPfixNo, Model model, HttpServletRequest request) {
        String offShNoPfixNo = Base64.decode(encodeOffShNoPfixNo, "utf-8");
        try {
            if (offerSheetMgr.delOfferSheetByOffShNoPfixNo(offShNoPfixNo)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (Exception e) {
            return ajaxDoneError(e.toString());
        }
    }

    @RequestMapping(value = "/getcustattnsbyfkcust", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCustAttnsByFkCust(@RequestParam(value = "fkCust") String fkCust, Model model) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        List<CustAttn> custAttns = offerSheetMgr.getCustAttnsByCuCode(fkCust);
        result.put("custAttns", custAttns);
        return result;
    }
}
