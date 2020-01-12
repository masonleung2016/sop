package sop.services;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import sop.persistence.beans.Checklist;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:58
 * @Package: sop.services
 */

public interface ChecklistServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "checklistServiceMgr";

    Checklist getDetailsByFkOrderProduct(Integer fkorder_product);

}
