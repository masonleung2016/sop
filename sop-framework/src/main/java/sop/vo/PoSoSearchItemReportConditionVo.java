package sop.vo;

import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:46
 * @Package: sop.vo
 */

public class PoSoSearchItemReportConditionVo extends SoPoConditionVo {
    /**
     * search list condition
     */
    private Date fromDate;

    private Date toDate;

    private String orderStatus;

    private String handleUserId;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }
}
