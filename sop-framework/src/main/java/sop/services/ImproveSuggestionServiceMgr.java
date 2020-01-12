package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.ImproveSuggestion;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:02
 * @Package: sop.services
 */

public interface ImproveSuggestionServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "improveSuggestionServiceMgr";

    List<ImproveSuggestion> getImproveSuggestionListByCondition(BaseConditionVO vo);

    void getImproveSuggestionListNum(BaseConditionVO vo);

    Checker checkImproveSuggestion(ImproveSuggestion improveSuggestion);

    boolean addImproveSuggestion(ImproveSuggestion improveSuggestion) throws ServiceException;

    ImproveSuggestion getImproveSuggestionByNo(String impNo);

    boolean updateImproveSuggestion(ImproveSuggestion currentImproveSuggestion) throws ServiceException;

    boolean deleteImproveSuggestion(String impNo) throws ServiceException;


    List<ImproveSuggestion> getAllImproveSuggestions();
}
