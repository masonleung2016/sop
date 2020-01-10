package dwz.common.util.web;

import java.util.List;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:46
 * @Package: dwz.common.util.web
 */

public class PageInfo {

    private int pageNo;
    private int pageSize;
    private int rows;
    private int pages;
    private int rangeSize = 10;

    private List<?> objects;

    public static void main(String[] args) {
        for (int tmpPageNo = 1; tmpPageNo <= 100; tmpPageNo++) {
            PageUtil pu = new PageUtil(1001, tmpPageNo, 10);
            PageInfo pageInfo = pu.getPageInfo();
            pageInfo.setRangeSize(20);

            System.out.println("当前第 " + pageInfo.getPageNo() + " 页" +
                    ";总页数：" + pageInfo.getPages() + "；" +
                    "区域大小：" + pageInfo.getPageSize() + ";" +
                    "区域中间值：" + pageInfo.getMiddle());

            int first = pageInfo.getRangeOfFirst();
            int end = pageInfo.getRangeOfEnd();
            for (int i = first; i <= end; i++) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public int getPageNo() {
        if (this.pageNo <= 1) {
            return 1;
        }
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return Returns the pageSize.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize - The pageSize to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return Returns the rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows - The rows to set.
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return Returns the pages.
     */
    public int getPages() {
        return pages;
    }

    /**
     * @param pages - The pages to set.
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * @return
     */
    public List<?> getObjects() {
        return objects;
    }

    /**
     * @param objectList
     */
    public void setObjects(List<?> objects) {
        this.objects = objects;
    }

    public int getNextPageNo() {
        if (this.pageNo >= this.pageNo) {
            return this.pageNo;
        }
        return this.pageNo + 1;
    }

    public int getPrePageNo() {
        if (this.pageNo <= 1) {
            return this.pageNo;
        }
        return this.pageNo - 1;
    }

    /**
     * @return Returns the rangeSize.
     */
    public int getRangeSize() {
        return rangeSize;
    }

    /**
     * 设定区域大小
     *
     * @param rangeSize - The rangeSize to set.
     */
    public void setRangeSize(int rangeSize) {
        this.rangeSize = rangeSize;
    }

    public int getMiddle() {
        return this.rangeSize / 2;
    }

    /**
     * 获取页面区域第一项
     *
     * @return
     */
    public int getRangeOfFirst() {
        if (this.getPages() <= this.getRangeSize()) {
            return 1;
        }

        if (this.getPageNo() - this.getMiddle() <= 1) {
            return 1;
        }

        return this.getPageNo() - this.getMiddle();
    }

    /**
     * 获取页面区域最后一项
     *
     * @return
     */
    public int getRangeOfEnd() {
        int end = this.getRangeOfFirst() - 1 + this.getRangeSize();
        if (end > this.getPages()) {
            return this.getPages();
        }
        return end;
    }
}
