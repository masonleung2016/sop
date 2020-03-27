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
import dwz.persistence.mapper.PayTermMapper;
import sop.persistence.beans.PayTerm;
import sop.services.PayTermServiceMgr;
import sop.vo.PayTermVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:48
 * @Package: sop.services.impl
 */

@Transactional(rollbackFor = Exception.class)
@Service(PayTermServiceMgr.SERVICE_NAME)
public class PayTermServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements PayTermServiceMgr {

    @Autowired
    private PayTermMapper payTermMapper;

    @Override
    public List<PayTermVo> searchPayTerm(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<PayTermVo> payTermList = payTermMapper.findPageBreakByCondition(vo,
                rb);
        return payTermList;
    }

    @Override
    public Integer searchPayTermNum(BaseConditionVO vo) {
        Integer count = payTermMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public boolean addPayTerm(PayTerm payTerm) throws ServiceException {
        Date now = new Date();
        payTerm.setCrtDate(now);
        payTerm.setModDate(now);
        payTermMapper.insert(payTerm);
        return true;
    }

    @Override
    public PayTerm getPayTerm(String code) {
        PayTerm payTerm = payTermMapper.load(code);
        return payTerm;
    }

    @Override
    public boolean updatePayTerm(PayTerm payTerm) throws ServiceException {
        Date now = new Date();
        payTerm.setModDate(now);
        payTermMapper.updateSelective(payTerm);
        return true;
    }

    @Override
    public Checker checkPaytm(PayTerm payTerm) {
        Checker checker = new Checker();
        if (payTerm != null) {
            if (payTerm.getPayCode() == null || payTerm.getPayCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("Pay Code不能为空,保存失败");
            } else {
                Integer count = payTermMapper.getCountByFkPayCode(payTerm.getPayCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Pay Code已存在,请使用其他ID");
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
    public boolean deletePaymentTerm(String payCode) {
        payTermMapper.deletePaymentTerm(payCode);
        return true;
    }

    @Override
    public Checker checkDecimal(PayTerm payTerm) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (payTerm.getPayDays() != null) {
            if (payTerm.getPayDays().doubleValue() < 1 || payTerm.getPayDays().doubleValue() > 9999999999.) {
                checker.setSuccess(false);
                checker.setReturnStr("Pay Days必须在[1, 9999999999]范围内");
                return checker;
            }
        }
        return checker;
    }
}
