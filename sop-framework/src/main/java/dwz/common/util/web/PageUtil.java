package dwz.common.util.web;

/**
 * 页面分页工具类
 *
 * @Author: LCF
 * @Date: 2020/1/8 15:47
 * @Package: dwz.common.util.web
 */

public class PageUtil {

    private int pageNo;
    private int pageSize;
    private int offset;
    private int pages;
    private int rows;

    /**
     * 构造函数
     *
     * @param rows     总行数
     * @param pageNo   当前页号
     * @param pageSize 页面大小
     */
    public PageUtil(int rows, int pageNo, int pageSize) {
        if (pageSize <= 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }

        if (pageNo <= 0) {
            pageNo = 1;
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }

        if (rows <= 0) {
            this.pageNo = 1;
            this.pages = 1;
            this.offset = 0;
            this.rows = 0;
            return;
        }

        this.rows = rows;
        int tmp = rows % this.pageSize;

        if (tmp == 0) {
            this.pages = rows / this.pageSize;
        } else {
            this.pages = rows / this.pageSize + 1;
        }
        if (pageNo > pages) {
            this.pageNo = pages;
        }
        this.offset = (this.pageNo - 1) * pageSize;
    }

    /**
     * 返回当前页
     *
     * @return Returns the pageNo.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 获取当前
     *
     * @return Returns the pageSize.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取数据库第几项偏移量
     *
     * @return Returns the offset.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * 获取总页数
     *
     * @return Returns the pages.
     */
    public int getPages() {
        return pages;
    }

    /**
     * 获取行数
     *
     * @return Returns the rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * 返回页面VO对象
     *
     * @return
     */
    
    public PageInfo getPageInfo() {
        PageInfo info = new PageInfo();
        info.setPages(this.getPages());
        info.setRows(this.getRows());
        info.setPageNo(this.getPageNo());
        info.setPageSize(this.getPageSize());
        return info;
    }
}
