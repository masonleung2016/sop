package sop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.persistence.mapper.OrderMapper;
import sop.persistence.beans.Checklist;
import sop.services.ChecklistServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:32
 * @Package: sop.services.impl
 */

@Service(ChecklistServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ChecklistServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ChecklistServiceMgr {

    @Autowired
    private OrderMapper orderMapper;

    public static void main(String[] args) {

    }

    @Override
    public Checklist getDetailsByFkOrderProduct(Integer fkOrderProduct) {
        Checklist details = orderMapper.getDetailsByFkOrderProduct(fkOrderProduct);
        return details;
    }
}
