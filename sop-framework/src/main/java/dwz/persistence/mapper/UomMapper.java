package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Uom;
import sop.vo.UomVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:33
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface UomMapper extends BaseMapper<Uom, Integer> {

    List<UomVo> getUomListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getUomListNumByCondition(BaseConditionVO vo);

    Integer getCountByFkuCode(String fkuCode);

    Integer insertUom(Uom uom);

    Uom getUomByFkuCode(String fkuCode);

    Integer updateUom(Uom uom);

    Integer deleteUom(String fkuCode);

    List<UomVo> getAllUoms();

}
