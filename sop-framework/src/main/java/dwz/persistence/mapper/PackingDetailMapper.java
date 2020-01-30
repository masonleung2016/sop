package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.PackingDetail;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:26
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface PackingDetailMapper extends BaseMapper<PackingDetail, String> {

    PackingDetail getPackingDetailByNo(String pdNo);

    List<PackingDetail> getPackingDetailByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getPackingDetailNumByCondition(BaseConditionVO vo);

    Integer insertPackingDetail(PackingDetail charge);

    Integer updatePackingDetail(PackingDetail charge);

    Integer deletePackingDetail(String pdNo);

    List<PackingDetail> getAllPackingDetail();
}
