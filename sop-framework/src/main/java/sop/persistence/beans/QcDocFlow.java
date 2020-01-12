package sop.persistence.beans;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:03
 * @Package: sop.persistence.beans
 */


public class QcDocFlow extends BaseBean {


    private String seqNo;

    private String docName;

    private boolean system;

    private String docType;

    private String path;

    private boolean materialObject;

    private boolean supportUpload;

    private boolean photoRequired;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public boolean isMaterialObject() {
        return materialObject;
    }

    public void setMaterialObject(boolean materialObject) {
        this.materialObject = materialObject;
    }

    public boolean isSupportUpload() {
        return supportUpload;
    }

    public void setSupportUpload(boolean supportUpload) {
        this.supportUpload = supportUpload;
    }

    public boolean isPhotoRequired() {
        return photoRequired;
    }

    public void setPhotoRequired(boolean photoRequired) {
        this.photoRequired = photoRequired;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public Serializable getId() {
        return this.seqNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
