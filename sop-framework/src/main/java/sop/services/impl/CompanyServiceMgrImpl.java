package sop.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.ComMapper;
import sop.persistence.beans.Company;
import sop.services.CompanyServiceMgr;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:32
 * @Package: sop.services.impl
 */

@Service(CompanyServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class CompanyServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements CompanyServiceMgr {

    @Autowired
    private ComMapper comMapper;

    @Override
    public List<CompanyVo> getComListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<CompanyVo> comVoList = comMapper.getComListByCondition(vo, rb);
        return comVoList;
    }

    @Override
    public void getComListNum(BaseConditionVO vo) {
        Integer count = comMapper.getComListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Company getCompanyById(String fkCoCode) {
        Company currentCom = comMapper.getCompanyByFkCoCode(fkCoCode);
        return currentCom;
    }

    @Override
    public boolean updateCompany(Company currentCom) {
        try {
            Date date = new Date();
            currentCom.setModDate(date);
            comMapper.updateCompany(currentCom);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Checker checkCompany(Company currentCom) {
        Checker checker = new Checker();
        if (currentCom != null) {
            if (currentCom.getCoCode() == null || currentCom.getCoCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("Co. Code不能为空,保存失败");
            } else {
                Integer count = comMapper.getCountByFkCoCode(currentCom.getCoCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Co. Code已存在,请使用其他ID");
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
    public boolean insertCompany(Company currentCom) {
        try {
            Date date = new Date();
            currentCom.setCrtDate(date);
            currentCom.setModDate(date);
            Integer flag = comMapper.insertCompany(currentCom);
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
    public boolean deleteCompany(String fkCoCode) {
        Integer flag = comMapper.deleteCompany(fkCoCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
}
