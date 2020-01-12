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
import dwz.persistence.mapper.ChargeMapper;
import sop.persistence.beans.Charge;
import sop.services.ChargeServiceMgr;
import sop.vo.ChargeVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:31
 * @Package: sop.services.impl
 */


@Service(ChargeServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ChargeServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ChargeServiceMgr {

    @Autowired
    private ChargeMapper chargeMapper;

    @Override
    public List<ChargeVo> getChargeListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ChargeVo> chargeVoList = chargeMapper.getChargeListByCondition(vo, rb);
        return chargeVoList;
    }

    @Override
    public void getChargeListNum(BaseConditionVO vo) {
        Integer count = chargeMapper.getChargeListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkCharge(Charge charge) {
        Checker checker = new Checker();
        if (charge != null) {
            if (charge.getChgCode() == null || charge.getChgCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("Charge/Deduction Code不能为空,保存失败");
            } else {
                Integer count = chargeMapper.getCountByFkChgCode(charge.getChgCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Charge/Deduction Code已存在");
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
    public boolean addCharge(Charge charge) {
        try {
            Date date = new Date();
            charge.setCrtDate(date);
            charge.setModDate(date);
            Integer flag = chargeMapper.insertCharge(charge);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Charge getChargeByFkChgCode(String fkChgCode) {
        Charge currentCharge = chargeMapper.getChargeByFkChgCode(fkChgCode);
        return currentCharge;
    }

    @Override
    public boolean updateCharge(Charge charge) {
        try {
            Date date = new Date();
            charge.setModDate(date);
            Integer flag = chargeMapper.updateCharge(charge);
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
    public boolean deleteCharge(String fkChgCode) throws ServiceException {
        Integer flag = chargeMapper.deleteCharge(fkChgCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Checker checkChargeDecimal(Charge currentCharge) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currentCharge.getChgAmt() != null) {
            if (currentCharge.getChgAmt().doubleValue() < -9999999.99 || currentCharge.getChgAmt().doubleValue() > 9999999.99) {
                checker.setSuccess(false);
                checker.setReturnStr("Amount必须在[-9999999.99, 9999999.99]范围内");
                return checker;
            }
        }
        if (currentCharge.getChgRate() != null) {
            if (currentCharge.getChgRate().doubleValue() < -9999.99999 || currentCharge.getChgRate().doubleValue() > 9999.99999) {
                checker.setSuccess(false);
                checker.setReturnStr("Rate必须在[-9999.99999, 9999.99999]范围内");
                return checker;
            }
        }
        return checker;
    }

    @Override
    public List<ChargeVo> getAllCharges() {
        List<ChargeVo> chargeVoList = chargeMapper.getAllCharges();
        return chargeVoList;
    }
}
