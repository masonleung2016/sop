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
import dwz.persistence.mapper.CurrencyMapper;
import sop.persistence.beans.Currency;
import sop.services.CurrencyServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:33
 * @Package: sop.services.impl
 */

@Transactional(rollbackFor = Exception.class)
@Service(CurrencyServiceMgr.SERVICE_NAME)
public class CurrencyServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements CurrencyServiceMgr {

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public void addCurrency(Currency currency) throws ServiceException {
        Date now = new Date();
        currency.setCrtDate(now);
        currencyMapper.insert(currency);
    }

    @Override
    public List<Currency> searchCurrency(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Currency> currencyList = currencyMapper.findPageBreakByCondition(vo,
                rb);
        return currencyList;
    }

    @Override
    public Integer searchCurrencyNum(BaseConditionVO vo) {
        Integer count = currencyMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public Currency getCurrencyByName(String name) {
        Currency currency = currencyMapper.getCurrencyByName(name);
        if (currency == null) return null;
        return currency;
    }

    @Override
    public void updateCurrency(Currency currency) throws ServiceException {
        Date now = new Date();
        currency.setModDate(now);
        currencyMapper.updateSelective(currency);
    }

    @Override
    public boolean deleteCurrency(String fkCcyCode) throws ServiceException {
        Integer flag = currencyMapper.deleteCurrency(fkCcyCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Checker checkCurrencyDecimal(Currency currency) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currency.getCcyRate() != null) {
            if (currency.getCcyRate().doubleValue() < -9999.99999 || currency.getCcyRate().doubleValue() > 9999.99999) {
                checker.setSuccess(false);
                checker.setReturnStr("Rate必须在[-9999.99999, 9999.99999]范围内");
                return checker;
            }
        }
        return checker;
    }

    @Override
    public Checker checkDecimal(Currency currency) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currency.getCcyRate() != null) {
            if (currency.getCcyRate().doubleValue() < -9999.99999 || currency.getCcyRate().doubleValue() > 9999.99999) {
                checker.setSuccess(false);
                checker.setReturnStr("Rate必须在[-9999.99999, 9999.99999]范围内");
                return checker;
            }
        }
        return checker;
    }

    @Override
    public Checker checkCurrency(Currency currency) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currency != null) {
            if (currency.getCcyCode() == null || currency.getCcyCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Currency Code, 保存失败");
            } else {
                Integer count = currencyMapper.getCountByFkCcyCode(currency.getCcyCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Currency Code已存在");
                } else {

                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }
}
