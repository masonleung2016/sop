package sop.web.management;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dwz.web.BaseController;
import sop.utils.Common;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:47
 * @Package: sop.web.management
 */

@Controller("management.downloadController")
@RequestMapping(value = "/download")
public class DownloadController extends BaseController {

    @RequestMapping("/qc/{encodeQcNo}/{itemNo}")
    public void picView(@PathVariable("encodeQcNo") String encodeQcNo, @PathVariable("itemNo") String itemNo, Model model, HttpServletResponse response) {
        OutputStream sout;
        try {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + itemNo + ".xls");
            sout = response.getOutputStream();
            
            //输出的输出流
            byte b[] = Common.getQc(encodeQcNo, itemNo);
            if (b != null) {
                sout.write(b);
                sout.flush();
            }
            
            //输入完毕，清除缓冲
            sout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    @RequestMapping("/report/{type}/{date}/{filename.*}")
    public void downloadReport(@PathVariable("type") String type, @PathVariable("date") String date, @PathVariable("filename.*") String filename, Model model, HttpServletResponse response) {
        Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
        String tempOutputFolder = tempPropertiesMap.get("outputFolder");

        OutputStream sout;
        try {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            sout = response.getOutputStream();
            //输出的输出流
            byte b[] = Common.getReport(tempOutputFolder, "/" + type + "/" + date + "/" + filename);
            if (b != null) {
                sout.write(b);
                sout.flush();
            }
            //输入完毕，清除缓冲
            sout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
