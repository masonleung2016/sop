package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Charge;
import sop.vo.ChargeVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:18
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface ChargeMapper extends BaseMapper<Charge, Integer> {

    List<ChargeVo> getChargeListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getChargeListNumByCondition(BaseConditionVO vo);

    Integer getCountByFkChgCode(String fkChgCode);

    Integer insertCharge(Charge charge);

    Charge getChargeByFkChgCode(String fkChgCode);

    Integer updateCharge(Charge charge);

    Integer deleteCharge(String fkChgCode);

    List<ChargeVo> getAllCharges();
}
