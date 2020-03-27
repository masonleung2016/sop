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
import dwz.persistence.mapper.ItemDocMapper;
import sop.persistence.beans.ItemDoc;
import sop.services.ItemDocServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:37
 * @Package: sop.services.impl
 */

@Service(ItemDocServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ItemDocServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ItemDocServiceMgr {

    @Autowired
    private ItemDocMapper itemDocMapper;

    @Override
    public List<ItemDoc> getItemDocListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ItemDoc> ItemDocList = itemDocMapper.getItemDocByCondition(vo, rb);
        return ItemDocList;
    }

    @Override
    public void getItemDocListNum(BaseConditionVO vo) {
        Integer count = itemDocMapper.getItemDocNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkItemDoc(ItemDoc itemDoc) {
        Checker checker = new Checker();
        if (itemDoc != null) {
            if (StringUtils.isBlank(itemDoc.getDocNo())) {
                checker.setSuccess(false);
                checker.setReturnStr("ItemDoc/No.不能为空,保存失败");
            } else {
                ItemDoc pb = itemDocMapper.getItemDocByNo(itemDoc.getDocNo());
                if (pb != null) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该ItemDoc 已存在");
                } else {
                    checker.setSuccess(true);
                    checker.setReturnStr("信息无误");
                }
            }
        } else 
        {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean addItemDoc(ItemDoc ItemDoc) {
        try {
            Date date = new Date();
            ItemDoc.setCrtDate(date);
            ItemDoc.setModDate(date);
            Integer flag = itemDocMapper.insertItemDoc(ItemDoc);
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
    public boolean updateItemDoc(ItemDoc ItemDoc) {
        try {
            Date date = new Date();
            ItemDoc.setModDate(date);
            Integer flag = itemDocMapper.updateItemDoc(ItemDoc);
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
    public boolean deleteItemDoc(String pb) throws ServiceException {
        Integer flag = itemDocMapper.deleteItemDoc(pb);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ItemDoc> getAllItemDocs() {
        List<ItemDoc> itemDocList = itemDocMapper.getAllItemDoc();
        return itemDocList;
    }

    @Override
    public ItemDoc getItemDocByNo(String docNo) {
        return itemDocMapper.getItemDocByNo(docNo);
    }
}
