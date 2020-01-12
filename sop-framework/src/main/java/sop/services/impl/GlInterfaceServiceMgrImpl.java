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
import dwz.persistence.mapper.GlInterfaceMapper;
import sop.persistence.beans.GlInterface;
import sop.services.GlInterfaceServiceMgr;
import sop.vo.GlInterfaceVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:35
 * @Package: sop.services.impl
 */


@Service(GlInterfaceServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class GlInterfaceServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements GlInterfaceServiceMgr {

    @Autowired
    private GlInterfaceMapper glinterfaceMapper;

    @Override
    public List<GlInterfaceVo> getGlInterfaceListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<GlInterfaceVo> glinterfaceVoList = glinterfaceMapper.getGlInterfaceListByCondition(vo, rb);
        return glinterfaceVoList;
    }

    @Override
    public void getGlInterfaceListNum(BaseConditionVO vo) {
        Integer count = glinterfaceMapper.getGlInterfaceListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public GlInterface getfkGlInfCodeById(String fkGlInfCode) {
        GlInterface currentGlInterface = glinterfaceMapper.getfkGlInfCodeByFkGlInfCode(fkGlInfCode);
        return currentGlInterface;
    }

    @Override
    public boolean updateGlInterface(GlInterface currentGlInterface) {
        try {
            Date date = new Date();
            currentGlInterface.setModDate(date);
            glinterfaceMapper.updateGlInterface(currentGlInterface);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Checker checkGlInterface(GlInterface currentGlInterface) {
        Checker checker = new Checker();
        if (currentGlInterface != null) {
            if (currentGlInterface.getGlInfCode() == null || currentGlInterface.getGlInfCode().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("G/L Interface Code不能为空,保存失败");
            } else {
                Integer count = glinterfaceMapper.getCountByFkGlInfCode(currentGlInterface.getGlInfCode());
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("G/L Interface Code已存在,请使用其他ID");
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
    public boolean insertGlInterface(GlInterface currentGlInterface) {
        try {
            Date date = new Date();
            currentGlInterface.setCrtDate(date);
            currentGlInterface.setModDate(date);
            Integer flag = glinterfaceMapper.insertGlInterface(currentGlInterface);
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
    public boolean deleteGlInterface(String fkGlInfCode) {
        Integer flag = glinterfaceMapper.deleteGlInterface(fkGlInfCode);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Checker checkGlInterfaceDecimal(GlInterface currentGlInterface) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currentGlInterface.getRevNo() != null) {
            if (currentGlInterface.getRevNo().doubleValue() < -99 || currentGlInterface.getRevNo().doubleValue() > 99) {
                checker.setSuccess(false);
                checker.setReturnStr("Rev no必须在[-99, 99]范围内");
                return checker;
            }
        }
        return checker;
    }
}
