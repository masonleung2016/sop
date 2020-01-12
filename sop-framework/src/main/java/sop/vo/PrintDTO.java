package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:46
 * @Package: sop.vo
 */

public class PrintDTO implements java.io.Serializable {

    private static final long serialVersionUID = -101038718354574077L;
    private String app;
    private String fileNo;
    private String part;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

}
