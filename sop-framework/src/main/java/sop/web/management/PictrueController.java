package sop.web.management;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.utils.Common;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:55
 * @Package: sop.web.management
 */


@Controller("management.picController")
@RequestMapping(value = "/pic")
public class PictrueController extends BaseController {
    @RequestMapping("/PHOTO-JPEG/{url}")
    public void picView(@PathVariable("url") String url, Model model, HttpServletResponse response) {
        url = Base64.decode(url, "utf-8");
        String picPath = "PHOTO-JPEG/" + url;

        OutputStream sout;
        try {
            sout = response.getOutputStream();
            //图片输出的输出流
            byte b[] = Common.getPic(picPath);
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

    @RequestMapping("/PART-PHOTO-JPEG/{url}")
    public void partPicView(@PathVariable("url") String url, Model model, HttpServletResponse response) {
        url = Base64.decode(url, "utf-8");

        String picPath = "PART-PHOTO-JPEG/" + url;

        OutputStream sout;
        try {
            sout = response.getOutputStream();
            //图片输出的输出流
            byte b[] = Common.getPic(picPath);
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

    @RequestMapping("/QC-PHOTO-JPEG/{qcNo}/{itCatNoSuffix}/{fileName}")
    public void qcPicView(@PathVariable("qcNo") String qcNo, @PathVariable("itCatNoSuffix") String itCatNoSuffix, @PathVariable("fileName") String fileName, Model model, HttpServletResponse response) {
        String decodeFileName = Base64.decode(fileName, "utf-8");
        String url = qcNo + "/" + itCatNoSuffix + "/" + decodeFileName;

        String picPath = "QC-PHOTO-JPEG/" + url;

        OutputStream sout;
        try {
            sout = response.getOutputStream();
            //图片输出的输出流
            byte b[] = Common.getPic(picPath);
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


    @RequestMapping("/barcode/ean128/{barcode}")
    public void viewbarcode(@PathVariable("barcode") String barcode, HttpServletResponse response) {
        OutputStream sout = null;
        try {
            sout = response.getOutputStream();
            sop.utils.BarcodeUtil.generate(barcode, sout);
            sout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sout != null) {
                try {
                    sout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }
}
