package sop.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import dwz.adapter.web.filter.SessionValidateFilter;
import dwz.framework.vo.Checker;
import it.sauronsoftware.base64.Base64;
import net.sf.json.JSONObject;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:26
 * @Package: sop.utils
 */

public class Common {

    private static final Log log = LogFactory
            .getLog(SessionValidateFilter.class);

    /**
     * @param fileName 文件名(放在resource源包目录下)，需要后缀
     *                 <p>
     *                 Spring 提供的 PropertiesLoaderUtils 允许您直接通过基于类路径的文件地址加载属性资源
     *                 最大的好处就是：实时加载配置文件，修改后立即生效，不必重启
     */
    public static Map<String, String> getProperties(String fileName) {
        Properties props = null;
        Map<String, String> resultMap = null;
        try {
            Resource resource = new ClassPathResource(fileName);
            props = PropertiesLoaderUtils.loadProperties(resource);
            if (props != null) {
                resultMap = new HashMap<String, String>();
                for (Object key : props.keySet()) {
                    resultMap.put(key.toString(), props.get(key).toString());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return resultMap;
    }

    /**
     * 传递键值对的Map，更新properties文件
     *
     * @param fileName    文件名(放在resource源包目录下)，需要后缀
     * @param keyValueMap 键值对Map
     */
    public static void updateProperties(String fileName,
                                        Map<String, String> keyValueMap) {
        // getResource方法使用了utf-8对路径信息进行了编码，当路径中存在中文和空格时，他会对这些字符进行转换，这样，
        // 得到的往往不是我们想要的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的中文及空格路径。
        String filePath = Common.class.getClassLoader().getResource(fileName)
                .getFile();
        Properties props = null;
        BufferedWriter bw = null;

        try {
            filePath = URLDecoder.decode(filePath, "utf-8");
            log.debug("updateProperties propertiesPath:" + filePath);
            props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(
                    fileName));
            log.debug("updateProperties old:" + props);

            // 写入属性文件
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath)));

            props.clear();// 清空旧的文件

            for (String key : keyValueMap.keySet())
                props.setProperty(key, keyValueMap.get(key));

            log.debug("updateProperties new:" + props);
            props.store(bw, "");
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param fileName 文件名(放在resource源包目录下)，需要后缀
     *                 <p>
     *                 读取excel
     */
    public static void getExcel(String fileName) {
        // XLSTransformer transformer = new XLSTransformer();
        // String filePath =
        // Common.class.getClassLoader().getResource("/itemmaster/chair/test.xlsx").toString();
        // transformer.transformXLS(filePath, beanParams, destFilePath);
        // Map<String, String> department = new HashMap<String, String>();
        // Map beans = new HashMap();
        // beans.put("department", department);
        // /sop-web/src/main/resources/itemmaster/chair/test.xlsx
        // /sop-web/src/main/resources/jdbc.properties
    }

    /**
     * 渲染Excel模块并在浏览器上导出Excel
     *
     * @param response
     * @param templateFileName 模块Excel文件的路径
     * @param beans            要渲染到“Excle的内容
     */
    public static void TransformXLSAndExportExcel(HttpServletResponse response,
                                                  String templateFileName, String destFileName, Map<String, Object> beans) {

        // 设置响应
        response.setHeader("Content-Disposition", "attachment;filename="
                + destFileName);
        response.setContentType("application/vnd.ms-excel");

        XLSTransformer transformer = new XLSTransformer();
        InputStream in = null;
        OutputStream out = null;
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
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * excel注入数据
     */
    public static Workbook importToExcel(String templateFileName, Map<String, Object> beans) {
        XLSTransformer transformer = new XLSTransformer();
        InputStream in = null;
        Workbook workbook = null;
        try {
            in = new BufferedInputStream(new FileInputStream(templateFileName));
            if (in != null) {
                workbook = transformer.transformXLS(in, beans);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 下载网络图片到本地
     *
     * @param _url     网络图片地址
     * @param savePath 保存本地地址
     */
    public static Checker down(String fileName, InputStream fis, String saveBasePath) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("保存成功!");
        try {
            String savePath = saveBasePath + "/" + fileName;

            mkDirs(saveBasePath);

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(savePath));

            byte[] data = new byte[1024];
            while (fis.read(data) != -1) {
                bos.write(data);
            }

            bos.flush();
            fis.close();
            bos.close();
            return checker;
        } catch (Exception e) {
            e.printStackTrace();
            checker.setSuccess(false);
            checker.setReturnStr("保存失败!");
            return checker;
        }
    }

    public static void mkDirs(String saveBasePath) {
        File file = new File(saveBasePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                mkDirs(file.getParent());
            }
        }
    }

    public static byte[] getPic(String picPath) {
        Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
        String baselocation = ftpPropertiesMap.get("baselocation");
        try {
            FileInputStream is = new FileInputStream(baselocation + "/" + picPath);
            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            return data;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String jsonMapToString(Map<String, Object> currentPartPic) {
        String returnStr = "{}";
        try {
            JSONObject jsonObject = JSONObject.fromObject(currentPartPic);
            returnStr = jsonObject.toString();
            if (returnStr.equals("{}")) {
                returnStr = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    public static Integer getLastLetterIndex(String str) {
        int step = -1;        //记录字符的位置
        int strLen = str.length();
        for (int i = strLen - 1; i > -1; i--) {
            char c = str.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c < 'Z')) {    //英文字符
                step = i;        //记录英文字符的位置
                break;
            }
        }
        return step;
    }

    public static boolean confirmDir(String downBasepath) {
        File file = new File(downBasepath);
        return file.exists();
    }

    public static byte[] getQc(String encodeQcNo, String itemNo) {
        Map<String, String> ftpPropertiesMap = Common.getProperties("/temp.properties");
        String baselocation = ftpPropertiesMap.get("baselocation");
        try {
            FileInputStream is = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itemNo + ".xls");
            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            return data;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String getPic(String pic, String fileName, String url) {
        url = Base64.encode(url, "utf-8");
        Map<String, Object> picVo = new LinkedHashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            //把pic data json string 转成map对象
            if (pic == null || !pic.startsWith("{")) {
                pic = "{}";
            }
            picVo = mapper.readValue(pic, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            picVo.put(fileName, url);
            pic = Common.jsonMapToString(picVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pic;
    }

    public static String getQcPic(String pic, String fileName, String baseUrl) {
        String encodeFileName = Base64.encode(fileName, "utf-8");
        String url = baseUrl + "/" + encodeFileName;
        Map<String, Object> picVo = new LinkedHashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            //把pic data json string 转成map对象
            if (pic == null || !pic.startsWith("{")) {
                pic = "{}";
            }
            picVo = mapper.readValue(pic, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            picVo.put(fileName, url);
            pic = Common.jsonMapToString(picVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pic;
    }

    public static void newQcExcelFile(String basePath, String filePath) {
        mkDirs(basePath);
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] getReport(String tempOutputFolder, String url) {
        try {
            FileInputStream is = new FileInputStream(tempOutputFolder + url);
            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            return data;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static byte[] getB(String location) {
        try {
            FileInputStream is = new FileInputStream(location);
            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            return data;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 中文转拼音
     *
     * @param chinese 中文字符串
     */
    public String convertChnToPinyin(String chinese) {
        String pinyinName = "";
        char[] nameChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * json str to map
     * 面向本项目的平面化string json
     * map<String, String>
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public Map<String, String> jsonStrToMap(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
        });
        return returnMap;
    }

//	@RequestMapping("/export")
//	public void export(HttpServletRequest request, HttpServletResponse response) {
////		上面的路径：D:\RRR\project\sop-web\src\main\webapp\resources\itemmaster\templateFileName.xlsx (系统找不到指定的路径。)
//		String templateFileName = request.getSession().getServletContext()
//				.getRealPath("/")
//				+ "/templateFileName.xlsx";
//		String destFileName = "destFileName.xlsx";
//		Employee test1 = new Employee(1, "test1");
////		// 模拟数据
////		List<Employee> staff = new ArrayList<Employee>();
////		staff.add(new Employee(1, "test1"));
////		staff.add(new Employee(2, "test2"));
////		staff.add(new Employee(3, "test3"));
//		Map<String, Object> beans = new HashMap<String, Object>();
////		beans.put("employees", staff);
//		beans.put("test1", test1);
//		XLSTransformer transformer = new XLSTransformer();
//		InputStream in = null;
//		OutputStream out = null;
//		// 设置响应
//		response.setHeader("Content-Disposition", "attachment;filename="
//				+ destFileName);
//		response.setContentType("application/vnd.ms-excel");
//		try {
//			in = new BufferedInputStream(new FileInputStream(templateFileName));
//			Workbook workbook = transformer.transformXLS(in, beans);
//			out = response.getOutputStream();
//			// 将内容写入输出流并把缓存的内容全部发出去
//			workbook.write(out);
//			out.flush();
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//				}
//			}
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//	}
}
