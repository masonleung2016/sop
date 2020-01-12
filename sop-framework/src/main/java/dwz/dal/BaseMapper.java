package dwz.dal;

import java.util.List;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:34
 * @Package: dwz.dal
 */

public interface BaseMapper<T extends AbstractDO, PK extends java.io.Serializable> {

    PK insert(T model);

    void delete(PK modelPK);

    T load(PK modelPK);

    void update(T model);

    void updateSelective(T model);

    int countAll();

    List<T> findAll();

    List<PK> findAllIds();

}
