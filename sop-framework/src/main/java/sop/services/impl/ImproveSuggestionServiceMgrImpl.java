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
import dwz.persistence.mapper.ImproveSuggestionMapper;
import sop.persistence.beans.ImproveSuggestion;
import sop.services.ImproveSuggestionServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:35
 * @Package: sop.services.impl
 */

@Service(ImproveSuggestionServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ImproveSuggestionServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ImproveSuggestionServiceMgr {

    @Autowired
    private ImproveSuggestionMapper improveSuggestionMapper;

    @Override
    public List<ImproveSuggestion> getImproveSuggestionListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ImproveSuggestion> ImproveSuggestionList = improveSuggestionMapper.getImproveSuggestionByCondition(vo, rb);
        return ImproveSuggestionList;
    }

    @Override
    public void getImproveSuggestionListNum(BaseConditionVO vo) {
        Integer count = improveSuggestionMapper.getImproveSuggestionNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkImproveSuggestion(ImproveSuggestion improveSuggestion) {
        Checker checker = new Checker();
        if (improveSuggestion != null) {
            if (StringUtils.isBlank(improveSuggestion.getImpNo())) {
                checker.setSuccess(false);
                checker.setReturnStr("ImproveSuggestion/No.不能为空,保存失败");
            } else {
                ImproveSuggestion pb = improveSuggestionMapper.getImproveSuggestionByNo(improveSuggestion.getImpNo());
                if (pb != null) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该ImproveSuggestion/Deduction Code已存在");
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
    public boolean addImproveSuggestion(ImproveSuggestion ImproveSuggestion) {
        try {
            Date date = new Date();
            ImproveSuggestion.setCrtDate(date);
            ImproveSuggestion.setModDate(date);
            Integer flag = improveSuggestionMapper.insertImproveSuggestion(ImproveSuggestion);
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
    public boolean updateImproveSuggestion(ImproveSuggestion ImproveSuggestion) {
        try {
            Date date = new Date();
            ImproveSuggestion.setModDate(date);
            Integer flag = improveSuggestionMapper.updateImproveSuggestion(ImproveSuggestion);
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
    public boolean deleteImproveSuggestion(String pb) throws ServiceException {
        Integer flag = improveSuggestionMapper.deleteImproveSuggestion(pb);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ImproveSuggestion> getAllImproveSuggestions() {
        List<ImproveSuggestion> improveSuggestionList = improveSuggestionMapper.getAllImproveSuggestion();
        return improveSuggestionList;
    }

    @Override
    public ImproveSuggestion getImproveSuggestionByNo(String impNo) {
        return improveSuggestionMapper.getImproveSuggestionByNo(impNo);
    }
}
