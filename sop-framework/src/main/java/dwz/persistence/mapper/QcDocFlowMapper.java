package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.QcDocFlow;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:29
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface QcDocFlowMapper extends BaseMapper<QcDocFlow, String> {

    QcDocFlow getQcDocFlowByNo(String seqNo);

    List<QcDocFlow> getQcDocFlowByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getQcDocFlowNumByCondition(BaseConditionVO vo);

    Integer insertQcDocFlow(QcDocFlow doc);

    Integer updateQcDocFlow(QcDocFlow doc);

    Integer deleteQcDocFlow(String seqNo);

    List<QcDocFlow> getAllQcDocFlow();
}
