package sop.services;

import java.util.List;
import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.ItemDoc;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:02
 * @Package: sop.services
 */

public interface ItemDocServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "itemDocServiceMgr";

    List<ItemDoc> getItemDocListByCondition(BaseConditionVO vo);

    void getItemDocListNum(BaseConditionVO vo);

    Checker checkItemDoc(ItemDoc ItemDoc);

    boolean addItemDoc(ItemDoc ItemDoc) throws ServiceException;

    ItemDoc getItemDocByNo(String docNo);

    boolean updateItemDoc(ItemDoc currentItemDoc) throws ServiceException;

    boolean deleteItemDoc(String docNo) throws ServiceException;

    List<ItemDoc> getAllItemDocs();
}
