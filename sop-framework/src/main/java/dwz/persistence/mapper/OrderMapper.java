package dwz.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Checklist;
import sop.persistence.beans.Order;
import sop.persistence.beans.OrderProduct;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:25
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface OrderMapper extends BaseMapper<Order, Integer> {

    List<Order> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

    List<OrderProduct> getProducdtsByOrderId(int id);

    Integer insertOrderProduct(OrderProduct op);

    Integer addChecklist(Map<String, Object> params);

    Checklist getDetailsByFkOrderProduct(Integer fkOrderProduct);

}
