package dwz.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Currency;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:19
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface CurrencyMapper extends BaseMapper<Currency, String> {

    List<Currency> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

    void updateStatus(@Param("ccyCode") String ccyCode, @Param("deleted") boolean b, @Param("modUsr") String modUsr, @Param("modDate") Date date);

    Currency getCurrencyByName(@Param("ccyCode") String name);

    List<Currency> findAllCurrencys();

    Integer deleteCurrency(String fkCcyCode);

    Integer getCountByFkCcyCode(String fkCcyCode);

    List<Currency> getAllCurrs();

}
