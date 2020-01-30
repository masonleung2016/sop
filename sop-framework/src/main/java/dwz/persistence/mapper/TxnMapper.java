package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Txn;
import sop.vo.TxnVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:32
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface TxnMapper extends BaseMapper<Txn, Integer> {

    List<TxnVo> getTxnListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getTxnListNumByCondition(BaseConditionVO vo);

    Integer getCountByFkTxtCode(String txtCode);

    Integer insertTxn(Txn txn);

    Txn getTxnByFkTxtCode(String fkTxtCode);

    Integer updateTxn(Txn txn);

    List<TxnVo> getAllTxns();

    Integer deleteTxn(String fkTxtCode);
}
