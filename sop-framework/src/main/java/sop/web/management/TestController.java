package sop.web.management;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dwz.web.BaseController;
import sop.utils.ExcelManager;

/**
 * @Author: LCF
 * @Date: 2020/1/9 13:01
 * @Package: sop.web.management
 */

@Controller("management.testController")
@RequestMapping(value = "/management/test")
public class TestController extends BaseController {

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        return "/management/test/test";
    }

    @RequestMapping(value = "/testimportexcel", method = RequestMethod.POST)
    @ResponseBody
    public void testImportExcel(@RequestParam(value = "encodeQcNo") String encodeQcNo, Model model, HttpServletRequest request, HttpServletResponse response) {

        String testStr = "{\"test1\": {\"test2\":\"test3\"}}";
        Map<String, Object> testVo = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Workbook workbook = null;
        try {
            //把detail data json string 转成map对象
            testVo = mapper.readValue(testStr, new TypeReference<HashMap<String, Object>>() {
            });
            workbook = ExcelManager.importToExcel("itemtemplates/test.xls", testVo);
            ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, "CL1000500", "test.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/testconcatexcel", method = RequestMethod.POST)
    @ResponseBody
    public void testConcatExcel(Model model, HttpServletRequest request, HttpServletResponse response) {

        String testStr = "{\"test1\":\"1\"}";
        Map<String, Object> testVo = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Workbook workbook = null;
        try {
            //把detail data json string 转成map对象
            testVo = mapper.readValue(testStr, new TypeReference<HashMap<String, Object>>() {
            });
            String str = request.getContextPath();
            workbook = ExcelManager.importToExcel("itemtemplates/test.xls", testVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (workbook != null) {
            ExcelManager.exportExcel(response, workbook, "test.xls");
        }
    }

}
