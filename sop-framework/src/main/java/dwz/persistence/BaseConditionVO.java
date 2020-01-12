package dwz.persistence;

import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:34
 * @Package: dwz.persistence.mapper
 */

public class BaseConditionVO {
    public final static int PAGE_SHOW_COUNT = 10;
    private int pageNum = 1;
    private int pageSize = 10;
    private int totalCount = 0;
    private String orderField;
    private String orderDirection;
    private String keywords;
    private String status;
    private String type;
    private Date startDate;
    private Date endDate;

    public String getType() {
        return "".equals(type) ? null : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return "".equals(status) ? null : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);
        }
        this.endDate = endDate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize > 0 ? pageSize : PAGE_SHOW_COUNT;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return "desc".equals(orderDirection) ? "desc" : "asc";
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getKeywords() {
        return "".equals(keywords) ? null : keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getStartIndex() {
        int pageNum = this.getPageNum() > 0 ? this.getPageNum() - 1 : 0;
        return pageNum * this.getPageSize();
    }
}
