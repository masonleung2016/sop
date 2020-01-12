package sop.web.management;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.ItemMapper;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.ItemType;
import sop.persistence.beans.ItemDoc;
import sop.services.ItemServiceMgr;
import sop.util.DateUtils;
import sop.util.Sys;
import sop.utils.Common;
import sop.services.ItemDocServiceMgr;


/**
 * @Author: LCF
 * @Date: 2020/1/9 12:51
 * @Package: sop.web.management
 */


@Controller("management.itemdocController")
@RequestMapping(value = "/management/itemdoc")
public class ItemDocController extends BaseController {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDocServiceMgr itemDocMgr;


    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<ItemDoc> list = itemDocMgr.getItemDocListByCondition(vo);
        itemDocMgr.getItemDocListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("list", list);
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        return "/management/itemdoc/list";
    }

    @RequestMapping("/uploadpicindex/{id}/{docNo}")
    public String uploadpicindex(@PathVariable("id") String id, @PathVariable("docNo") String docNo, Model model, HttpServletRequest request) {
        model.addAttribute("id", id);
        model.addAttribute("docNo", docNo);
        return "/management/itemdoc/uploadpic";
    }

    @RequestMapping("/upload")
    public void upload(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @RequestParam(value = "docNo") String docNo) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String picdir = ftpPropertiesMap.get("picdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + picdir + "/" + docNo);
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        ItemDoc pb = new ItemDoc();
        pb.setDocNo("DN" + DateUtils.formatDateTime("yyMMddHHmmSS", Sys.getDate()));
        model.addAttribute("itemDoc", pb);
        return "/management/itemdoc/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(ItemDoc itemDoc, HttpServletRequest request) {
        Checker checker = itemDocMgr.checkItemDoc(itemDoc);
        if (checker.isSuccess()) {
            User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (user != null) {
                itemDoc.setCrtUsr(user.getUsrId());
                itemDoc.setModUsr(user.getUsrId());
            } else {
                itemDoc.setCrtUsr("test");
                itemDoc.setModUsr("test");
            }
            try {
                itemDocMgr.addItemDoc(itemDoc);
            } catch (ServiceException e) {
                //e.printStackTrace();
                //return ajaxDoneError("Code 重复");
                return this.update(itemDoc, request);
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{docNo}")
    public String edit(@PathVariable("docNo") String docNo, Model model) {
        //docNo = Base64.decode(docNo, "utf-8");
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        ItemDoc itemDoc = itemDocMgr.getItemDocByNo(docNo);
        model.addAttribute("itemDoc", itemDoc);
        return "/management/itemdoc/add";
    }

    @RequestMapping("/delete/{docNo}")
    public ModelAndView delete(@PathVariable("docNo") String docNo, Model model) throws ServiceException {
        //docNo = Base64.decode(docNo, "utf-8");
        itemDocMgr.deleteItemDoc(docNo);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/update")
    public ModelAndView update(ItemDoc itemDoc, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (user != null) {
            itemDoc.setModUsr(user.getUsrId());
        } else {
            itemDoc.setModUsr("test");
        }
        try {
            itemDocMgr.updateItemDoc(itemDoc);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

}
