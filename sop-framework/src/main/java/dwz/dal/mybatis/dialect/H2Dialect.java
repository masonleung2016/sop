package dwz.dal.mybatis.dialect;

/**
 * A dialect compatible with the H2 database.
 *
 * @Author: LCF
 * @Date: 2020/1/8 16:25
 * @Package: dwz.dal.mybatis.dialect
 */

public class H2Dialect extends Dialect {

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return new StringBuffer(sql.length() + 40).
                append(sql).
                append((offset > 0) ? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : " limit " + limitPlaceholder).
                toString();
    }

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

//    public boolean bindLimitParametersInReverseOrder() {
//        return true;
//    }
//
//    public boolean bindLimitParametersFirst() {
//        return false;
//    }


}
