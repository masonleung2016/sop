package sop.reports.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:24
 * @Package: sop.reports.vo
 */

public class ReportDownload implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String fileType;
    private String fileName;
    private String remoteDownloadURL;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemoteDownloadURL() {
        return remoteDownloadURL;
    }

    public void setRemoteDownloadURL(String remoteDownloadURL) {
        this.remoteDownloadURL = remoteDownloadURL;
    }
}
