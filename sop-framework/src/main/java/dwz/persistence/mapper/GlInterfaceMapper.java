package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.GlInterface;
import sop.vo.GlInterfaceVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:22
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface GlInterfaceMapper extends BaseMapper<GlInterface, Integer> {

    List<GlInterfaceVo> getGlInterfaceListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getGlInterfaceListNumByCondition(BaseConditionVO vo);

    GlInterface getfkGlInfCodeByFkGlInfCode(String fkGlInfCode);

    void updateGlInterface(GlInterface currentGlInterface);

    Integer getCountByFkGlInfCode(String fkGlInfCode);

    Integer insertGlInterface(GlInterface currentGlInterface);

    Integer deleteGlInterface(String fkGlInfCode);

    List<GlInterfaceVo> getAllGlInterfaceVo();

}
