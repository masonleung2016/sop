package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.QcCheckList;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:11
 * @Package: sop.services
 */


public interface QcCheckListServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "qcCheckListServiceMgr";

    List<QcCheckList> getQcCheckListListByCondition(BaseConditionVO vo);

    void getQcCheckListListNum(BaseConditionVO vo);

    Checker checkQcCheckList(QcCheckList QcCheckList);

    boolean addQcCheckList(QcCheckList QcCheckList) throws ServiceException;

    QcCheckList getQcCheckListByNo(String pbNo);

    boolean updateQcCheckList(QcCheckList currentQcCheckList) throws ServiceException;

    boolean deleteQcCheckList(String pbNo) throws ServiceException;


    List<QcCheckList> getAllQcCheckLists();

    List<QcCheckList> getQcCheckListByItemType(int itemType);


}
