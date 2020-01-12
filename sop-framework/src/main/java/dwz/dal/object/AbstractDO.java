package dwz.dal.object;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 基础Data Object 类
 *
 * @Author: LCF
 * @Date: 2020/1/8 16:33
 * @Package: dwz.dal.object
 */

public abstract class AbstractDO implements Serializable {

    private static final long serialVersionUID = -3942149913171834745L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public abstract Serializable getId();
}
