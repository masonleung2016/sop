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
import dwz.persistence.mapper.PackingDetailMapper;
import sop.persistence.beans.PackingDetail;
import sop.services.PackingDetailServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:48
 * @Package: sop.services.impl
 */


@Service(PackingDetailServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class PackingDetailServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements PackingDetailServiceMgr {

    @Autowired
    private PackingDetailMapper packingDetailMapper;

    @Override
    public List<PackingDetail> getPackingDetailListByCondition(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<PackingDetail> PackingDetailList = packingDetailMapper.getPackingDetailByCondition(vo, rb);
        return PackingDetailList;
    }

    @Override
    public void getPackingDetailListNum(BaseConditionVO vo) {
        Integer count = packingDetailMapper.getPackingDetailNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public Checker checkPackingDetail(PackingDetail packingDetail) {
        Checker checker = new Checker();
        if (packingDetail != null) {
            if (StringUtils.isBlank(packingDetail.getPdNo())) {
                checker.setSuccess(false);
                checker.setReturnStr("PackingDetail/No.不能为空,保存失败");
            } else {
                PackingDetail pb = packingDetailMapper.getPackingDetailByNo(packingDetail.getPdNo());
                if (pb != null) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该PackingDetail 已存在");
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
    public boolean addPackingDetail(PackingDetail PackingDetail) {
        try {
            Date date = new Date();
            PackingDetail.setCrtDate(date);
            PackingDetail.setModDate(date);
            Integer flag = packingDetailMapper.insertPackingDetail(PackingDetail);
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
    public boolean updatePackingDetail(PackingDetail PackingDetail) {
        try {
            Date date = new Date();
            PackingDetail.setModDate(date);
            Integer flag = packingDetailMapper.updatePackingDetail(PackingDetail);
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
    public boolean deletePackingDetail(String pb) throws ServiceException {
        Integer flag = packingDetailMapper.deletePackingDetail(pb);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<PackingDetail> getAllPackingDetails() {
        List<PackingDetail> packingDetailList = packingDetailMapper.getAllPackingDetail();
        return packingDetailList;
    }


    @Override
    public PackingDetail getPackingDetailByNo(String pdNo) {
        return packingDetailMapper.getPackingDetailByNo(pdNo);
    }
}
