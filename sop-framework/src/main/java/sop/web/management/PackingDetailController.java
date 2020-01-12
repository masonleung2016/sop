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
import sop.persistence.beans.PackingDetail;
import sop.services.ItemServiceMgr;
import sop.services.PackingDetailServiceMgr;
import sop.util.DateUtils;
import sop.util.Sys;
import sop.utils.Common;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:54
 * @Package: sop.web.management
 */


@Controller("management.packingdetailController")
@RequestMapping(value = "/management/packingdetail")
public class PackingDetailController extends BaseController {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private PackingDetailServiceMgr packingDetailMgr;


    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<PackingDetail> list = packingDetailMgr.getPackingDetailListByCondition(vo);
        packingDetailMgr.getPackingDetailListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("list", list);
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        return "/management/packingdetail/list";
    }

    @RequestMapping("/uploadpicindex/{id}/{pdNo}")
    public String uploadpicindex(@PathVariable("id") String id, @PathVariable("pdNo") String pdNo, Model model, HttpServletRequest request) {
        model.addAttribute("id", id);
        model.addAttribute("pdNo", pdNo);
        return "/management/packingdetail/uploadpic";
    }

    @RequestMapping("/upload")
    public void upload(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @RequestParam(value = "pdNo") String pdNo) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String picdir = ftpPropertiesMap.get("picdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + picdir + "/" + pdNo);
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        PackingDetail pb = new PackingDetail();
        pb.setPdNo("PDN" + DateUtils.formatDateTime("yyMMddHHmmSS", Sys.getDate()));
        model.addAttribute("packingDetail", pb);
        return "/management/packingdetail/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(PackingDetail packingDetail, HttpServletRequest request) {
        Checker checker = packingDetailMgr.checkPackingDetail(packingDetail);
        if (checker.isSuccess()) {
            User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (user != null) {
                packingDetail.setCrtUsr(user.getUsrId());
                packingDetail.setModUsr(user.getUsrId());
            } else {
                packingDetail.setCrtUsr("test");
                packingDetail.setModUsr("test");
            }
            try {
                packingDetailMgr.addPackingDetail(packingDetail);
            } catch (ServiceException e) {
                //e.printStackTrace();
                //return ajaxDoneError("Code 重复");
                return this.update(packingDetail, request);
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{pdNo}")
    public String edit(@PathVariable("pdNo") String pdNo, Model model) {
        //pdNo = Base64.decode(pdNo, "utf-8");
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        PackingDetail packingDetail = packingDetailMgr.getPackingDetailByNo(pdNo);
        model.addAttribute("packingDetail", packingDetail);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ObjectMapper picMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            map = picMapper.readValue(packingDetail.getPdDetail(), new TypeReference<LinkedHashMap<String, Object>>() {
            });
            model.addAttribute("bzwlqdVo", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/management/packingdetail/add";
    }

    @RequestMapping("/delete/{pdNo}")
    public ModelAndView delete(@PathVariable("pdNo") String pdNo, Model model) throws ServiceException {
        //pdNo = Base64.decode(pdNo, "utf-8");
        packingDetailMgr.deletePackingDetail(pdNo);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/update")
    public ModelAndView update(PackingDetail packingDetail, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (user != null) {
            packingDetail.setModUsr(user.getUsrId());
        } else {
            packingDetail.setModUsr("test");
        }
        try {
            packingDetailMgr.updatePackingDetail(packingDetail);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

}
