package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.QcCheckList;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:29
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface QcCheckListMapper extends BaseMapper<QcCheckList, String> {

    QcCheckList getQcCheckListByNo(String pbNo);

    List<QcCheckList> getQcCheckListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getQcCheckListNumByCondition(BaseConditionVO vo);

    Integer insertQcCheckList(QcCheckList charge);

    Integer updateQcCheckList(QcCheckList charge);

    Integer deleteQcCheckList(String pbNo);

    List<QcCheckList> getAllQcCheckList();

    List<QcCheckList> getQcCheckListByItemType(int itemType);
}
