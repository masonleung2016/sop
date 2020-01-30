package dwz.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Supplier;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:32
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface SupplierMapper extends BaseMapper<Supplier, String> {

    List<Supplier> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

    void updateStatus(@Param("code") String ccyCode, @Param("deleted") boolean b, @Param("modUsr") String modUsr, @Param("modDate") Date date);

    Supplier getSupplierByCode(@Param("code") String code);

    List<Supplier> findAllSuppliers();
    
}
