package sop.web.management;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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

import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.ItemType;
import sop.persistence.beans.Template;
import sop.services.ItemServiceMgr;
import sop.utils.Common;
import sop.vo.ItemMasterComboVo;
import sop.vo.ItemMasterVo;
import sop.vo.SearchItemConditionVo;
import sop.vo.SearchTemplateConditionVo;


/**
 * @Author: LCF
 * @Date: 2020/1/9 12:50
 * @Package: sop.web.management
 */


@Controller("management.itemController")
@RequestMapping(value = "/management/item")
@SessionAttributes({"currentItem"})
public class ItemController extends BaseController {
    @Autowired
    private ItemServiceMgr itemMgr;

    @RequestMapping("/selecttemplateindex")
    public String selectTemplateIndex(SearchTemplateConditionVo searchTemplateConditionVo, Model model, HttpServletRequest request) {
        //		List<ItemType> itemMasterTypes = itemmasterMgr.getAllItemMasterTypes();
        //
        //		model.addAttribute("itemMasterTypes", itemMasterTypes);
        //		model.addAttribute("searchTemplateConditionVo", searchTemplateConditionVo);
        model.addAttribute("detailFor", request.getParameter("detailFor"));
        model.addAttribute("func", request.getParameter("func"));

        return "/management/item/template";
    }

