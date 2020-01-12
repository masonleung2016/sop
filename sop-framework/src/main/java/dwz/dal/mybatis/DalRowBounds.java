package dwz.dal.mybatis;

import org.apache.ibatis.session.RowBounds;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:32
 * @Package: dwz.dal.mybatis
 */

public class DalRowBounds extends RowBounds {

    private int extOffset;
    private int extLimit;
    private int total;

    public DalRowBounds(int offset, int limit) {
        this.extOffset = offset;
        this.extLimit = limit;
    }

    public int getExtOffset() {
        return extOffset;
    }

    public int getExtLimit() {
        return extLimit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
