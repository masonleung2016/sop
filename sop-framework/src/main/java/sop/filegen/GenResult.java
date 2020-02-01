package sop.filegen;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:39
 * @Package: sop.filegen
 */

@XmlRootElement
public class GenResult implements java.io.Serializable {

    private String reportId;

    private String returnCode;

    private String reportFile;

    private String remoteDownloadURL;

    private String message;

    private String status;

    private Integer logId;

    private long startTime;

    private long endTime;


    @XmlElement
    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    @XmlElement
    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    @XmlElement
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @XmlElement
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getRemoteDownloadURL() {
        return remoteDownloadURL;
    }

    public void setRemoteDownloadURL(String remoteDownloadURL) {
        this.remoteDownloadURL = remoteDownloadURL;
    }

    @Override
    public String toString() {
        return "GenResult [reportId=" + reportId + ", returnCode=" + returnCode + ", reportFile=" + reportFile + ", remoteDownloadURL=" + remoteDownloadURL + ", message="
                + message + ", status=" + status + ", logId=" + logId + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }
}
