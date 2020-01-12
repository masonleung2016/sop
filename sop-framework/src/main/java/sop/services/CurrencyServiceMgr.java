package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Currency;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:59
 * @Package: sop.services
 */

public interface CurrencyServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "currencyServiceMgr";

    List<Currency> searchCurrency(BaseConditionVO vo);

    Integer searchCurrencyNum(BaseConditionVO vo);

    void addCurrency(Currency currency) throws ServiceException;

    Currency getCurrencyByName(String name);

    void updateCurrency(Currency currency) throws ServiceException;

    boolean deleteCurrency(String fkCcyCode) throws ServiceException;

    Checker checkCurrencyDecimal(Currency currency);

    Checker checkDecimal(Currency currency);

    Checker checkCurrency(Currency currency);


}
