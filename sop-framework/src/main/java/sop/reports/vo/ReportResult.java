package sop.reports.vo;

import java.util.List;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:24
 * @Package: sop.reports.vo
 */


public class ReportResult implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String status = "0";
    private Integer dataCount = 0;
    private String fileName;
    private String reportHtml;
    private List<ReportDownload> reportDownloads;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReportHtml() {
        return reportHtml;
    }

    public void setReportHtml(String reportHtml) {
        this.reportHtml = reportHtml;
    }

    public List<ReportDownload> getReportDownloads() {
        return reportDownloads;
    }

    public void setReportDownloads(List<ReportDownload> reportDownloads) {
        this.reportDownloads = reportDownloads;
    }
}
