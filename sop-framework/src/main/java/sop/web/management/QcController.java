package sop.web.management;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import dwz.framework.vo.Checker;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.QcDocFlowMapper;
import dwz.persistence.mapper.QcMapper;
import dwz.persistence.mapper.StaffMapper;
import dwz.persistence.mapper.SysUserMapper;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import net.sf.json.JSONObject;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.OfferSheet;
import sop.persistence.beans.QcCheckList;
import sop.persistence.beans.QcImage;
import sop.persistence.beans.QcItemBase;
import sop.persistence.beans.QualityCheck;
import sop.persistence.beans.QualityCheckItem;
import sop.services.ChargeServiceMgr;
import sop.services.ItemServiceMgr;
import sop.services.OfferSheetServiceMgr;
import sop.services.PoServiceMgr;
import sop.services.QcCheckListServiceMgr;
import sop.services.QcServiceMgr;
import sop.services.SaleOrderServiceMgr;
import sop.utils.Common;
import sop.vo.PurchaseOrderDetailsVo;
import sop.vo.QualityCheckConditionVo;
import sop.vo.QualityCheckDetailsVo;
import sop.vo.QualityCheckItemSingleFormVo;
import sop.vo.QualityCheckItemVo;
import sop.vo.QualityCheckVo;
import sop.vo.SaleOrderDetailsVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:58
 * @Package: sop.web.management
 */

