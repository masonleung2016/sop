package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:10
 * @Package: sop.persistence.beans
 */

public class WorkflowLog extends BaseBean {

    private Integer id;

    private String qcNo;

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQcNo() {
        return qcNo;
    }

    public void setQcNo(String qcNo) {
        this.qcNo = qcNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
