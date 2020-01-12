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
import sop.persistence.beans.ImproveSuggestion;
import sop.services.ItemServiceMgr;
import sop.util.DateUtils;
import sop.util.Sys;
import sop.utils.Common;
import sop.services.ImproveSuggestionServiceMgr;


/**
 * @Author: LCF
 * @Date: 2020/1/9 12:49
 * @Package: sop.web.management
 */


@Controller("management.improvesuggestionController")
@RequestMapping(value = "/management/improvesuggestion")
public class ImproveSuggestionController extends BaseController {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ImproveSuggestionServiceMgr improveSuggestionMgr;


    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<ImproveSuggestion> list = improveSuggestionMgr.getImproveSuggestionListByCondition(vo);
        improveSuggestionMgr.getImproveSuggestionListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("list", list);
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        return "/management/improvesuggestion/list";
    }

    @RequestMapping("/uploadpicindex/{id}/{impNo}")
    public String uploadpicindex(@PathVariable("id") String id, @PathVariable("impNo") String impNo, Model model, HttpServletRequest request) {
        model.addAttribute("id", id);
        model.addAttribute("impNo", impNo);
        return "/management/improvesuggestion/uploadpic";
    }

    @RequestMapping("/upload")
    public void upload(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @RequestParam(value = "impNo") String impNo) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String picdir = ftpPropertiesMap.get("picdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + picdir + "/" + impNo);
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        ImproveSuggestion pb = new ImproveSuggestion();
        pb.setImpNo("IMP" + DateUtils.formatDateTime("yyMMddHHmmSS", Sys.getDate()));
        model.addAttribute("improveSuggestion", pb);
        return "/management/improvesuggestion/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(ImproveSuggestion improveSuggestion, HttpServletRequest request) {
        Checker checker = improveSuggestionMgr.checkImproveSuggestion(improveSuggestion);
        if (checker.isSuccess()) {
            User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (user != null) {
                improveSuggestion.setCrtUsr(user.getUsrId());
                improveSuggestion.setModUsr(user.getUsrId());
            } else {
                improveSuggestion.setCrtUsr("test");
                improveSuggestion.setModUsr("test");
            }
            try {
                improveSuggestionMgr.addImproveSuggestion(improveSuggestion);
            } catch (ServiceException e) {
                //e.printStackTrace();
                //return ajaxDoneError("Code 重复");
                return this.update(improveSuggestion, request);
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{impNo}")
    public String edit(@PathVariable("impNo") String impNo, Model model) {
        //impNo = Base64.decode(impNo, "utf-8");
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        ImproveSuggestion improveSuggestion = improveSuggestionMgr.getImproveSuggestionByNo(impNo);
        model.addAttribute("improveSuggestion", improveSuggestion);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ObjectMapper picMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            map = picMapper.readValue(improveSuggestion.getImpDetail(), new TypeReference<LinkedHashMap<String, Object>>() {
            });
            model.addAttribute("bzwlqdVo", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/management/improvesuggestion/add";
    }

    @RequestMapping("/delete/{impNo}")
    public ModelAndView delete(@PathVariable("impNo") String impNo, Model model) throws ServiceException {
        //impNo = Base64.decode(impNo, "utf-8");
        improveSuggestionMgr.deleteImproveSuggestion(impNo);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/update")
    public ModelAndView update(ImproveSuggestion improveSuggestion, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (user != null) {
            improveSuggestion.setModUsr(user.getUsrId());
        } else {
            improveSuggestion.setModUsr("test");
        }
        try {
            improveSuggestionMgr.updateImproveSuggestion(improveSuggestion);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

}