@Controller("management.qcController")
@RequestMapping(value = "/management/qc")
@SessionAttributes({"currentQc", "currentQcItem"})
public class QcController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QcServiceMgr qcMgr;

    @Autowired
    private PoServiceMgr poMgr;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private QcCheckListServiceMgr qcCheckListServiceMgr;

    @Autowired
    private ItemServiceMgr itemMgr;

    @Autowired
    private QcDocFlowMapper qcDocFlowMapper;

    @Autowired
    private ChargeServiceMgr chargeMgr;

    @Autowired
    private SaleOrderServiceMgr saleOrderMgr;

    @Autowired
    private OfferSheetServiceMgr offerSheetMgr;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private QcMapper qcMapper;

    @RequestMapping("/list")
    public String list(QualityCheckConditionVo vo, Model model, HttpServletRequest request) {

        List<QualityCheckVo> qcPurchaseOrderVoList = qcMgr.searchQualityCheckVo(vo);
        Integer totalCount = qcMgr.searchQualityCheckVoNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("qcPurchaseOrderVoList", qcPurchaseOrderVoList);

        return "/management/qc/list";
    }

    @RequestMapping(value = "/validatepono", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validatePoNo(@RequestParam(value = "encodePoNo") String encodePoNo, Model model) {

        String poNo = Base64.decode(encodePoNo, "utf-8");
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        boolean havedQc = qcMgr.validatePoNo(poNo);
        result.put("havedQc", havedQc);
        return result;
    }

    @RequestMapping(value = "/initaddqc")
    public String initAddQc(Model model, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        QualityCheckDetailsVo currentQc = (QualityCheckDetailsVo) WebUtils.getSessionAttribute(request, "currentQc");
        if (currentQc == null) {
            currentQc = new QualityCheckDetailsVo();
        }

        model.addAttribute("users", sysUserMapper.findAllByRole("QC"));
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        String qcNo = currentQc.getQcNo();

        String poNo = StringUtils.isEmpty(currentQc.getQcPoNoRef()) ? request.getParameter("qcPoNoRef") : currentQc.getQcPoNoRef();
        if (StringUtils.isNotBlank(poNo)) {

            boolean exists = qcMgr.validatePoNo(poNo);
            if (!exists) {
                PurchaseOrderDetailsVo po = poMgr.getPurchaseOrderDetailVoItemsByFkPoNo(poNo);
                model.addAttribute("items", po.getPurchaseOrderItems().keySet());
                currentQc.setQcItems(constructItems(poNo, po.getPurchaseOrderItems().keySet()));
                currentQc.setQcNo(poNo);
                currentQc.setNew(true);
            } else {
                currentQc.setNew(false);
                currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(poNo);
            }
        }
        String soNo = StringUtils.isEmpty(currentQc.getQcSoNoRef()) ? request.getParameter("qcSoNoRef") : currentQc.getQcSoNoRef();
        if (StringUtils.isNotBlank(soNo)) {
            boolean exists = qcMgr.validatePoNo(soNo);
            if (!exists) {
                SaleOrderDetailsVo so = saleOrderMgr.getSaleOrderDetailVoByFkSoNo(soNo);
                model.addAttribute("items", so.getSaleOrderItems().keySet());
                currentQc.setQcItems(constructItems(soNo, so.getSaleOrderItems().keySet()));
                currentQc.setQcNo(soNo);
                currentQc.setNew(true);
            } else {
                currentQc.setNew(false);
                currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(soNo);
            }
        }
        String ofNo = StringUtils.isEmpty(currentQc.getQcOfNoRef()) ? request.getParameter("qcOfNoRef") : currentQc.getQcOfNoRef();
        if (StringUtils.isNotBlank(ofNo)) {
            boolean exists = qcMgr.validatePoNo(ofNo);
            if (!exists) {
                OfferSheet of = offerSheetMgr.getOfferSheetByOffShNoPfixNo(ofNo);
                model.addAttribute("items", of.getOfferSheetItems().keySet());
                currentQc.setQcNo(ofNo);
                currentQc.setQcItems(constructItems(ofNo, of.getOfferSheetItems().keySet()));
                currentQc.setNew(true);
            } else {
                currentQc.setNew(false);
                currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(ofNo);
            }
        }


        currentQc.setPoNo(poNo);
        currentQc.setSoNo(soNo);
        currentQc.setOfNo(ofNo);
        model.addAttribute("currentQc", currentQc);
        model.addAttribute("readonly", false);
        return "/management/qc/add";
    }

    private ArrayList<QualityCheckItemVo> constructItems(String qcNo, Collection<String> items) {
        ArrayList<QualityCheckItemVo> result = new ArrayList<QualityCheckItemVo>();
        for (String itNo : items) {
            result.add(new QualityCheckItemVo(qcNo, itNo));
        }
        return result;
    }

    @RequestMapping(value = "/getchecklist/{itemNo}")
    public String getchecklist(@PathVariable("itemNo") String itemNo, Model model, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);

        List<QcCheckList> templates = null;
        if (StringUtils.isNotBlank(itemNo)) {
            ItemMaster item = itemMgr.getItemMasterByItCatNoSuffix(itemNo);
            if (item != null) {
                int fkItemType = 0;
                if (item.getFkItemType() != null) {
                    fkItemType = item.getFkItemType();
                }
                templates = qcCheckListServiceMgr.getQcCheckListByItemType(fkItemType);
                if (templates == null || templates.isEmpty()) {
                    templates = qcCheckListServiceMgr.getAllQcCheckLists();
                }
            }
            model.addAttribute("item", item);
        }

        model.addAttribute("templates", templates);
        model.addAttribute("template", templates.get(0));

        //不存在
        QualityCheckDetailsVo currentQc = (QualityCheckDetailsVo) WebUtils.getSessionAttribute(request, "currentQc");
        if (currentQc == null) {
            currentQc = new QualityCheckDetailsVo();
        }
        //已存在
        if (currentQc.getQcItemCheckListByItemNo(itemNo) != null) {
            String itemDetail = currentQc.getQcItemCheckListByItemNo(itemNo);
            Map<String, Object> map = stringToMap(itemDetail);
            model.addAttribute("bzwlqdVo", map);

        } else {
            if (templates.size() >= 1) {
                QcCheckList qcl = templates.get(0);
                currentQc.getQcItemQctMap().put(itemNo, qcl.getQctNo());
                Map<String, Object> map = stringToMap(qcl.getQctDetail());
                //Add to map for further modification.
                currentQc.getQcItemCheckListMap().put(itemNo, qcl.getQctDetail());
                model.addAttribute("bzwlqdVo", map);
            }
        }


        model.addAttribute("table", this.qcDocFlowMapper.getAllQcDocFlow());
        model.addAttribute("currentQc", currentQc);
        return "/management/qc/qcchecklist";
    }

    @RequestMapping(value = "/deleteqc/{qcNoEncode}")
    @ResponseBody
    public ModelAndView deleteqc(Model model, HttpServletRequest request, @PathVariable("qcNoEncode") String qcNoEncode) {
        String qcNo = Base64.decode(qcNoEncode, "utf-8");
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        this.qcMgr.deleteQc(qcNo);
        return ajaxDoneSuccess("Deleted " + qcNo + " successfully!");
    }

    @RequestMapping(value = "/updateitem/{itemNo}", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateitem(Model model, HttpServletRequest request, @PathVariable("itemNo") String itemNo) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        QualityCheckDetailsVo currentQc = (QualityCheckDetailsVo) WebUtils.getSessionAttribute(request, "currentQc");
        currentQc.getQcItemCheckListMap().put(itemNo, request.getParameter("data"));
        model.addAttribute("currentQc", currentQc);
        return ajaxDoneSuccess("Update " + itemNo + " Success!");
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertQc(Model model, HttpServletRequest request, @ModelAttribute("currentQc") QualityCheckDetailsVo currentQc) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        QualityCheck qc = new QualityCheck(currentQc.getQcNo(), currentUser, currentQc);
        boolean addFlag = qcMgr.addQc(qc, currentQc);
        if (addFlag) {
            currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(currentQc.getQcNo());
        }
        model.addAttribute("currentQc", currentQc);
        return ajaxDoneSuccess("保存成功");
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateQc(Model model, HttpServletRequest request, @ModelAttribute("currentQc") QualityCheckDetailsVo currentQc) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        QualityCheck qc = new QualityCheck(currentQc.getQcNo(), currentUser, currentQc);
        boolean addFlag = qcMgr.updateQc(qc, currentQc);
        if (addFlag) {
            currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(currentQc.getQcNo());
        }
        model.addAttribute("currentQc", currentQc);
        return ajaxDoneSuccess("保存成功");
    }

    @RequestMapping(value = "/addqc/{encodePoNo}", method = RequestMethod.GET)
    public String addQc(@PathVariable(value = "encodePoNo") String encodePoNo, Model model, HttpServletRequest request, @ModelAttribute("currentQc") QualityCheckDetailsVo currentQc) {
        String qcNo = Base64.decode(encodePoNo, "utf-8");
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        QualityCheck qc = new QualityCheck(qcNo, currentUser, currentQc);
        boolean addFlag = qcMgr.addQc(qc, currentQc);
        if (addFlag) {
            currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(qcNo);
        }
        model.addAttribute("currentQc", currentQc);
        //extract doc to DMS

        //
        //------------
        return "/management/qc/qcview";
    }

    @RequestMapping(value = "/viewqc/{qcNoEncode}")
    public String viewQc(@PathVariable(value = "qcNoEncode") String qcNoEncode, Model model, HttpServletRequest request) {
        String qcNo = Base64.decode(qcNoEncode, "utf-8");
        QualityCheckDetailsVo currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(qcNo);
        model.addAttribute("currentQc", currentQc);
        model.addAttribute("readonly", Boolean.TRUE);
        model.addAttribute("users", sysUserMapper.findAllByRole("QC"));
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        model.addAttribute("items", currentQc.getQcItemCheckListMap().keySet());
        return "/management/qc/add";
    }

    @RequestMapping(value = "/editqc/{qcNoEncode}")
    public String editQc(@PathVariable(value = "qcNoEncode") String qcNoEncode, Model model, HttpServletRequest request) {
        String qcNo = Base64.decode(qcNoEncode, "utf-8");
        QualityCheckDetailsVo currentQc = qcMgr.getQualityCheckDetailsVoByFkQcNo(qcNo);
        model.addAttribute("currentQc", currentQc);
        model.addAttribute("readonly", Boolean.FALSE);
        model.addAttribute("users", sysUserMapper.findAllByRole("QC"));
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        model.addAttribute("items", currentQc.getQcItemCheckListMap().keySet());
        return "/management/qc/add";
    }

    @RequestMapping(value = "/viewqcitem/{itCatNoSuffix}", method = RequestMethod.GET)
    public String viewQcItem(@PathVariable(value = "itCatNoSuffix") String itCatNoSuffix, @ModelAttribute("currentQc") QualityCheckDetailsVo currentQc, Model model, HttpServletRequest request) {
        QcItemBase qcItemVo = new QualityCheckItemVo(currentQc.getQcNo(), itCatNoSuffix);
        String qcTemplateName = itemMgr.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
        QualityCheckItem currentQcItem = qcMgr.getQcItemByQcItemVo(qcItemVo);
        JSONObject currentQcItemVo = JSONObject.fromObject(currentQcItem);
        model.addAttribute("qcTemplateName", qcTemplateName);
        model.addAttribute("currentQcItem", currentQcItem);
        model.addAttribute("currentQcItemVo", currentQcItemVo);

        model = qcMgr.getQcTemplateDetailsVo(model, currentQcItem);
        return "/management/qc/editqcitem";
    }

    @RequestMapping(value = "/updateqcitem", method = RequestMethod.POST)
    public ModelAndView updateQcItem(@ModelAttribute("currentQcItem") QualityCheckItem currentQcItem, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentQcItem.setModUsr(currentUser.getUsrId());
        } else {
            currentQcItem.setModUsr("test");
        }
        if (qcMgr.updateQcItem(currentQcItem)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping(value = "/updateqcitemselect", method = RequestMethod.POST)
    public ModelAndView updateQcItemSelect(QcItemBase currentQcItem, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentQcItem.setModUsr(currentUser.getUsrId());
        } else {
            currentQcItem.setModUsr("test");
        }
        if (qcMgr.updateQcItemSelected(currentQcItem)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }


    @RequestMapping(value = "/updateqcitemform", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateqcitemform(@RequestBody QualityCheckItemSingleFormVo vo, HttpServletRequest request) {
        try {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            String qcNo = vo.getQcNo();
            String qc2ItCatNoSuffix = vo.getItCatNoSuffix();
            QcItemBase criteria = new QualityCheckItemVo(qcNo, qc2ItCatNoSuffix);
            QualityCheckItem currentQcItem = this.qcMgr.getQcItemByQcItemVo(criteria);
            String form = vo.getForm();
            String data = vo.getData();
            if (currentQcItem != null) {
                currentQcItem.setModUsr(currentUser.getUsrId());

                Method method = BeanUtils.findMethod(QualityCheckItem.class, "set" + StringUtils.capitalize(form), String.class);
                if (method != null) {
                    method.invoke(currentQcItem, data);
                }
                //				if ("qc2Bzmx".equals(form)) {
                //					currentQcItem.setQc2Bzmx(data);
                //				}
                //				if ("qc2Bz".equals(form)) {
                //					currentQcItem.setQc2Bz(data);
                //				}
                //
                //				if ("qc2Bzwlqd".equals(form)) {
                //					currentQcItem.setQc2Bzwlqd(data);
                //				}
                //				if ("qc2Dhgj".equals(form)) {
                //					currentQcItem.setQc2Dhgj(data);
                //				}

                if (qcMgr.updateQcItem(currentQcItem)) {
                    return ajaxDoneSuccess(getMessage("msg.operation.success"));
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            }
            return ajaxDoneError(getMessage("msg.operation.failure"));

        } catch (Exception e) {
            logger.error("Update single form exception.", e);
            return ajaxDoneError(e.getMessage());
        }
    }

    @RequestMapping(value = "/exportqc/{encodeQcNo}", method = RequestMethod.POST)
    public ModelAndView exportQc(@PathVariable(value = "encodeQcNo") String encodeQcNo, HttpServletRequest request) {
        String qcNo = Base64.decode(encodeQcNo, "utf-8");
        try {
            qcMgr.exportQc(qcNo);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
    }


    @RequestMapping(value = "/finishqc/{encodeQcNo}", method = RequestMethod.POST)
    public ModelAndView finishQc(@PathVariable(value = "encodeQcNo") String encodeQcNo, HttpServletRequest request) {
        String qcNo = Base64.decode(encodeQcNo, "utf-8");
        boolean flag = qcMgr.finishQc(qcNo);
        if (flag) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping(value = "/checkexportqc/{encodeQcNo}", method = RequestMethod.POST)
    public ModelAndView checkExportQc(@PathVariable(value = "encodeQcNo") String encodeQcNo, HttpServletRequest request) {
        boolean flag = qcMgr.checkExportQc(encodeQcNo);
        if (flag) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping("/getexportlist/{encodeQcNo}")
    public String editLc(@PathVariable("encodeQcNo") String encodeQcNo, @ModelAttribute("currentQc") QualityCheckDetailsVo currentQc, Model model, HttpServletRequest request) {
        String qcNo = Base64.decode(encodeQcNo, "utf-8");
        return "/management/qc/qcdownload";
    }

    @RequestMapping("/uploadpartpicindex")
    public String uploadPartPicIndex() {
        return "/management/qc/uploadpartpic";
    }

    @RequestMapping("/uploadpart")
    public void uploadPart(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @ModelAttribute("currentQcItem") QualityCheckItem currentQcItem) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String partpicdir = ftpPropertiesMap.get("partpicdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            String url = currentQcItem.getQc2ItCat() + "/" + currentQcItem.getQc2ItNo() + "/" + currentQcItem.getQc2ItSuffix();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + partpicdir + "/" + url);
            if (checker.isSuccess()) {
                String partPic = itemMgr.getItemPartPic(currentQcItem.getQc2PartPic(), fileName, currentQcItem.getQc2ItCat() + "/" + currentQcItem.getQc2ItNo() + "/" + currentQcItem.getQc2ItSuffix() + "/" + fileName);
                currentQcItem.setQc2PartPic(partPic);
                model.addAttribute("currentQcItem", currentQcItem);
            }
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/refleshpartpic")
    public String refleshPartPic(Model model, HttpServletRequest request, @ModelAttribute("currentQcItem") QualityCheckItem currentQcItem) throws IOException {
        String partPicStr = currentQcItem.getQc2PartPic();
        if (partPicStr == null || !partPicStr.startsWith("{")) {
            partPicStr = "{}";
            currentQcItem.setQc2PartPic(partPicStr);
        }
        Map<String, Object> currentPartPic = new LinkedHashMap<String, Object>();
        ObjectMapper partPicMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            currentPartPic = partPicMapper.readValue(partPicStr, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            model.addAttribute("currentPartPic", currentPartPic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("currentQcItem", currentQcItem);
        return "/management/qc/partpicturestable";
    }

    @RequestMapping("/deletepartpic")
    public String deletePartPic(Model model, HttpServletRequest request, @RequestParam(value = "partPicId") String partPicId, @ModelAttribute("currentQcItem") QualityCheckItem currentQcItem) throws IOException {
        partPicId = Base64.decode(partPicId, "utf-8");
        String partPicStr = currentQcItem.getQc2PartPic();
        if (partPicStr == null || !partPicStr.startsWith("{")) {
            partPicStr = "{}";
            currentQcItem.setQc2PartPic(partPicStr);
        }
        Map<String, Object> currentPartPic = new LinkedHashMap<String, Object>();
        ObjectMapper partPicMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            currentPartPic = partPicMapper.readValue(partPicStr, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            currentPartPic.remove(partPicId);
            partPicStr = Common.jsonMapToString(currentPartPic);
            currentQcItem.setQc2PartPic(partPicStr);
            model.addAttribute("currentPartPic", currentPartPic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("currentQcItem", currentQcItem);
        return "/management/qc/partpicturestable";
    }

    @RequestMapping("/uploadqcpicindex")
    public String uploadQcPicIndex() {
        return "/management/qc/uploadqcpic";
    }

    @RequestMapping("/uploadqc")
    public void uploadQc(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @ModelAttribute("currentQcItem") QualityCheckItem currentQcItem) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String qcpicdir = ftpPropertiesMap.get("qcpicdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            String encodeQcNo = Base64.encode(currentQcItem.getQc2No());

            String url = encodeQcNo + "/" + currentQcItem.getQc2ItCatNoSuffix();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + qcpicdir + "/" + url);
            if (checker.isSuccess()) {
                String qcPic = Common.getQcPic(currentQcItem.getQc2Pic(), fileName, encodeQcNo + "/" + currentQcItem.getQc2ItCatNoSuffix());
                currentQcItem.setQc2Pic(qcPic);
                model.addAttribute("currentQcItem", currentQcItem);
            }
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/initUploadQcPic/{qc2ItCatNoSuffix}")
    public String initUploadQcPic(@PathVariable("qc2ItCatNoSuffix") String qc2ItCatNoSuffix, Model model, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        QualityCheckDetailsVo currentQc = (QualityCheckDetailsVo) WebUtils.getSessionAttribute(request, "currentQc");

        QualityCheckItem currentQcItem = this.qcMgr.getQcItemByUniqueKey(qc2ItCatNoSuffix);
        if (currentQcItem == null) {
            currentQcItem = new QualityCheckItem();
            ItemMaster item = this.itemMapper.getItemMasterByItCatNoSuffix(qc2ItCatNoSuffix);
            currentQcItem.setQc2ItCatNoSuffix(qc2ItCatNoSuffix);
            currentQcItem.setQc2ItCat(item.getItCat());
            currentQcItem.setQc2ItNo(item.getItNo());
            currentQcItem.setQc2ItSuffix(item.getItSuffix());
            currentQcItem.setQc2No(currentQc.getQcNo());
        }

        return refleshQcPic(model, request, currentQcItem);

    }

    @RequestMapping("/refleshqcpic")
    public String refleshQcPic(Model model, HttpServletRequest request, @ModelAttribute("currentQcItem") QualityCheckItem currentQcItem) {
        String qcPicStr = currentQcItem.getQc2Pic();
        if (qcPicStr == null || !qcPicStr.startsWith("{")) {
            qcPicStr = "{}";
            currentQcItem.setQc2Pic(qcPicStr);
        }
        Map<String, Object> currentQcPic = new LinkedHashMap<String, Object>();
        ObjectMapper qcPicMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            currentQcPic = qcPicMapper.readValue(qcPicStr, new TypeReference<LinkedHashMap<String, Object>>() {
            });


            if (currentQcPic == null || currentQcPic.isEmpty()) {
                //LIST OUT required images.
                List<QcImage> images = qcMapper.getQcImages();
                currentQcPic = new LinkedHashMap<String, Object>();
                for (QcImage qi : images) {
                    currentQcPic.put(qi.getDocName(), qi.getPurpose() + "(" + qi.getQty() + ")");
                }
            }
            model.addAttribute("currentQcPic", currentQcPic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("currentQcItem", currentQcItem);
        return "/management/qc/qcpicturestable";
    }

    @RequestMapping("/deleteqcpic")
    public String deleteQcPic(Model model, HttpServletRequest request, @RequestParam(value = "qcPicId") String qcPicId, @ModelAttribute("currentQcItem") QualityCheckItem currentQcItem) throws IOException {
        qcPicId = Base64.decode(qcPicId, "utf-8");
        String qcPicStr = currentQcItem.getQc2Pic();
        if (qcPicStr == null || !qcPicStr.startsWith("{")) {
            qcPicStr = "{}";
            currentQcItem.setQc2Pic(qcPicStr);
        }
        Map<String, Object> currentQcPic = new LinkedHashMap<String, Object>();
        ObjectMapper qcPicMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            currentQcPic = qcPicMapper.readValue(qcPicStr, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            currentQcPic.remove(qcPicId);
            qcPicStr = Common.jsonMapToString(currentQcPic);
            currentQcItem.setQc2Pic(qcPicStr);
            model.addAttribute("currentQcPic", currentQcPic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("currentQcItem", currentQcItem);
        return "/management/qc/qcpicturestable";
    }

    @RequestMapping("/viewqcpic/{qcNo}/{itCatNoSuffix}/{fileName}")
    public String viewQcPic(@PathVariable("qcNo") String qcNo, @PathVariable("itCatNoSuffix") String itCatNoSuffix, @PathVariable("fileName") String fileName, Model model, HttpServletRequest request) {
        String viewUrl = null;
        if (StringUtils.isNotBlank(qcNo) && StringUtils.isNotBlank(itCatNoSuffix) && StringUtils.isNotBlank(fileName)) {
            viewUrl = request.getContextPath() + "/pic/QC-PHOTO-JPEG/" + qcNo + "/" + itCatNoSuffix + "/" + fileName;
        }
        model.addAttribute("viewUrl", viewUrl);
        return "/management/pic/viewpic";
    }


    @RequestMapping("/viewqctpic/{pic}")
    public String viewQctPic(@PathVariable("pic") String fileName, Model model, HttpServletRequest request) {
        //fileName = Base64.decode(fileName, "utf-8");
        String viewUrl = null;
        if (StringUtils.isNotBlank(fileName)) {
            viewUrl = request.getContextPath() + "/pic/PHOTO-JPEG/" + fileName;
        }
        model.addAttribute("viewUrl", viewUrl);
        return "/management/pic/viewpic";
    }
}
