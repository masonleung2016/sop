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
import dwz.persistence.mapper.FactoryMapper;
import dwz.persistence.mapper.PayTermMapper;
import sop.persistence.beans.Factory;
import sop.services.FactoryServiceMgr;
import sop.utils.Common;
import sop.vo.FactoryComboVo;
import sop.vo.FactoryConditionVo;
import sop.vo.FactoryVo;
import sop.vo.PayTermVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:34
 * @Package: sop.services.impl
 */

@Transactional(rollbackFor = Exception.class)
@Service(FactoryServiceMgr.SERVICE_NAME)
public class FactoryServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements FactoryServiceMgr {

    @Autowired
    private FactoryMapper factoryMapper;

    @Autowired
    private PayTermMapper payTermMapper;

    @Override
    public List<FactoryVo> searchFactory(FactoryConditionVo vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<FactoryVo> factoryList = factoryMapper.findPageBreakByCondition(vo,
                rb);
        return factoryList;
    }

    @Override
    public Integer searchFactoryNum(FactoryConditionVo vo) {
        Integer count = factoryMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public void addFactory(Factory factory) throws ServiceException {
        Date now = new Date();
        factory.setCrtDate(now);
        factoryMapper.insert(factory);
    }

    @Override
    public Factory getFactoryBySuCode(String suCode) {
        Factory factory = factoryMapper.getFactoryBySuCode(suCode);
        return factory;
    }

    @Override
    public void updateFactory(Factory factory) throws ServiceException {
        Date now = new Date();
        factory.setModDate(now);
        factoryMapper.updateSelective(factory);
    }

    @Override
    public boolean deleteFactory(String fkSuCode) throws ServiceException {
        Integer flag = factoryMapper.deleteFactory(fkSuCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FactoryComboVo getFactoryComboVo() {
        FactoryComboVo factoryComboVo = new FactoryComboVo();
        List<PayTermVo> payTerms = payTermMapper.getAllPayTermVo();
        factoryComboVo.setPayTerms(payTerms);
        return factoryComboVo;
    }

    @Override
    public Checker checkFactoryDecimal(Factory factory) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (factory.getSuCredit() != null) {
            if (factory.getSuCredit().doubleValue() < -99999999.99 || factory.getSuCredit().doubleValue() > 99999999.99) {
                checker.setSuccess(false);
                checker.setReturnStr("Credit必须在[-99999999.99, 99999999.99]范围内");
                return checker;
            }
        }
        return checker;
    }

    @Override
    public Checker checkFactory(Factory factory) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (factory != null) {
            if (factory.getSuCode() == null || factory.getSuCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Factory Code, 保存失败");
            } else {
                Integer count = factoryMapper.getCountByFkSuCode(factory.getSuCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Factory Code已存在");
                } else {
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkDecimal(Factory factory) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (factory.getSuCredit() != null) {
            if (factory.getSuCredit().doubleValue() < -99999999.99 || factory.getSuCredit().doubleValue() > 99999999.99) {
                checker.setSuccess(false);
                checker.setReturnStr("Credit必须在[-99999999.99, 99999999.99]范围内");
                return checker;
            }
        }
        return checker;
    }

    @Override
    public String getLatestFactoryCodeByPrefix(String getCodeByPrefix) {
        String factoryCode = factoryMapper.getLatestFactoryCodeByPrefix(getCodeByPrefix);
        if (factoryCode != null) {
            Integer lastLetterIndex = Common.getLastLetterIndex(factoryCode);
            if (lastLetterIndex < 0) {
                factoryCode = (Integer.valueOf(factoryCode) + 1) + "";
            } else {
                Integer factoryCodeLength = factoryCode.length();
                String factoryCodePrefix = factoryCode.substring(0, lastLetterIndex + 1);
                String factoryCodeSuffix = factoryCode.substring(lastLetterIndex + 1, factoryCodeLength);
                Integer factoryCodeSuffixInt = Integer.valueOf(factoryCodeSuffix) + 1;
                factoryCode = factoryCodePrefix + factoryCodeSuffixInt;
            }
        }
        return factoryCode;
    }
}
