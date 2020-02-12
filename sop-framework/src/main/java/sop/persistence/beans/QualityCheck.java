package sop.persistence.beans;

import java.util.Date;

import dwz.framework.user.User;
import sop.vo.QualityCheckDetailsVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:04
 * @Package: sop.persistence.beans
 */

public class QualityCheck extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    private String qcNo;
    
    private String qcStatus;
    
    private String qcPoNoRef;
    
    private String qcSoNoRef;
    
    private String qcOfNoRef;
    
    private String assignee;
    
    private String comment;
    
    private Date startDate;
    
    private Date dueDate;
    
    private Date actualStartDate;
    
    private Date actualEndDate;
    
    private Integer id;
    
    public QualityCheck() {
    }
    
    public QualityCheck(String qcNo, User user, QualityCheckDetailsVo currentQc) {
        this.qcNo = qcNo;
        this.actualStartDate = currentQc.getActualStartDate();
        this.actualEndDate = currentQc.getActualCompleteDate();
        this.qcOfNoRef = currentQc.getQcOfNoRef();
        this.qcSoNoRef = currentQc.getQcSoNoRef();
        this.qcPoNoRef = qcNo;
        this.comment = currentQc.getComment();
        this.assignee = currentQc.getAssignee();
        this.dueDate = currentQc.getDueDate();
        this.startDate = currentQc.getStartDate();
        this.qcStatus = currentQc.getQcStatus();

        if (user != null) {
            setCrtUsr(user.getUsrId());
            setModUsr(user.getUsrId());
        } else {
            setCrtUsr("tst");
            setModUsr("tst");
        }
        setCrtDate(new Date());
        setModDate(new Date());
    }

    public QualityCheck(String qcNo, User user) {
        this.qcNo = qcNo;
        if (user != null) {
            setCrtUsr(user.getUsrId());
            setModUsr(user.getUsrId());
        } else {
            setCrtUsr("tst");
            setModUsr("tst");
        }
        setCrtDate(new Date());
        setModDate(new Date());
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getQcSoNoRef() {
        return qcSoNoRef;
    }

    public void setQcSoNoRef(String qcSoNoRef) {
        this.qcSoNoRef = qcSoNoRef;
    }

    public String getQcOfNoRef() {
        return qcOfNoRef;
    }

    public void setQcOfNoRef(String qcOfNoRef) {
        this.qcOfNoRef = qcOfNoRef;
    }

    public String getQcPoNoRef() {
        return qcPoNoRef;
    }

    public void setQcPoNoRef(String qcPoNoRef) {
        this.qcPoNoRef = qcPoNoRef;
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

    public String getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }
}
