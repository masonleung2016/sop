package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Charge;
import sop.persistence.beans.PackingBom;
import sop.vo.ChargeVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:26
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface PackingBomMapper extends BaseMapper<PackingBom, String> {

    PackingBom getPackingBomByNo(String pbNo);

    List<PackingBom> getPackingBomByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getPackingBomNumByCondition(BaseConditionVO vo);

    Integer insertPackingBom(PackingBom charge);

    Integer updatePackingBom(PackingBom charge);

    Integer deletePackingBom(String pbNo);

    List<PackingBom> getAllPackingBom();

}
