package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Company;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:19
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface ComMapper extends BaseMapper<Company, Integer> {

    List<CompanyVo> getComListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getComListNumByCondition(BaseConditionVO vo);

    Company getCompanyByFkCoCode(String fkCoCode);

    void updateCompany(Company currentCom);

    Integer getCountByFkCoCode(String fkCoCode);

    Integer insertCompany(Company currentCom);

    Integer deleteCompany(String fkCoCode);
}
