package dwz.dal.mybatis.dialect;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:29
 * @Package: dwz.dal.mybatis.dialect
 */

public class SybaseDialect extends Dialect {

    public boolean supportsLimit() {
        return false;
    }

    public boolean supportsLimitOffset() {
        return false;
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        throw new UnsupportedOperationException("paged queries not supported");
    }

}