    @RequestMapping("/selecttemplateindex2")
    public String selectTemplateIndex2(SearchTemplateConditionVo searchTemplateConditionVo, Model model, @ModelAttribute("currentItem") ItemMaster currentItem, HttpServletRequest request) {
        //		List<ItemType> itemMasters = itemmasterMgr.getAllItemMasterTypes();
        //		List<Template> templates = null;
        //		boolean selected = false;
        //		if(searchTemplateConditionVo.getFkItemMaster() != null){
        //			templates = itemmasterMgr.getTemplatesByFkItemMaster(searchTemplateConditionVo.getFkItemMaster());
        //		}
        //		if(searchTemplateConditionVo.getFkTemplate() != null){
        //			selected = true;
        //		}
        //		model.addAttribute("itemMasters", itemMasters);
        //		model.addAttribute("templates", templates);
        //		model.addAttribute("selected", selected);
        //		model.addAttribute("searchTemplateConditionVo", searchTemplateConditionVo);
        //
        String partPicStr = currentItem.getPartPic();
        if (partPicStr == null || !partPicStr.startsWith("{")) {
            partPicStr = "{}";
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

        model.addAttribute("load", false);
        model.addAttribute("detailFor", request.getParameter("detailFor"));
        model.addAttribute("func", request.getParameter("func"));
        model.addAttribute("load", false);
        model.addAttribute("searchTemplateConditionVo", searchTemplateConditionVo);
        return "/management/item/template";
    }


    @RequestMapping("/selectengtemplate")
    public String selectEngTemplate(SearchTemplateConditionVo searchTemplateConditionVo, Model model, @ModelAttribute("currentItem") ItemMaster currentItem, HttpServletRequest request) {
        String partPicStr = currentItem.getPartPic();
        if (partPicStr == null || !partPicStr.startsWith("{")) {
            partPicStr = "{}";
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

        model.addAttribute("detailFor", request.getParameter("detailFor"));
        model.addAttribute("func", request.getParameter("func"));
        model.addAttribute("searchTemplateConditionVo", searchTemplateConditionVo);
        return "/management/item/engtemplate";
    }

    @RequestMapping(value = "/gettemplates", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getTemplates(@RequestParam(value = "type") String type, Model model) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        //		List<Template> templates = itemmasterMgr.getTemplatesByItemMasterType(type);
        //		result.put("templates", templates);
        return result;
    }

    @RequestMapping(value = "/gettemplatesbyfkitemmaster", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getTemplatesByFkItemMaster(@RequestParam(value = "fkItemMaster") Integer fkItemMaster, Model model) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        List<Template> templates = itemMgr.getTemplatesByFkItemType(fkItemMaster);
        result.put("templates", templates);
        return result;
    }

    @RequestMapping("/selecttemplatetest/{type}/{template}")
    public String selectTemplateChair1(@PathVariable("type") String type, @PathVariable("template") String template, Model model) {
        return "/management/itemmaster/" + type + "/" + template;
    }

    @RequestMapping("/selecttemplate")
    public String selectTemplate(SearchTemplateConditionVo searchTemplateConditionVo, Model model) {
        model.addAttribute("searchTemplateConditionVo", searchTemplateConditionVo);
        return "/management/itemmaster/" + searchTemplateConditionVo.getItemMasterType() + "/" + searchTemplateConditionVo.getTemplateName();
    }

    @RequestMapping(value = "saveitem", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveItem(@RequestBody Map<String, Object> json, Model model) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        //		boolean success = itemmasterMgr.saveItem(json);
        //		result.put("success", success);
        return result;
    }

    @RequestMapping("/itemindex")
    public String index(SearchItemConditionVo vo, Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("itemMasterCombos") == null) {
            ItemMasterComboVo itemMasterCombos = itemMgr.getItemMasterCombos();
            request.getSession().setAttribute("itemMasterCombos", itemMasterCombos);
        }
        //判断是否是初次登录
        if (vo.isIndexFlag()) {
            vo.setIndexFlag(false);
        } else {
            List<ItemMasterVo> itemVoList = itemMgr.getItemListByCondition(vo);
            itemMgr.getItemListNum(vo);
            model.addAttribute("totalCount", vo.getTotalCount());
            model.addAttribute("pageSize", vo.getPageSize());
            model.addAttribute("itemVoList", itemVoList);
        }

        model.addAttribute("vo", vo);
        return "/management/item/itemindex";
    }

    @RequestMapping("/selectitem")
    public String selectItem(@RequestParam(value = "fkItem") Integer fkItem, Model model, HttpServletRequest request) {
        List<Template> templates = null;
        ItemMaster currentItem = itemMgr.getItemMasterById(fkItem);
        if (currentItem != null && currentItem.getFkItemType() != null) {
            templates = itemMgr.getTemplatesByFkItemType(currentItem.getFkItemType());
            String detailVoStr = currentItem.getDetailData();
            if (detailVoStr == null || !detailVoStr.startsWith("{")) {
                detailVoStr = "{}";
            }
            Map<String, Object> detailVo = new LinkedHashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                //把detail data json string 转成map对象
                detailVo = mapper.readValue(detailVoStr, new TypeReference<LinkedHashMap<String, Object>>() {
                });
                model.addAttribute("detailVo", detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("currentItem", currentItem);
        model.addAttribute("templates", templates);
        model.addAttribute("load", true);
        return "/management/item/item";
    }

    @RequestMapping("/list")
    public String list(SearchItemConditionVo vo, Model model, HttpServletRequest request) {
        //判断是否是初次登录
        vo.setIndexFlag(false);

        //if(vo.isIndexFlag()){
        //	vo.setIndexFlag(false);
        //}else{
        List<ItemMasterVo> itemVoList = itemMgr.getItemListByCondition(vo);
        itemMgr.getItemListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("itemVoList", itemVoList);
        //}
        List<ItemType> itemTypes = itemMgr.getItemTypes();
        model.addAttribute("itemTypes", itemTypes);

        List<Template> templates = itemMgr.getAllTemplates();
        model.addAttribute("templates", templates);


        model.addAttribute("vo", vo);
        return "/management/item/list";
    }

    @RequestMapping("/editbyitemno/{itemno}")
    public String editbyitemno(@PathVariable("itemno") String itemno, Model model, HttpServletRequest request) {
        ItemMaster currentItem = itemMgr.getItemMasterByItCatNoSuffix(itemno);
        if (currentItem != null) {
            handleItem(currentItem, model);
        }
        model.addAttribute("load", true);
        return "/management/item/edit";
    }

    @RequestMapping("/edit/{fkItem}")
    public String edit(@PathVariable("fkItem") Integer fkItem, Model model, HttpServletRequest request) {
        ItemMaster currentItem = itemMgr.getItemMasterById(fkItem);
        if (currentItem != null) {
            handleItem(currentItem, model);
        }
        model.addAttribute("load", true);
        return "/management/item/edit";
    }

    private void handleItem(ItemMaster currentItem, Model model) {
        ItemMasterComboVo itemMasterCombos = itemMgr.getItemMasterCombos();
        model.addAttribute("itemMasterCombos", itemMasterCombos);
        List<Template> templates = null;
        if (currentItem.getFkItemType() != null) {
            templates = itemMgr.getTemplatesByFkItemType(currentItem.getFkItemType());
        }

        //BOM
        String detailVoStr = currentItem.getDetailData();
        if (detailVoStr == null || !detailVoStr.startsWith("{")) {
            detailVoStr = "{}";
            currentItem.setDetailData(detailVoStr);
        }


        Map<String, Object> detailVo = new LinkedHashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            detailVo = mapper.readValue(detailVoStr, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            model.addAttribute("detailVo", detailVo);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //PART PIC
        String partPicStr = currentItem.getPartPic();
        if (partPicStr == null || !partPicStr.startsWith("{")) {
            partPicStr = "{}";
            currentItem.setPartPic(partPicStr);
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


        //shipping mark
        String shippingMarkStr = currentItem.getShippingMark();
        if (shippingMarkStr == null || !shippingMarkStr.startsWith("{")) {
            shippingMarkStr = "{}";
            currentItem.setShippingMark(shippingMarkStr);
        }
        Map<String, Object> shippingMark = new LinkedHashMap<String, Object>();
        ObjectMapper shippingMarkMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            shippingMark = shippingMarkMapper.readValue(shippingMarkStr, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            model.addAttribute("shippingmark", shippingMark);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("templates", templates);
        model.addAttribute("currentItem", currentItem);

    }

    @RequestMapping("/copy/{fkItem}")
    public String copy(@PathVariable("fkItem") Integer fkItem, Model model, HttpServletRequest request) {
        ItemMasterComboVo itemMasterCombos = itemMgr.getItemMasterCombos();
        model.addAttribute("itemMasterCombos", itemMasterCombos);
        List<Template> templates = null;
        ItemMaster currentItem = itemMgr.getItemMasterById(fkItem);
        if (currentItem != null) {
            itemMgr.clearItemIdAndPic(currentItem);
            if (currentItem.getFkItemType() != null) {
                templates = itemMgr.getTemplatesByFkItemType(currentItem.getFkItemType());
            }
            String detailVoStr = currentItem.getDetailData();
            if (detailVoStr == null || !detailVoStr.startsWith("{")) {
                detailVoStr = "{}";
                currentItem.setDetailData(detailVoStr);
            }
            Map<String, Object> detailVo = new LinkedHashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                //把detail data json string 转成map对象
                detailVo = mapper.readValue(detailVoStr, new TypeReference<LinkedHashMap<String, Object>>() {
                });
                model.addAttribute("detailVo", detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("currentItem", currentItem);
        model.addAttribute("templates", templates);
        model.addAttribute("load", true);
        return "/management/item/add";
    }

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request) {
        ItemMasterComboVo itemMasterCombos = itemMgr.getItemMasterCombos();
        Map<String, Object> currentPartPic = new LinkedHashMap<String, Object>();
        model.addAttribute("itemMasterCombos", itemMasterCombos);
        model.addAttribute("currentItem", new ItemMaster());
        model.addAttribute("currentPartPic", currentPartPic);
        return "/management/item/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute("currentItem") ItemMaster currentItem, HttpServletRequest request) {
        Checker checker = itemMgr.checkItem(currentItem);
        if (checker.isSuccess()) {
            Checker checkDecimal = itemMgr.checkDecimal(currentItem);
            if (checkDecimal.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentItem.setCrtUsr(currentUser.getUsrId());
                    currentItem.setModUsr(currentUser.getUsrId());
                    currentItem.setCrtDate(new Date());
                }
                Integer fkitem = itemMgr.addItem(currentItem);
                if (fkitem != null && fkitem > 0) {
                    return ajaxDoneSuccess(fkitem.toString());
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            } else {
                return ajaxDoneError(checkDecimal.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentItem") ItemMaster currentItem, HttpServletRequest request) {
        Checker checker = itemMgr.checkItem(currentItem);
        if (checker.isSuccess()) {
            Checker checkDecimal = itemMgr.checkDecimal(currentItem);
            if (checkDecimal.isSuccess()) {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentItem.setModUsr(currentUser.getUsrId());
                    currentItem.setModDate(new Date());
                }

                if (itemMgr.editItem(currentItem)) {
                    return ajaxDoneSuccess(getMessage("msg.operation.success"));
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            } else {
                return ajaxDoneError(checkDecimal.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/delete/{fkItem}")
    public ModelAndView delete(@PathVariable("fkItem") Integer fkItem, Model model, HttpServletRequest request) {
        try {
            if (itemMgr.deleteItem(fkItem)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            return ajaxDoneError(e.toString());
        }
    }

    @RequestMapping("/upload")
    public void upload(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @RequestParam(value = "fkType") String fkType) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String picdir = ftpPropertiesMap.get("picdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + picdir + "/" + fkType);
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/uploadpicindex/{fkNumber}/{fkType}")
    public String uploadpicindex(@PathVariable("fkNumber") String fkNumber, @PathVariable("fkType") String fkType, Model model, HttpServletRequest request) {
        model.addAttribute("fkNumber", fkNumber);
        model.addAttribute("fkType", fkType);
        return "/management/item/uploadpic";
    }

    /*Shipping Marks*/
    @RequestMapping("/uploadsmpicindex/{fkNumber}/{fkType}")
    public String uploadsmpicindex(@PathVariable("fkNumber") String fkNumber, @PathVariable("fkType") String fkType, Model model, HttpServletRequest request) {
        model.addAttribute("fkNumber", fkNumber);
        model.addAttribute("fkType", fkType);
        return "/management/item/uploadsmpic";
    }


    @RequestMapping("/uploadpartpicindex/{itCat}/{itNo}/{itSuffix}")
    public String uploadPartPicIndex(Model model, HttpServletRequest request, @PathVariable("itCat") String itCat, @PathVariable("itNo") String itNo, @PathVariable("itSuffix") String itSuffix, @ModelAttribute("currentItem") ItemMaster currentItem) {
        if (currentItem.getItCat() == null || currentItem.getItCat().equals("")) {
            currentItem.setItCat(itCat);
        }
        if (currentItem.getItNo() == null || currentItem.getItNo().equals("")) {
            currentItem.setItNo(itNo);
        }
        if (currentItem.getItSuffix() == null || currentItem.getItSuffix().equals("")) {
            currentItem.setItSuffix(itSuffix);
        }
        model.addAttribute("currentItem", currentItem);
        return "/management/item/uploadpartpic";
    }


    @RequestMapping("/uploadpart")
    public void uploadPart(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @ModelAttribute("currentItem") ItemMaster currentItem) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String partpicdir = ftpPropertiesMap.get("partpicdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            String url = currentItem.getItCat() + "/" + currentItem.getItNo() + "/" + currentItem.getItSuffix();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + partpicdir + "/" + url);
            if (checker.isSuccess()) {
                String partPic = itemMgr.getItemPartPic(currentItem.getPartPic(), fileName, currentItem.getItCat() + "/" + currentItem.getItNo() + "/" + currentItem.getItSuffix() + "/" + fileName);
                currentItem.setPartPic(partPic);
                model.addAttribute("currentItem", currentItem);
            }
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/refleshpartpic")
    public String refleshPartPic(Model model, HttpServletRequest request, @ModelAttribute("currentItem") ItemMaster currentItem) throws IOException {
        String partPicStr = currentItem.getPartPic();
        if (partPicStr == null || !partPicStr.startsWith("{")) {
            partPicStr = "{}";
            currentItem.setPartPic(partPicStr);
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
        model.addAttribute("currentItem", currentItem);
        return "/management/item/partpicturestable";
    }

    @RequestMapping("/deletepartpic")
    public String deletePartPic(Model model, HttpServletRequest request, @RequestParam(value = "partPicId") String partPicId, @ModelAttribute("currentItem") ItemMaster currentItem) throws IOException {
        partPicId = Base64.decode(partPicId, "utf-8");
        String partPicStr = currentItem.getPartPic();
        if (partPicStr == null || !partPicStr.startsWith("{")) {
            partPicStr = "{}";
            currentItem.setPartPic(partPicStr);
        }
        Map<String, Object> currentPartPic = new LinkedHashMap<String, Object>();
        ObjectMapper partPicMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            currentPartPic = partPicMapper.readValue(partPicStr, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            currentPartPic.remove(partPicId);
            partPicStr = Common.jsonMapToString(currentPartPic);
            currentItem.setPartPic(partPicStr);
            model.addAttribute("currentPartPic", currentPartPic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("currentItem", currentItem);
        return "/management/item/partpicturestable";
    }

    @RequestMapping("/viewpartpic/{url}")
    public String viewPartPic(@PathVariable("url") String url, Model model, HttpServletRequest request) {
        String viewUrl = null;
        if (url != null && !url.trim().equals("")) {
            viewUrl = request.getContextPath() + "/pic/PART-PHOTO-JPEG/" + url;
        }
        model.addAttribute("viewUrl", viewUrl);
        return "/management/pic/viewpic";
    }
}
