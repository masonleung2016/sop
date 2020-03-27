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
import dwz.persistence.mapper.PackingBomMapper;
import sop.persistence.beans.PackingBom;
import sop.services.PackingBomServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:47
 * @Package: sop.services.impl
 */

@Service(PackingBomServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class PackingBomServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements PackingBomServiceMgr {

    @Autowired
    private PackingBomMapper packingBomMapper;

    @Override
    public List<PackingBom> getPackingBomListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<PackingBom> PackingBomList = packingBomMapper.getPackingBomByCondition(vo, rb);
        return PackingBomList;
    }

    @Override
    public void getPackingBomListNum(BaseConditionVO vo) {
        Integer count = packingBomMapper.getPackingBomNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkPackingBom(PackingBom packingBom) {
        Checker checker = new Checker();
        if (packingBom != null) {
            if (StringUtils.isBlank(packingBom.getPbNo())) {
                checker.setSuccess(false);
                checker.setReturnStr("PackingBom/No.不能为空,保存失败");
            } else {
                PackingBom pb = packingBomMapper.getPackingBomByNo(packingBom.getPbNo());
                if (pb != null) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该PackingBom/Deduction Code已存在");
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
    public boolean addPackingBom(PackingBom PackingBom) {
        try {
            Date date = new Date();
            PackingBom.setCrtDate(date);
            PackingBom.setModDate(date);
            Integer flag = packingBomMapper.insertPackingBom(PackingBom);
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
    public boolean updatePackingBom(PackingBom PackingBom) {
        try {
            Date date = new Date();
            PackingBom.setModDate(date);
            Integer flag = packingBomMapper.updatePackingBom(PackingBom);
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
    public boolean deletePackingBom(String pb) throws ServiceException {
        Integer flag = packingBomMapper.deletePackingBom(pb);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PackingBom> getAllPackingBoms() {
        List<PackingBom> packingBomList = packingBomMapper.getAllPackingBom();
        return packingBomList;
    }

    @Override
    public PackingBom getPackingBomByNo(String pbNo) {
        return packingBomMapper.getPackingBomByNo(pbNo);
    }
}
