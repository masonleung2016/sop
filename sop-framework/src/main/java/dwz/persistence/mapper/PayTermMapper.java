package dwz.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.PayTerm;
import sop.vo.PayTermVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:27
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface PayTermMapper extends BaseMapper<PayTerm, String> {

    // 查询
    List<PayTermVo> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

    int isUniquePayTerm(@Param("id") Integer id, @Param("name") String name);

    void updateStatus(@Param("code") String code, @Param("deleted") boolean b, @Param("modUsr") String modUsr, @Param("modDate") Date date);

    PayTerm getPayTermByCode(@Param("code") String code);

    List<PayTerm> findAllPayTerms();

    Integer getCountByFkPayCode(String fkPayCode);

    void deletePaymentTerm(String payCode);

    List<PayTermVo> getAllPayTermVo();

}
