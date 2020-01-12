package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.ImproveSuggestion;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:23
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface ImproveSuggestionMapper extends BaseMapper<ImproveSuggestion, String> {

    ImproveSuggestion getImproveSuggestionByNo(String impNo);

    List<ImproveSuggestion> getImproveSuggestionByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getImproveSuggestionNumByCondition(BaseConditionVO vo);

    Integer insertImproveSuggestion(ImproveSuggestion charge);

    Integer updateImproveSuggestion(ImproveSuggestion charge);

    Integer deleteImproveSuggestion(String impNo);

    List<ImproveSuggestion> getAllImproveSuggestion();

}
