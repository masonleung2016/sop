package dwz.common.util.io;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import dwz.common.util.DateUtil;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:34
 * @Package: dwz.common.util.io
 */

public class FileStoreUtils {

    public static FileStoreObject buildFilenamePathByNow(String ext, String physicalPath, String relativePath) {
        Assert.hasText(ext);
        physicalPath = formatPath(physicalPath);
        relativePath = formatPath(relativePath);
        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        String dateFormatDirectory = _dateFormatDirectory(new Date());
        FileStoreObject fso = new FileStoreObject(physicalPath, relativePath, ext, dateFormatDirectory, filename);
        return fso;
    }

    public static String formatPath(String path) {
        if (StringUtils.isEmpty(path) || StringUtils.isBlank(path) || path.equals("/")
                || path.equals("\\")) {
            path = "";
        }
        path = path.replaceAll("\\\\", "/");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    private static String _dateFormatDirectory(Date date) {
        return DateUtil.date2String(date, "/yyyy/MM/dd");
    }

    public static void main(String[] args) {
        FileStoreObject fso = buildFilenamePathByNow("pdf", "c:\\user\\usermamanger\\", "/services/");
        System.out.println(fso.getPhysicalPath());
        System.out.println(fso.getRelativePath());
        System.out.println(fso.getExt());
        System.out.println(fso.getDateFormatPath());
        System.out.println(fso.getFilename());
        System.out.println(fso.getFullPath());
        System.out.println(fso.getRelativeFullPath());
        System.out.println(fso.getDateFullPath());
    }
}
