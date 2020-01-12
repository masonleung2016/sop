package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Charge;
import sop.persistence.beans.ItemDoc;
import sop.vo.ChargeVo;


/**
 * @Author: LCF
 * @Date: 2020/1/8 17:23
 * @Package: dwz.persistence.mapper
 */
@Repository
public interface ItemDocMapper extends BaseMapper<ItemDoc, String> {

    ItemDoc getItemDocByNo(String docNo);

    List<ItemDoc> getItemDocByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getItemDocNumByCondition(BaseConditionVO vo);

    Integer insertItemDoc(ItemDoc doc);

    Integer updateItemDoc(ItemDoc doc);

    Integer deleteItemDoc(String docNo);

    List<ItemDoc> getAllItemDoc();

}
