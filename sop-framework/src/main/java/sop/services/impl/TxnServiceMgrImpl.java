package sop.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.TxnMapper;
import sop.persistence.beans.Txn;
import sop.services.TxnServiceMgr;
import sop.vo.TxnVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:56
 * @Package: sop.services.impl
 */

@Service(TxnServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class TxnServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements TxnServiceMgr {

    @Autowired
    private TxnMapper txnMapper;

    @Override
    public List<TxnVo> getTxnListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<TxnVo> txnVoList = txnMapper.getTxnListByCondition(vo, rb);
        return txnVoList;
    }

    @Override
    public void getTxnListNum(BaseConditionVO vo) {
        Integer count = txnMapper.getTxnListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkTxn(Txn txn) {
        Checker checker = new Checker();
        if (txn != null) {
            if (txn.getTxtCode() == null || txn.getTxtCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("Txn. type不能为空,保存失败");
            } else {
                Integer count = txnMapper.getCountByFkTxtCode(txn.getTxtCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Txn. type已存在");
                } else {
                    checker.setSuccess(true);
                    checker.setReturnStr("信息无误");
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean addTxn(Txn txn) throws ServiceException {
        try {
            Date date = new Date();
            txn.setCrtDate(date);
            txn.setModDate(date);
            Integer flag = txnMapper.insertTxn(txn);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Txn getTxnByFkTxtCode(String fkTxtCode) {
        Txn currentTxn = txnMapper.getTxnByFkTxtCode(fkTxtCode);
        return currentTxn;
    }

    @Override
    public boolean updateTxn(Txn txn) throws ServiceException {
        try {
            Date date = new Date();
            txn.setModDate(date);
            Integer flag = txnMapper.updateTxn(txn);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteTxn(String fkTxtCode) {
        Integer flag = txnMapper.deleteTxn(fkTxtCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
}
