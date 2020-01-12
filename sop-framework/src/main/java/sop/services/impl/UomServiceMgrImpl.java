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
import dwz.persistence.mapper.UomMapper;
import sop.persistence.beans.Uom;
import sop.services.UomServiceMgr;
import sop.vo.UomVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:56
 * @Package: sop.services.impl
 */


@Service(UomServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class UomServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements UomServiceMgr {

    @Autowired
    private UomMapper uomMapper;

    @Override
    public List<UomVo> getUomListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<UomVo> uomVoList = uomMapper.getUomListByCondition(vo, rb);
        return uomVoList;
    }

    @Override
    public void getUomListNum(BaseConditionVO vo) {
        Integer count = uomMapper.getUomListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    //
//	@Override
//	public Company getCompanyById(String fkCoCode) {
//		Company currentCom = comMapper.getCompanyByFkCoCode(fkCoCode);
//		return currentCom;
//	}
//
//	@Override
//	public boolean updateCompany(Company currentCom) {
//		try {
//			comMapper.updateCompany(currentCom);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
    @Override
    public Checker checkUom(Uom uom) {
        Checker checker = new Checker();
        if (uom != null) {
            if (uom.getuCode() == null || uom.getuCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("UOM不能为空,保存失败");
            } else {
                Integer count = uomMapper.getCountByFkuCode(uom.getuCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该UOM已存在");
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
    public boolean addUom(Uom uom) throws ServiceException {
        try {
            Date date = new Date();
            uom.setCrtDate(date);
            uom.setModDate(date);
            Integer flag = uomMapper.insertUom(uom);
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
    public Uom getUomByFkuCode(String fkuCode) {
        Uom currentUom = uomMapper.getUomByFkuCode(fkuCode);
        return currentUom;
    }

    @Override
    public boolean updateUom(Uom uom) throws ServiceException {
        try {
            Date date = new Date();
            uom.setModDate(date);
            Integer flag = uomMapper.updateUom(uom);
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
    public boolean deleteUom(String fkuCode) throws ServiceException {
        Integer flag = uomMapper.deleteUom(fkuCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
}
