package sop.web.management;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dwz.web.BaseController;
import net.sf.jxls.transformer.XLSTransformer;
import sop.persistence.beans.Checklist;
//import sop.persistence.beans.Employee;
import sop.services.ChecklistServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:43
 * @Package: sop.web.management
 */

@Controller("management.checklistController")
@RequestMapping(value = "/management/checklist")
public class ChecklistController extends BaseController {
    
    @Autowired
    private ChecklistServiceMgr checklistServiceMgr;

    @RequestMapping("/desktop")
    public String list() {
        
        return "/management/itemmaster/desktop/desk1";
        
    }

    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        
//		上面的路径：D:\RRR\project\sop-web\src\main\webapp\resources\itemmaster\templateFileName.xlsx (系统找不到指定的路径。)
        
        String templateFileName = request.getSession().getServletContext()
                .getRealPath("/")
                + "/templateFileName.xlsx";
//		String templateFileName = ChecklistController.class.getClassLoader().getResource("/itemmaster/templateFileName.xlsx").toString();
        String destFileName = "destFileName.xlsx";
//		Employee test1 = new Employee(1, "test1");
//		// 模拟数据
//		List<Employee> staff = new ArrayList<Employee>();
//		staff.add(new Employee(1, "test1"));
//		staff.add(new Employee(2, "test2"));
//		staff.add(new Employee(3, "test3"));
        Map<String, Object> beans = new HashMap<String, Object>();
//		beans.put("employees", staff);
//		beans.put("test1", test1);
        XLSTransformer transformer = new XLSTransformer();
        InputStream in = null;
        OutputStream out = null;
        // 设置响应
        response.setHeader("Content-Disposition", "attachment;filename="
                + destFileName);
        response.setContentType("application/vnd.ms-excel");
        try {
            in = new BufferedInputStream(new FileInputStream(templateFileName));
            Workbook workbook = transformer.transformXLS(in, beans);
            out = response.getOutputStream();
            // 将内容写入输出流并把缓存的内容全部发出去
            workbook.write(out);
            out.flush();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @RequestMapping("/view/{fkOrderProduct}")
    public String viewChecklist(@PathVariable("fkOrderProduct") Integer fkOrderProduct, Model model) {
        Checklist checklist = checklistServiceMgr.getDetailsByFkOrderProduct(fkOrderProduct);
        model.addAttribute("checklist", checklist);
        model.addAttribute("fkOrderProduct", fkOrderProduct);
        return "/management/order/viewchecklist";
    }
}
