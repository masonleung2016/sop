package sop.services;

import java.util.List;

import org.springframework.ui.Model;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import sop.persistence.beans.QcItemBase;
import sop.persistence.beans.QualityCheck;
import sop.persistence.beans.QualityCheckItem;
import sop.vo.QualityCheckConditionVo;
import sop.vo.QualityCheckDetailsVo;
import sop.vo.QualityCheckVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:11
 * @Package: sop.services
 */


public interface QcServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "qcServiceMgr";

    boolean validatePoNo(String poNo);

    boolean deleteQc(String qcNo);

    boolean addQc(QualityCheck qc, QualityCheckDetailsVo currentQc);

    boolean updateQc(QualityCheck qc, QualityCheckDetailsVo currentQc);


    public QualityCheckDetailsVo getQualityCheckDetailsVoByFkQcNo(String qcNo);

    QualityCheckItem getQcItemByQcItemVo(QcItemBase qcItemVo);

    QualityCheckItem getQcItemByUniqueKey(String qc2ItCatNoSuffix);


    boolean updateQcItem(QualityCheckItem currentQcItem);

    boolean updateQcItemSelected(QcItemBase currentQcItem);

    Model getQcTemplateDetailsVo(Model model, QualityCheckItem currentQcItem);

    void exportQc(String qcNo) throws Exception;

    List<QualityCheckVo> searchQualityCheckVo(QualityCheckConditionVo vo);

    Integer searchQualityCheckVoNum(QualityCheckConditionVo vo);

    boolean finishQc(String qcNo);

    boolean checkExportQc(String encodeQcNo);
}
