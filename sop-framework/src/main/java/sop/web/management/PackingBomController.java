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
import sop.persistence.beans.PackingBom;
import sop.services.ItemServiceMgr;
import sop.services.PackingBomServiceMgr;
import sop.util.DateUtils;
import sop.util.Sys;
import sop.utils.Common;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:53
 * @Package: sop.web.management
 */


@Controller("management.packingbomController")
@RequestMapping(value = "/management/packingbom")
public class PackingBomController extends BaseController {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private PackingBomServiceMgr packingBomMgr;


    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<PackingBom> list = packingBomMgr.getPackingBomListByCondition(vo);
        packingBomMgr.getPackingBomListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("list", list);
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        return "/management/packingbom/list";
    }

    @RequestMapping("/uploadpicindex/{id}/{pbNo}")
    public String uploadpicindex(@PathVariable("id") String id, @PathVariable("pbNo") String pbNo, Model model, HttpServletRequest request) {
        model.addAttribute("id", id);
        model.addAttribute("pbNo", pbNo);
        return "/management/packingbom/uploadpic";
    }

    @RequestMapping("/upload")
    public void upload(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @RequestParam(value = "pbNo") String pbNo) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String picdir = ftpPropertiesMap.get("picdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + picdir + "/" + pbNo);
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        PackingBom pb = new PackingBom();
        pb.setPbNo("PBN" + DateUtils.formatDateTime("yyMMddHHmmSS", Sys.getDate()));
        model.addAttribute("packingBom", pb);
        return "/management/packingbom/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(PackingBom packingBom, HttpServletRequest request) {
        Checker checker = packingBomMgr.checkPackingBom(packingBom);
        if (checker.isSuccess()) {
            User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (user != null) {
                packingBom.setCrtUsr(user.getUsrId());
                packingBom.setModUsr(user.getUsrId());
            } else {
                packingBom.setCrtUsr("test");
                packingBom.setModUsr("test");
            }
            try {
                packingBomMgr.addPackingBom(packingBom);
            } catch (ServiceException e) {
                //e.printStackTrace();
                //return ajaxDoneError("Code 重复");
                return this.update(packingBom, request);
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{pbNo}")
    public String edit(@PathVariable("pbNo") String pbNo, Model model) {
        //pbNo = Base64.decode(pbNo, "utf-8");
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        PackingBom packingBom = packingBomMgr.getPackingBomByNo(pbNo);
        model.addAttribute("packingBom", packingBom);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ObjectMapper picMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            map = picMapper.readValue(packingBom.getPbDetail(), new TypeReference<LinkedHashMap<String, Object>>() {
            });
            model.addAttribute("bzwlqdVo", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/management/packingbom/add";
    }

    @RequestMapping("/delete/{pbNo}")
    public ModelAndView delete(@PathVariable("pbNo") String pbNo, Model model) throws ServiceException {
        //pbNo = Base64.decode(pbNo, "utf-8");
        packingBomMgr.deletePackingBom(pbNo);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/update")
    public ModelAndView update(PackingBom packingBom, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (user != null) {
            packingBom.setModUsr(user.getUsrId());
        } else {
            packingBom.setModUsr("test");
        }
        try {
            packingBomMgr.updatePackingBom(packingBom);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

}
