package dwz.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Factory;
import sop.vo.FactoryVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:21
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface FactoryMapper extends BaseMapper<Factory, String> {

    // 查询
    List<FactoryVo> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

    void updateStatus(@Param("suCode") String suCode, @Param("deleted") boolean b, @Param("modUsr") String modUsr, @Param("modDate") Date date);

    Factory getFactoryBySuCode(@Param("suCode") String suCode);

    List<Factory> findAllFactories();

    Integer deleteFactory(String fkSuCode);

    Integer getCountByFkSuCode(String suCode);

    List<FactoryVo> getAllFactories();

    String getLatestFactoryCodeByPrefix(String getCodeByPrefix);
}
