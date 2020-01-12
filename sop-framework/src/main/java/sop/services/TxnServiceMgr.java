package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Txn;
import sop.vo.TxnVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:14
 * @Package: sop.services
 */

public interface TxnServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "txnServiceMgr";

    List<TxnVo> getTxnListByCondition(BaseConditionVO vo);

    void getTxnListNum(BaseConditionVO vo);

    Checker checkTxn(Txn txn);

    boolean addTxn(Txn txn) throws ServiceException;

    Txn getTxnByFkTxtCode(String fkTxtCode);

    boolean updateTxn(Txn currentTxn) throws ServiceException;

    boolean deleteTxn(String fkTxtCode);
}
