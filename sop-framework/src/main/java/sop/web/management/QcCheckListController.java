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

import com.itextpdf.text.log.LoggerFactory;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.QcDocFlowMapper;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.QcDocFlow;
import sop.persistence.beans.ItemType;
import sop.persistence.beans.QcCheckList;
import sop.services.ItemServiceMgr;
import sop.services.QcCheckListServiceMgr;
import sop.util.DateUtils;
import sop.util.Sys;
import sop.utils.Common;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:57
 * @Package: sop.web.management
 */

@Controller("management.qcchecklistController")
@RequestMapping(value = "/management/qcchecklist")
public class QcCheckListController extends BaseController {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private QcDocFlowMapper qcDocFlowMapper;

    @Autowired
    private QcCheckListServiceMgr qcCheckListMgr;


    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<QcCheckList> list = qcCheckListMgr.getQcCheckListListByCondition(vo);
        qcCheckListMgr.getQcCheckListListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("list", list);
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        return "/management/qcchecklist/list";
    }

    @RequestMapping("/uploadpicindex/{id}/{qctNo}")
    public String uploadpicindex(@PathVariable("id") String id, @PathVariable("qctNo") String qctNo, Model model, HttpServletRequest request) {
        model.addAttribute("id", id);
        model.addAttribute("qctNo", qctNo);
        return "/management/qcchecklist/uploadpic";
    }

    @RequestMapping("/upload")
    public void u(Model model, HttpServletRequest request, HttpServletResponse respone, @RequestParam(value = "myfile") MultipartFile multipartFile, @RequestParam(value = "qctNo") String qctNo) throws IOException {
        PrintWriter out = respone.getWriter();
        if (multipartFile.getSize() > 0) {
            Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
            String baselocation = ftpPropertiesMap.get("baselocation");
            String picdir = ftpPropertiesMap.get("picdir");

            InputStream fis = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();

            Checker checker = Common.down(fileName, fis, baselocation + "/" + picdir + "/" + qctNo);
            out.println("<script>parent.callback(" + checker.isSuccess() + ", '" + checker.getReturnStr() + "')</script>");
        } else {
            out.println("<script>parent.callback(false, '请选择图片')</script>");
        }
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        QcCheckList pb = new QcCheckList();
        pb.setQctNo("QCT" + DateUtils.formatDateTime("yyMMddHHmmSS", Sys.getDate()));
        model.addAttribute("qcchecklist", pb);
        model.addAttribute("table", qcDocFlowMapper.getAllQcDocFlow());

        return "/management/qcchecklist/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(QcCheckList qcCheckList, HttpServletRequest request) {
        Checker checker = qcCheckListMgr.checkQcCheckList(qcCheckList);
        if (checker.isSuccess()) {
            User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (user != null) {
                qcCheckList.setCrtUsr(user.getUsrId());
                qcCheckList.setModUsr(user.getUsrId());
            } else {
                qcCheckList.setCrtUsr("test");
                qcCheckList.setModUsr("test");
            }
            try {
                qcCheckListMgr.addQcCheckList(qcCheckList);
            } catch (ServiceException e) {
                //e.printStackTrace();
                //return ajaxDoneError("Code 重复");
                return this.update(qcCheckList, request);
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{qctNo}")
    public String edit(@PathVariable("qctNo") String qctNo, Model model) {
        //qctNo = Base64.decode(qctNo, "utf-8");
        model.addAttribute("itemTypes", itemMapper.getAllItemTypes());
        QcCheckList qcCheckList = qcCheckListMgr.getQcCheckListByNo(qctNo);
        model.addAttribute("qcchecklist", qcCheckList);


        model.addAttribute("table", this.qcDocFlowMapper.getAllQcDocFlow());

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ObjectMapper picMapper = new ObjectMapper();
        try {
            //把detail data json string 转成map对象
            map = picMapper.readValue(qcCheckList.getQctDetail(), new TypeReference<LinkedHashMap<String, Object>>() {
            });
            model.addAttribute("bzwlqdVo", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/management/qcchecklist/add";
    }

    @RequestMapping("/delete/{qctNo}")
    public ModelAndView delete(@PathVariable("qctNo") String qctNo, Model model) throws ServiceException {
        //qctNo = Base64.decode(qctNo, "utf-8");
        qcCheckListMgr.deleteQcCheckList(qctNo);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/update")
    public ModelAndView update(QcCheckList qcCheckList, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (user != null) {
            qcCheckList.setModUsr(user.getUsrId());
        } else {
            qcCheckList.setModUsr("test");
        }
        try {
            qcCheckListMgr.updateQcCheckList(qcCheckList);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

}
