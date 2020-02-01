package sop.filegen;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:39
 * @Package: sop.filegen
 */

@XmlRootElement
@XmlType
public class GenRequest implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String reportId;

    private String outputType;

    private Map<String, Object> params = new LinkedHashMap<String, Object>();

    private List<String> fieldMeta = new ArrayList<String>();

    private List<String> htmlFields = new ArrayList<String>();

    private List<Object> dataList;

    private String requestUserId;

    private Date requestDatetime;

    private Date scheduleDatetime;

    private String outputTypeForItext;

    private String fillType;

    private boolean printBarCode;

    @XmlElement
    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @XmlElement
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @XmlElement
    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    @XmlElement
    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    @XmlElement
    public Date getRequestDatetime() {
        return requestDatetime;
    }

    public void setRequestDatetime(Date requestDatetime) {
        this.requestDatetime = requestDatetime;
    }


    public void addParameter(String key, Object value) {
        this.params.put(key, value);
    }

    public Object getParameter(String key) {
        return this.params.get(key);
    }

    public String getParameterAsString(String key) {
        return StringUtils.trimToEmpty(String.valueOf(this.params.get(key)));
    }

    @XmlElement
    public Date getScheduleDatetime() {
        return scheduleDatetime;
    }

    public void setScheduleDatetime(Date scheduleDatetime) {
        this.scheduleDatetime = scheduleDatetime;
    }

    public String getOutputTypeForItext() {
        return outputTypeForItext;
    }

    public void setOutputTypeForItext(String outputTypeForItext) {
        this.outputTypeForItext = outputTypeForItext;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "GenRequest [reportId=" + reportId + ", outputType=" + outputType + ", params=" + params + ", dataList=" + dataList + ", requestUserId=" + requestUserId
                + ", requestDatetime=" + requestDatetime + ", scheduleDatetime=" + scheduleDatetime + ", outputTypeForItext=" + outputTypeForItext + "]";
    }

    public List<String> getFieldMeta() {
        return fieldMeta;
    }


    public void addFieldAsList(String meta) {
        this.getFieldMeta().add(meta);
    }

    public String getFillType() {
        return fillType;
    }

    public void setFillType(String fillType) {
        this.fillType = fillType;
    }

    public boolean isPrintBarCode() {
        return printBarCode;
    }

    public void setPrintBarCode(boolean printBarCode) {
        this.printBarCode = printBarCode;
    }

    public List<String> getHtmlFields() {
        return htmlFields;
    }

    public void addFieldAsHtmlStyling(String field) {
        this.getHtmlFields().add(field);
    }
}
