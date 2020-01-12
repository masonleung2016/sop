package dwz.dal.mybatis.dialect;

/**
 * Dialect for HSQLDB
 *
 * @Author: LCF
 * @Date: 2020/1/8 16:25
 * @Package: dwz.dal.mybatis.dialect
 */

public class HSQLDialect extends Dialect {

    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return true;
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        boolean hasOffset = offset > 0;
        return new StringBuffer(sql.length() + 10)
                .append(sql)
                .insert(sql.toLowerCase().indexOf("select") + 6, hasOffset ? " limit " + offsetPlaceholder + " " + limitPlaceholder : " top " + limitPlaceholder)
                .toString();
    }

}
