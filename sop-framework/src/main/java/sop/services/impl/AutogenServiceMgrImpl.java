package sop.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.persistence.mapper.AutogenMapper;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import sop.persistence.beans.Autogen;
import sop.services.AutogenServiceMgr;
import sop.vo.AutogenVo;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:30
 * @Package: sop.services.impl
 */


@Service(AutogenServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class AutogenServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements AutogenServiceMgr {

    @Autowired
    private AutogenMapper autogenMapper;

    @Override
    public List<AutogenVo> getAutogenListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<AutogenVo> autogenVoList = autogenMapper.getAutogenListByCondition(vo, rb);
        return autogenVoList;
    }

    @Override
    public void getAutogenListNum(BaseConditionVO vo) {
        Integer count = autogenMapper.getAutogenListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkAutogen(Autogen autogen, boolean isUpdate) {
        Checker checker = new Checker();
        if (autogen != null) {
            if (StringUtils.isEmpty(autogen.getCoCode())) {
                checker.setSuccess(false);
                checker.setReturnStr("请选择Company code,保存失败");
                return checker;
            }
            if (StringUtils.isEmpty(autogen.getAutTxType())) {
                checker.setSuccess(false);
                checker.setReturnStr("Autogen No. Type不能为空,保存失败");
                return checker;
            }

            if (StringUtils.isNotEmpty(autogen.getAutTxNum()) && !NumberUtils.isDigits(autogen.getAutTxNum())) {
                checker.setSuccess(false);
                checker.setReturnStr("Autogen No. 不是数字,保存失败");
                return checker;
            }

            if (!isUpdate) {
                Integer count = autogenMapper.getCountByAutogen(autogen);
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Company的Autogen No. Type已存在");
                    return checker;
                }
            }
            checker.setSuccess(true);
            checker.setReturnStr("信息无误");
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean addAutogen(Autogen autogen) throws ServiceException {
        try {
            Date date = new Date();
            autogen.setCrtDate(date);
            autogen.setModDate(date);
            if (StringUtils.isEmpty(autogen.getAutTxNum())) {
                autogen.setAutTxNum(null);
            }
            Integer flag = autogenMapper.insertAutogen(autogen);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CompanyVo> getAllCompany() {
        List<CompanyVo> autogenCompanyList = autogenMapper.getAllCompany();
        return autogenCompanyList;
    }

    @Override
    public Autogen getAutogen(String coCode, String autTxType) {
        Autogen autogen = new Autogen();
        autogen.setAutTxType(autTxType);
        autogen.setCoCode(coCode);
        return autogenMapper.getAutogen(autogen);
    }

    @Override
    public boolean deleteAutogen(String coCode, String autTxType) throws ServiceException {
        Autogen autogen = new Autogen();
        autogen.setAutTxType(autTxType);
        autogen.setCoCode(coCode);
        try {
            autogenMapper.deleteAutogen(autogen);
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAutogen(Autogen autogen) throws ServiceException {
        try {
            Date date = new Date();
            autogen.setCrtDate(date);
            autogen.setModDate(date);
            if (StringUtils.isEmpty(autogen.getAutTxNum())) {
                autogen.setAutTxNum(null);
            }
            autogenMapper.updateAutogen(autogen);
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
