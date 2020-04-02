package sop.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.common.util.StringUtils;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.QcCheckListMapper;
import sop.persistence.beans.QcCheckList;
import sop.services.QcCheckListServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:51
 * @Package: sop.services.impl
 */

@Service(QcCheckListServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class QcCheckListServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements QcCheckListServiceMgr {

    @Autowired
    private QcCheckListMapper qcCheckListMapper;

    @Override
    public List<QcCheckList> getQcCheckListListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<QcCheckList> QcCheckListList = qcCheckListMapper.getQcCheckListByCondition(vo, rb);
        return QcCheckListList;
    }

    @Override
    public void getQcCheckListListNum(BaseConditionVO vo) {
        Integer count = qcCheckListMapper.getQcCheckListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkQcCheckList(QcCheckList qcCheckList) {
        Checker checker = new Checker();
        if (qcCheckList != null) {
            if (StringUtils.isBlank(qcCheckList.getQctNo())) {
                checker.setSuccess(false);
                checker.setReturnStr("QcCheckList/No.不能为空,保存失败");
            } else {
                QcCheckList pb = qcCheckListMapper.getQcCheckListByNo(qcCheckList.getQctNo());
                if (pb != null) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该QcCheckList已存在");
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
    public boolean addQcCheckList(QcCheckList QcCheckList) {
        try {
            Date date = new Date();
            QcCheckList.setCrtDate(date);
            QcCheckList.setModDate(date);
            Integer flag = qcCheckListMapper.insertQcCheckList(QcCheckList);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateQcCheckList(QcCheckList QcCheckList) {
        try {
            Date date = new Date();
            QcCheckList.setModDate(date);
            Integer flag = qcCheckListMapper.updateQcCheckList(QcCheckList);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteQcCheckList(String pb) throws ServiceException {
        Integer flag = qcCheckListMapper.deleteQcCheckList(pb);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<QcCheckList> getAllQcCheckLists() {
        List<QcCheckList> qcCheckListList = qcCheckListMapper.getAllQcCheckList();
        return qcCheckListList;
    }

    @Override
    public QcCheckList getQcCheckListByNo(String pbNo) {
        return qcCheckListMapper.getQcCheckListByNo(pbNo);
    }

    @Override
    public List<QcCheckList> getQcCheckListByItemType(int itemType) {
        List<QcCheckList> qcCheckListList = qcCheckListMapper.getQcCheckListByItemType(itemType);
        return qcCheckListList;
    }
}
