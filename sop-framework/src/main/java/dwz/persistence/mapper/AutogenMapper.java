package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Autogen;
import sop.vo.AutogenVo;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:18
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface AutogenMapper extends BaseMapper<Autogen, Integer> {

    List<AutogenVo> getAutogenListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getAutogenListNumByCondition(BaseConditionVO vo);

    Integer getCountByAutogen(Autogen autogen);

    Integer insertAutogen(Autogen autogen);

    List<CompanyVo> getAllCompany();

    Autogen getAutogen(Autogen autogen);

    Integer deleteAutogen(Autogen autogen);

    Integer updateAutogen(Autogen autogen);
}
