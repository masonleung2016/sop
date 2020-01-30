package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Order;
import sop.persistence.beans.ProductItem;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:28
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface ProductItemMapper extends BaseMapper<Order, Integer> {

    List<ProductItem> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

}
