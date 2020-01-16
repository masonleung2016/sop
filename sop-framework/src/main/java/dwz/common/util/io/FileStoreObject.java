package dwz.common.util.io;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:34
 * @Package: dwz.common.util.io
 */

public class FileStoreObject {
    private String physicalPath;
    private String relativePath;
    private String ext;
    private String dateFormatPath;
    private String filename;

    public FileStoreObject(String physicalPath, String relativePath, String ext, String dateFormatPath, String filename) {
        this.physicalPath = physicalPath;
        this.relativePath = relativePath;
        this.ext = ext;
        this.dateFormatPath = dateFormatPath;
        this.filename = filename;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getExt() {
        return ext;
    }

    public String getDateFormatPath() {
        return dateFormatPath;
    }

    public String getFilename() {
        return filename;
    }

    public String getFullPath() {
        return this.physicalPath + this.relativePath + this.dateFormatPath + "/" + this.filename + "." + this.ext;
    }

    public String getRelativeFullPath() {
        return this.relativePath + this.dateFormatPath + "/" + this.filename + "." + this.ext;
    }

    public String getDateFullPath() {
        return this.dateFormatPath + "/" + this.filename + "." + this.ext;
    }
}
