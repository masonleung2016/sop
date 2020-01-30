package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import sop.persistence.beans.QcImage;
import sop.persistence.beans.QcItemBase;
import sop.persistence.beans.QualityCheck;
import sop.persistence.beans.QualityCheckItem;
import sop.vo.PurchaseOrderDetailsVo;
import sop.vo.QualityCheckConditionVo;
import sop.vo.QualityCheckItemVo;
import sop.vo.QualityCheckVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:30
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface QcMapper extends BaseMapper<QualityCheck, Integer> {

    Integer getQcCountByPoNo(String poNo);

    Integer updateQualityCheck(QualityCheck qc); // new

    Integer insertQualityCheck(QualityCheck qc);

    void insertQualityCheckItem(QcItemBase qcItem);

    Integer insertQcItemSelect(QcItemBase qcItemVo);// new

    QualityCheck getQualityCheckByFkQcNo(String qcNo);

    QualityCheckItem getQcItemByQcItemVo(QcItemBase qcItemVo);

    Integer updateQcItem(QualityCheckItem qcItem);

    Integer updateQcItemSelect(QcItemBase qcItemVo);

    List<QualityCheckItemVo> getQualityCheckItemListByFkQcNo(String qcNo);

    List<QualityCheckItem> getQcItemsByQcNo(String qcNo);

    List<QualityCheckVo> findPageBreakByCondition(QualityCheckConditionVo vo,、RowBounds rb);

    Integer findNumberByCondition(QualityCheckConditionVo vo);

    Integer finishQc(String qcNo);

    QualityCheckItem getQcItemByUniqueKey(String qc2ItCatNoSuffix);

    void deleteQc1(String qcNo);

    void deleteQc2(String qcNo);

    List<QcImage> getQcImages();

}
