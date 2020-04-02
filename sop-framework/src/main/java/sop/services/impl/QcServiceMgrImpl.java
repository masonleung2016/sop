package sop.services.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import dwz.common.util.StringUtils;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.persistence.mapper.ChargeMapper;
import dwz.persistence.mapper.CurrencyMapper;
import dwz.persistence.mapper.FactoryMapper;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.PayTermMapper;
import dwz.persistence.mapper.PoMapper;
import dwz.persistence.mapper.QcMapper;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.PurchaseOrder;
import sop.persistence.beans.PurchaseOrderItem;
import sop.persistence.beans.QcItemBase;
import sop.persistence.beans.QualityCheck;
import sop.persistence.beans.QualityCheckItem;
import sop.services.ItemServiceMgr;
import sop.services.PoServiceMgr;
import sop.services.QcServiceMgr;
import sop.services.SaleOrderServiceMgr;
import sop.utils.ColAndRow;
import sop.utils.Common;
import sop.utils.ExcelManager;
import sop.utils.PoiUtil;
import sop.vo.PurchaseOrderDetailsVo;
import sop.vo.QualityCheckConditionVo;
import sop.vo.QualityCheckDetailsVo;
import sop.vo.QualityCheckItemVo;
import sop.vo.QualityCheckVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:51
 * @Package: sop.services.impl
 */

@Service(QcServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class QcServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements QcServiceMgr {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QcMapper qcMapper;

    @Autowired
    private PoMapper poMapper;

    @Autowired
    private PayTermMapper payTermMapper;

    @Autowired
    private CurrencyMapper currMapper;

    @Autowired
    private FactoryMapper factoryMapper;

    @Autowired
    private ChargeMapper chargeMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SaleOrderServiceMgr saleOrderMgr;

    @Autowired
    private PoServiceMgr poMgr;

    @Autowired
    private ItemServiceMgr itemMgr;

    @Override
    public boolean validatePoNo(String poNo) {
        Integer count = qcMapper.getQcCountByPoNo(poNo);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateQc(QualityCheck qc, QualityCheckDetailsVo currentQc) {
        Integer affect = qcMapper.updateQualityCheck(qc);

        if (currentQc != null && affect > 0) {
            List<QualityCheckItemVo> items = currentQc.getQcItems();
            if (items != null) {
                Iterator<QualityCheckItemVo> iter = items.iterator();
                while (iter.hasNext()) {
                    QualityCheckItemVo qcItem = iter.next();
                    String qc2PartPic = itemMapper.getPartPicByItCatNoSuffix(qcItem.getQc2ItCatNoSuffix());
                    qcItem.setQc2PartPic(qc2PartPic);
                    String data = currentQc.getQcItemCheckListByItemNo(qcItem.getQc2ItCatNoSuffix());
                    qcItem.setQc2Cl(data);
                    logger.debug("check list data:{}", data);
                    qcMapper.updateQcItemSelect(qcItem);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addQc(QualityCheck qc, QualityCheckDetailsVo currentQc) {
        Integer qcInsertReturn = qcMapper.insertQualityCheck(qc);
        if (currentQc != null && qcInsertReturn > 0) {
            List<QualityCheckItemVo> items = currentQc.getQcItems();
            if (items != null) {
                Iterator<QualityCheckItemVo> iter = items.iterator();
                while (iter.hasNext()) {
                    QualityCheckItemVo qcItem = iter.next();
                    String qc2PartPic = itemMapper.getPartPicByItCatNoSuffix(qcItem.getQc2ItCatNoSuffix());
                    qcItem.setQc2PartPic(qc2PartPic);
                    String data = currentQc.getQcItemCheckListByItemNo(qcItem.getQc2ItCatNoSuffix());
                    qcItem.setQc2Cl(data);
                    logger.debug("check list data:{}", data);
                    qcMapper.insertQualityCheckItem(qcItem);
                }
            }
            return true;
        }
        return false;
    }

    public QualityCheckDetailsVo getQualityCheckDetailsVoByFkQcNo(String qcNo) {
        QualityCheckDetailsVo detail = null;
        QualityCheck qualityCheck = qcMapper.getQualityCheckByFkQcNo(qcNo);
        if (qualityCheck != null) {
            detail = new QualityCheckDetailsVo(qualityCheck);
            detail.setNew(false);
            List<QualityCheckItemVo> qualityCheckItemList = qcMapper.getQualityCheckItemListByFkQcNo(qcNo);
            if (qualityCheckItemList != null) {
                detail.setQcItems(qualityCheckItemList);
                for (QualityCheckItemVo vo : qualityCheckItemList) {
                    detail.getQcItemCheckListMap().put(vo.getQc2ItCatNoSuffix(), vo.getQc2Cl());
                }
            }
        }
        return detail;
    }

    @Override
    public QualityCheckItem getQcItemByQcItemVo(QcItemBase qcItemVo) {
        QualityCheckItem qcItem = qcMapper.getQcItemByQcItemVo(qcItemVo);
        return qcItem;
    }

    @Override
    public boolean updateQcItem(QualityCheckItem currentQcItem) {
        Date date = new Date();
        currentQcItem.setModDate(date);
        Integer flag = qcMapper.updateQcItem(currentQcItem);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateQcItemSelected(QcItemBase currentQcItem) {
        Date date = new Date();
        currentQcItem.setModDate(date);
        Integer flag = qcMapper.updateQcItemSelect(currentQcItem);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Model getQcTemplateDetailsVo(Model model,
                                        QualityCheckItem currentQcItem) {
        //BOM
        String detailVoStr = currentQcItem.getQc2ItDetails();
        if (detailVoStr == null || !detailVoStr.startsWith("{")) {
            detailVoStr = "{}";
        }
        Map<String, Object> detailVo = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            detailVo = mapper.readValue(detailVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("detailVo", detailVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //部件图片
        String partPicStr = currentQcItem.getQc2PartPic();
        if (partPicStr != null) {
            Map<String, Object> currentPartPic = new LinkedHashMap<String, Object>();
            ObjectMapper partPicMapper = new ObjectMapper();
            try {
                //把detail data json string 转成map对象
                currentPartPic = partPicMapper.readValue(partPicStr, new TypeReference<LinkedHashMap<String, Object>>() {
                });
                model.addAttribute("currentPartPic", currentPartPic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //QC图片
        String qcPicStr = currentQcItem.getQc2Pic();
        if (qcPicStr != null) {
            Map<String, Object> currentQcPic = new LinkedHashMap<String, Object>();
            ObjectMapper qcPicMapper = new ObjectMapper();
            try {
                //把detail data json string 转成map对象
                currentQcPic = qcPicMapper.readValue(qcPicStr, new TypeReference<LinkedHashMap<String, Object>>() {
                });
                model.addAttribute("currentQcPic", currentQcPic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //BZMX
        String bzmxVoStr = currentQcItem.getQc2Bzmx();
        if (bzmxVoStr == null || !bzmxVoStr.startsWith("{")) {
            bzmxVoStr = "{}";
        }
        Map<String, Object> bzmxVo = new HashMap<String, Object>();
        ObjectMapper bzmxMapper = new ObjectMapper();
        try {
            bzmxVo = bzmxMapper.readValue(bzmxVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("bzmxVo", bzmxVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //BZ
        String bzVoStr = currentQcItem.getQc2Bz();
        if (bzVoStr == null || !bzVoStr.startsWith("{")) {
            bzVoStr = "{}";
        }
        Map<String, Object> bzVo = new HashMap<String, Object>();
        ObjectMapper bzMapper = new ObjectMapper();
        try {
            bzVo = bzMapper.readValue(bzVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("bzVo", bzVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //BZWLQD
        String bzwlqdVoStr = currentQcItem.getQc2Bzwlqd();
        if (bzwlqdVoStr == null || !bzwlqdVoStr.startsWith("{")) {
            bzwlqdVoStr = "{}";
        }
        Map<String, Object> bzwlqdVo = new HashMap<String, Object>();
        ObjectMapper bzwlqdMapper = new ObjectMapper();
        try {
            bzwlqdVo = bzwlqdMapper.readValue(bzwlqdVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("bzwlqdVo", bzwlqdVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //CP
        String cpVoStr = currentQcItem.getQc2Cp();
        if (cpVoStr == null || !cpVoStr.startsWith("{")) {
            cpVoStr = "{}";
        }
        Map<String, Object> cpVo = new HashMap<String, Object>();
        ObjectMapper cpMapper = new ObjectMapper();
        try {
            cpVo = cpMapper.readValue(cpVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("cpVo", cpVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //DHGJ
        String dhgjVoStr = currentQcItem.getQc2Dhgj();
        if (dhgjVoStr == null || !dhgjVoStr.startsWith("{")) {
            dhgjVoStr = "{}";
        }
        Map<String, Object> dhgjVo = new HashMap<String, Object>();
        ObjectMapper dhgjMapper = new ObjectMapper();
        try {
            dhgjVo = dhgjMapper.readValue(dhgjVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("dhgjVo", dhgjVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //YHZJ
        String yhzjVoStr = currentQcItem.getQc2Yhzj();
        if (yhzjVoStr == null || !yhzjVoStr.startsWith("{")) {
            yhzjVoStr = "{}";
        }
        Map<String, Object> yhzjVo = new HashMap<String, Object>();
        ObjectMapper yhzjMapper = new ObjectMapper();
        try {
            yhzjVo = yhzjMapper.readValue(yhzjVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            model.addAttribute("yhzjVo", yhzjVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //SFCSBG
        if (currentQcItem.isQc2HaveSfcsbg()) {
            String sfcsbgVoStr = currentQcItem.getQc2Sfcsbg();
            if (sfcsbgVoStr == null || !sfcsbgVoStr.startsWith("{")) {
                sfcsbgVoStr = "{}";
            }
            Map<String, Object> sfcsbgVo = new HashMap<String, Object>();
            ObjectMapper sfcsbgMapper = new ObjectMapper();
            try {
                sfcsbgVo = sfcsbgMapper.readValue(sfcsbgVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("sfcsbgVo", sfcsbgVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //YSJC
        if (currentQcItem.isQc2HaveYsjc()) {
            String ysjcVoStr = currentQcItem.getQc2Ysjc();
            if (ysjcVoStr == null || !ysjcVoStr.startsWith("{")) {
                ysjcVoStr = "{}";
            }
            Map<String, Object> ysjcVo = new HashMap<String, Object>();
            ObjectMapper ysjcMapper = new ObjectMapper();
            try {
                ysjcVo = ysjcMapper.readValue(ysjcVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("ysjcVo", ysjcVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //FYHSM
        if (currentQcItem.isQc2HaveFyhsm()) {
            String fyhsmVoStr = currentQcItem.getQc2Fyhsm();
            if (fyhsmVoStr == null || !fyhsmVoStr.startsWith("{")) {
                fyhsmVoStr = "{}";
            }
            Map<String, Object> fyhsmVo = new HashMap<String, Object>();
            ObjectMapper fyhsmMapper = new ObjectMapper();
            try {
                fyhsmVo = fyhsmMapper.readValue(fyhsmVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("fyhsmVo", fyhsmVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //ZCJYRBG
        if (currentQcItem.isQc2HaveZcjyrbg()) {
            String zcjyrbgVoStr = currentQcItem.getQc2Zcjyrbg();
            if (zcjyrbgVoStr == null || !zcjyrbgVoStr.startsWith("{")) {
                zcjyrbgVoStr = "{}";
            }
            Map<String, Object> zcjyrbgVo = new HashMap<String, Object>();
            ObjectMapper zcjyrbgMapper = new ObjectMapper();
            try {
                zcjyrbgVo = zcjyrbgMapper.readValue(zcjyrbgVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("zcjyrbgVo", zcjyrbgVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //SCJHKZ
        if (currentQcItem.isQc2HaveScjhkz()) {
            String scjhkzVoStr = currentQcItem.getQc2Scjhkz();
            if (scjhkzVoStr == null || !scjhkzVoStr.startsWith("{")) {
                scjhkzVoStr = "{}";
            }
            Map<String, Object> scjhkzVo = new HashMap<String, Object>();
            ObjectMapper scjhkzMapper = new ObjectMapper();
            try {
                scjhkzVo = scjhkzMapper.readValue(scjhkzVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("scjhkzVo", scjhkzVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //GFX
        if (currentQcItem.isQc2HaveGfx()) {
            String gfxVoStr = currentQcItem.getQc2Gfx();
            if (gfxVoStr == null || !gfxVoStr.startsWith("{")) {
                gfxVoStr = "{}";
            }
            Map<String, Object> gfxVo = new HashMap<String, Object>();
            ObjectMapper gfxMapper = new ObjectMapper();
            try {
                gfxVo = gfxMapper.readValue(gfxVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("gfxVo", gfxVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //CLSJ
        if (currentQcItem.isQc2HaveClsj()) {
            String clsjVoStr = currentQcItem.getQc2Clsj();
            if (clsjVoStr == null || !clsjVoStr.startsWith("{")) {
                clsjVoStr = "{}";
            }
            Map<String, Object> clsjVo = new HashMap<String, Object>();
            ObjectMapper clsjMapper = new ObjectMapper();
            try {
                clsjVo = clsjMapper.readValue(clsjVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("clsjVo", clsjVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //WPCGZ
        if (currentQcItem.isQc2HaveWpcgz()) {
            String wpcgzVoStr = currentQcItem.getQc2Wpcgz();
            if (wpcgzVoStr == null || !wpcgzVoStr.startsWith("{")) {
                wpcgzVoStr = "{}";
            }
            Map<String, Object> wpcgzVo = new HashMap<String, Object>();
            ObjectMapper wpcgzMapper = new ObjectMapper();
            try {
                wpcgzVo = wpcgzMapper.readValue(wpcgzVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("wpcgzVo", wpcgzVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //CSYQ
        if (currentQcItem.isQc2HaveCsyq()) {
            String csyqVoStr = currentQcItem.getQc2Csyq();
            if (csyqVoStr == null || !csyqVoStr.startsWith("{")) {
                csyqVoStr = "{}";
            }
            Map<String, Object> csyqVo = new HashMap<String, Object>();
            ObjectMapper csyqMapper = new ObjectMapper();
            try {
                csyqVo = csyqMapper.readValue(csyqVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("csyqVo", csyqVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //CC
        if (currentQcItem.isQc2HaveCc()) {
            String ccVoStr = currentQcItem.getQc2Cc();
            if (ccVoStr == null || !ccVoStr.startsWith("{")) {
                ccVoStr = "{}";
            }
            Map<String, Object> ccVo = new HashMap<String, Object>();
            ObjectMapper ccMapper = new ObjectMapper();
            try {
                ccVo = ccMapper.readValue(ccVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                model.addAttribute("ccVo", ccVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    private void updateHeaderInfo(String prefix, QualityCheckItem item, Map<String, Object> detailVo) {
        detailVo.put(prefix + "poNo", item.getQc2No());
        detailVo.put(prefix + "itemNo", item.getQc2ItCatNoSuffix());
        detailVo.put(prefix + "itemNameEng", item.getQc2ItName());
    }

    @Override
    public void exportQc(String qcNo) throws Exception {
        List<QualityCheckItem> qcItems = qcMapper.getQcItemsByQcNo(qcNo);
        for (QualityCheckItem qualityCheckItem : qcItems) {
            logger.info("==================================handleQcItem, {}", qualityCheckItem);
            handleQcItem(qualityCheckItem);
            logger.info("==================================exportQcItem2");
            exportQcItem2(qualityCheckItem);
            logger.info("==================================handleQcItemPic");
            handleQcItemPic(qualityCheckItem);
        }
    }

    private void exportQcItem2(QualityCheckItem qualityCheckItem) throws Exception {
        Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
        String baselocation = tempPropertiesMap.get("baselocation");
        String encodeQcNo = Base64.encode(qualityCheckItem.getQc2No(), "utf-8");
        String itCatNoSuffix = qualityCheckItem.getQc2ItCatNoSuffix();
        File file = new File(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
        if (!file.exists()) {
            logger.error("File not found {}", file.getAbsolutePath());
            return;
        }
        InputStream inputStream = new FileInputStream(file);
        if (inputStream != null) {
            Workbook workbook = new HSSFWorkbook(inputStream);
            Map<String, Object> detailVo = new HashMap<String, Object>();
            //BOM
            String qcTemplateName = itemMgr.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
            if (qcTemplateName != null && qcTemplateName.trim().endsWith("")) {
                String detailVoStr = qualityCheckItem.getQc2ItDetails();
                if (detailVoStr == null || !detailVoStr.startsWith("{")) {
                    detailVoStr = "{}";
                }
                ObjectMapper mapper = new ObjectMapper();
                try {
                    //把detail data json string 转成map对象
                    detailVo = mapper.readValue(detailVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    updateHeaderInfo("bom", qualityCheckItem, detailVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //BZMX
            String bzmxVoStr = qualityCheckItem.getQc2Bzmx();
            if (bzmxVoStr == null || !bzmxVoStr.startsWith("{")) {
                bzmxVoStr = "{}";
            }
            Map<String, Object> bzmxVo = new HashMap<String, Object>();
            ObjectMapper bzmxMapper = new ObjectMapper();
            try {
                bzmxVo = bzmxMapper.readValue(bzmxVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                detailVo.putAll(bzmxVo);
                updateHeaderInfo("bzmx", qualityCheckItem, detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //BZ
            String bzVoStr = qualityCheckItem.getQc2Bz();
            if (bzVoStr == null || !bzVoStr.startsWith("{")) {
                bzVoStr = "{}";
            }
            Map<String, Object> bzVo = new HashMap<String, Object>();
            ObjectMapper bzMapper = new ObjectMapper();
            try {
                bzVo = bzMapper.readValue(bzVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                detailVo.putAll(bzVo);
                updateHeaderInfo("bz", qualityCheckItem, detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //BZWLQD
            String bzwlqdVoStr = qualityCheckItem.getQc2Bzwlqd();
            if (bzwlqdVoStr == null || !bzwlqdVoStr.startsWith("{")) {
                bzwlqdVoStr = "{}";
            }
            Map<String, Object> bzwlqdVo = new HashMap<String, Object>();
            ObjectMapper bzwlqdMapper = new ObjectMapper();
            try {
                bzwlqdVo = bzwlqdMapper.readValue(bzwlqdVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                detailVo.putAll(bzwlqdVo);
                updateHeaderInfo("bzwlqd", qualityCheckItem, detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //CP
            String cpVoStr = qualityCheckItem.getQc2Cp();
            if (cpVoStr == null || !cpVoStr.startsWith("{")) {
                cpVoStr = "{}";
            }
            Map<String, Object> cpVo = new HashMap<String, Object>();
            ObjectMapper cpMapper = new ObjectMapper();
            try {
                cpVo = cpMapper.readValue(cpVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                detailVo.putAll(cpVo);
                updateHeaderInfo("cp", qualityCheckItem, detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //DHGJ
            String dhgjVoStr = qualityCheckItem.getQc2Dhgj();
            if (dhgjVoStr == null || !dhgjVoStr.startsWith("{")) {
                dhgjVoStr = "{}";
            }
            Map<String, Object> dhgjVo = new HashMap<String, Object>();
            ObjectMapper dhgjMapper = new ObjectMapper();
            try {
                dhgjVo = dhgjMapper.readValue(dhgjVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                detailVo.putAll(dhgjVo);
                updateHeaderInfo("dhgj", qualityCheckItem, detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //YHZJ
            String yhzjVoStr = qualityCheckItem.getQc2Yhzj();
            if (yhzjVoStr == null || !yhzjVoStr.startsWith("{")) {
                yhzjVoStr = "{}";
            }
            Map<String, Object> yhzjVo = new HashMap<String, Object>();
            ObjectMapper yhzjMapper = new ObjectMapper();
            try {
                yhzjVo = yhzjMapper.readValue(yhzjVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                detailVo.putAll(yhzjVo);
                updateHeaderInfo("yhzj", qualityCheckItem, detailVo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //SFCSBG
            if (qualityCheckItem.isQc2HaveSfcsbg()) {
                String sfcsbgVoStr = qualityCheckItem.getQc2Sfcsbg();
                if (sfcsbgVoStr == null || !sfcsbgVoStr.startsWith("{")) {
                    sfcsbgVoStr = "{}";
                }
                Map<String, Object> sfcsbgVo = new HashMap<String, Object>();
                ObjectMapper sfcsbgMapper = new ObjectMapper();
                try {
                    sfcsbgVo = sfcsbgMapper.readValue(sfcsbgVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(sfcsbgVo);
                    updateHeaderInfo("sfcsbg", qualityCheckItem, detailVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //YSJC
            if (qualityCheckItem.isQc2HaveYsjc()) {
                String ysjcVoStr = qualityCheckItem.getQc2Ysjc();
                if (ysjcVoStr == null || !ysjcVoStr.startsWith("{")) {
                    ysjcVoStr = "{}";
                }
                Map<String, Object> ysjcVo = new HashMap<String, Object>();
                ObjectMapper ysjcMapper = new ObjectMapper();
                try {
                    ysjcVo = ysjcMapper.readValue(ysjcVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(ysjcVo);
                    updateHeaderInfo("ysjc", qualityCheckItem, detailVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //FYHSM
            if (qualityCheckItem.isQc2HaveFyhsm()) {
                String fyhsmVoStr = qualityCheckItem.getQc2Fyhsm();
                if (fyhsmVoStr == null || !fyhsmVoStr.startsWith("{")) {
                    fyhsmVoStr = "{}";
                }
                Map<String, Object> fyhsmVo = new HashMap<String, Object>();
                ObjectMapper fyhsmMapper = new ObjectMapper();
                try {
                    fyhsmVo = fyhsmMapper.readValue(fyhsmVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(fyhsmVo);
                    updateHeaderInfo("fyhsm", qualityCheckItem, detailVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //ZCJYRBG
            if (qualityCheckItem.isQc2HaveZcjyrbg()) {
                String zcjyrbgVoStr = qualityCheckItem.getQc2Zcjyrbg();
                if (zcjyrbgVoStr == null || !zcjyrbgVoStr.startsWith("{")) {
                    zcjyrbgVoStr = "{}";
                }
                Map<String, Object> zcjyrbgVo = new HashMap<String, Object>();
                ObjectMapper zcjyrbgMapper = new ObjectMapper();
                try {
                    zcjyrbgVo = zcjyrbgMapper.readValue(zcjyrbgVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(zcjyrbgVo);
                    updateHeaderInfo("zcjyrbg", qualityCheckItem, detailVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //SCJHKZ
            if (qualityCheckItem.isQc2HaveScjhkz()) {
                String scjhkzVoStr = qualityCheckItem.getQc2Scjhkz();
                if (scjhkzVoStr == null || !scjhkzVoStr.startsWith("{")) {
                    scjhkzVoStr = "{}";
                }
                Map<String, Object> scjhkzVo = new HashMap<String, Object>();
                ObjectMapper scjhkzMapper = new ObjectMapper();
                try {
                    scjhkzVo = scjhkzMapper.readValue(scjhkzVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(scjhkzVo);
                    updateHeaderInfo("scjhkz", qualityCheckItem, detailVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //GFX
            if (qualityCheckItem.isQc2HaveGfx()) {
                String gfxVoStr = qualityCheckItem.getQc2Gfx();
                if (gfxVoStr == null || !gfxVoStr.startsWith("{")) {
                    gfxVoStr = "{}";
                }
                Map<String, Object> gfxVo = new HashMap<String, Object>();
                ObjectMapper gfxMapper = new ObjectMapper();
                try {
                    gfxVo = gfxMapper.readValue(gfxVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(gfxVo);
                    updateHeaderInfo("gfx", qualityCheckItem, detailVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //CLSJ
            if (qualityCheckItem.isQc2HaveClsj()) {
                String clsjVoStr = qualityCheckItem.getQc2Clsj();
                if (clsjVoStr == null || !clsjVoStr.startsWith("{")) {
                    clsjVoStr = "{}";
                }
                Map<String, Object> clsjVo = new HashMap<String, Object>();
                ObjectMapper clsjMapper = new ObjectMapper();
                try {
                    clsjVo = clsjMapper.readValue(clsjVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(clsjVo);
                    updateHeaderInfo("clsj", qualityCheckItem, detailVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //WPCGZ
            if (qualityCheckItem.isQc2HaveWpcgz()) {
                String wpcgzVoStr = qualityCheckItem.getQc2Wpcgz();
                if (wpcgzVoStr == null || !wpcgzVoStr.startsWith("{")) {
                    wpcgzVoStr = "{}";
                }
                Map<String, Object> wpcgzVo = new HashMap<String, Object>();
                ObjectMapper wpcgzMapper = new ObjectMapper();
                try {
                    wpcgzVo = wpcgzMapper.readValue(wpcgzVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(wpcgzVo);
                    updateHeaderInfo("wpcgz", qualityCheckItem, detailVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //CSYQ
            if (qualityCheckItem.isQc2HaveCsyq()) {
                String csyqVoStr = qualityCheckItem.getQc2Csyq();
                if (csyqVoStr == null || !csyqVoStr.startsWith("{")) {
                    csyqVoStr = "{}";
                }
                Map<String, Object> csyqVo = new HashMap<String, Object>();
                ObjectMapper csyqMapper = new ObjectMapper();
                try {
                    csyqVo = csyqMapper.readValue(csyqVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(csyqVo);
                    updateHeaderInfo("csyq", qualityCheckItem, detailVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //CC
            if (qualityCheckItem.isQc2HaveCc()) {
                String ccVoStr = qualityCheckItem.getQc2Cc();
                if (ccVoStr == null || !ccVoStr.startsWith("{")) {
                    ccVoStr = "{}";
                }
                Map<String, Object> ccVo = new HashMap<String, Object>();
                ObjectMapper ccMapper = new ObjectMapper();
                try {
                    ccVo = ccMapper.readValue(ccVoStr, new TypeReference<HashMap<String, Object>>() {
                    });
                    detailVo.putAll(ccVo);
                    updateHeaderInfo("cc", qualityCheckItem, detailVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ExcelManager.importToExcel(workbook, detailVo);

            FileOutputStream fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }

    }

    private void handleQcItem(QualityCheckItem qualityCheckItem) throws Exception {
        String encodeQcNo = Base64.encode(qualityCheckItem.getQc2No(), "utf-8");
        String itCatNoSuffix = qualityCheckItem.getQc2ItCatNoSuffix();
        Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
        String baselocation = tempPropertiesMap.get("baselocation");

        POIFSFileSystem qcItemFs;
        qcItemFs = new POIFSFileSystem(ExcelManager.class.getClassLoader().getResourceAsStream("qctemplates/QCTemplate.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(qcItemFs);

        //BOM
        String qcTemplateName = itemMgr.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
        if (StringUtils.isNotBlank(qcTemplateName)) {
            List<String> removeBomList = getRemoveBomList(qcTemplateName);
            removeUnusedBoms(wb, removeBomList);
        } else {
            throw new Exception("qcTemplateName is not found !" + qcTemplateName);
        }

        //SFCSBG
        if (!qualityCheckItem.isQc2HaveSfcsbg()) {
            removeSheetBySheetName(wb, "水分测试报告");
        }

        //YSJC
        if (!qualityCheckItem.isQc2HaveYsjc()) {
            removeSheetBySheetName(wb, "颜色对比检查表");
        }

        //FYHSM
        if (!qualityCheckItem.isQc2HaveFyhsm()) {
            removeSheetBySheetName(wb, "复验货申明");
        }

        //ZCJYRBG
        if (!qualityCheckItem.isQc2HaveZcjyrbg()) {
            removeSheetBySheetName(wb, "QC制程检验日报表");
        }

        //SCJHKZ
        if (!qualityCheckItem.isQc2HaveScjhkz()) {
            removeSheetBySheetName(wb, "生产计划控制表");
        }

        //GFX
        if (!qualityCheckItem.isQc2HaveGfx()) {
            removeSheetBySheetName(wb, "高风险");
        }

        //CLSJ
        if (!qualityCheckItem.isQc2HaveClsj()) {
            removeSheetBySheetName(wb, "材料送检记录");
        }

        //WPCGZ
        if (!qualityCheckItem.isQc2HaveWpcgz()) {
            removeSheetBySheetName(wb, "WPC地板检验规章");
        }

        //CSYQ
        if (!qualityCheckItem.isQc2HaveCsyq()) {
            removeSheetBySheetName(wb, "测试要求");
        }

        //CC
        if (!qualityCheckItem.isQc2HaveCc()) {
            removeSheetBySheetName(wb, "尺寸检测表");
        }
        Common.newQcExcelFile(baselocation + "/" + encodeQcNo, baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
        FileOutputStream fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    private void removeUnusedBoms(HSSFWorkbook wb, List<String> removeBomList) {
        if (removeBomList != null && removeBomList.size() > 0) {
            for (String sheetName : removeBomList) {
                removeSheetBySheetName(wb, sheetName);
            }
        }
    }

    private void removeSheetBySheetName(HSSFWorkbook wb, String sheetName) {
        int index = wb.getSheetIndex(sheetName);
        wb.removeSheetAt(index);
    }

    private void handleQcItemPic(QualityCheckItem qualityCheckItem) throws Exception {
        String encodeQcNo = Base64.encode(qualityCheckItem.getQc2No(), "utf-8");
        String itCatNoSuffix = qualityCheckItem.getQc2ItCatNoSuffix();
        Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
        String baselocation = tempPropertiesMap.get("baselocation");
        String directory = baselocation + "/" + encodeQcNo + "/";
        String savePath = directory + itCatNoSuffix + ".xls";

        File file = new File(savePath);
        if (!file.exists()) {
            logger.error("File not found {}", file.getAbsolutePath());
            return;
        }
        Map<String, String> ftpPropertiesMap = Common.getProperties("/ftpclient.properties");
        String picBaseLocation = ftpPropertiesMap.get("baselocation");

        //BOM
        String qcTemplateName = itemMgr.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
        if (qcTemplateName != null && qcTemplateName.trim().endsWith("")) {
            List<ColAndRow> picColAndRow = getBomColAndRowByTemplateName(qcTemplateName);
            POIFSFileSystem bomfs;
            FileOutputStream fileOut = null;
            try {
                bomfs = new POIFSFileSystem(new FileInputStream(savePath));
                HSSFWorkbook bomwb = new HSSFWorkbook(bomfs);
                String sheetName = getBomTemplateSheetName(qcTemplateName);
                HSSFSheet bomst = bomwb.getSheet(sheetName);
                if (bomwb != null) {
                    if (picColAndRow != null && picColAndRow.size() > 0) {
                        for (ColAndRow colAndRow : picColAndRow) {
                            String cellValue = PoiUtil.getCellValue(bomst, colAndRow.getStartCol(), colAndRow.getStartRow());
                            if (cellValue != null && !cellValue.trim().equals("")) {
                                PoiUtil.stringToImage(picBaseLocation + "/PART-PHOTO-JPEG/" + qualityCheckItem.getQc2ItCat() + "/" + qualityCheckItem.getQc2ItNo() + "/" + qualityCheckItem.getQc2ItSuffix() + "/" + cellValue, bomwb, bomst, colAndRow.getStartCol(), colAndRow.getStartRow(), colAndRow.getEndCol(), colAndRow.getEndRow());
                            }
                        }
                    }
                }
                fileOut = new FileOutputStream(savePath);
                // 写入excel文件
                bomwb.write(fileOut);
            } finally {
                if (fileOut != null) {
                    try {
                        fileOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //BZWLQD
        List<ColAndRow> bzwlqdPicColAndRow = getQcColAndRowByModule("BZWLQD");
        POIFSFileSystem bzwlqdfs;
        FileOutputStream bzwlqdFileOut = null;
        try {
            bzwlqdfs = new POIFSFileSystem(new FileInputStream(savePath));
            HSSFWorkbook bzwlqdwb = new HSSFWorkbook(bzwlqdfs);
            String sheetName = "包装物料清单";
            HSSFSheet bzwlqdst = bzwlqdwb.getSheet(sheetName);
            if (bzwlqdwb != null) {
                if (bzwlqdPicColAndRow != null && bzwlqdPicColAndRow.size() > 0) {
                    for (ColAndRow colAndRow : bzwlqdPicColAndRow) {
                        String cellValue = PoiUtil.getCellValue(bzwlqdst, colAndRow.getStartCol(), colAndRow.getStartRow());
                        if (cellValue != null && !cellValue.trim().equals("")) {
                            PoiUtil.stringToImage(picBaseLocation + "/QC-PHOTO-JPEG/" + encodeQcNo + "/" + qualityCheckItem.getQc2ItCatNoSuffix() + "/" + cellValue, bzwlqdwb, bzwlqdst, colAndRow.getStartCol(), colAndRow.getStartRow(), colAndRow.getEndCol(), colAndRow.getEndRow());
                        }
                    }
                }
            }
            bzwlqdFileOut = new FileOutputStream(savePath);
            // 写入excel文件
            bzwlqdwb.write(bzwlqdFileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bzwlqdFileOut != null) {
                try {
                    bzwlqdFileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //DHGJ
        List<ColAndRow> dhgjPicColAndRow = getQcColAndRowByModule("DHGJ");
        POIFSFileSystem dhgjfs;
        FileOutputStream dhgjFileOut = null;
        try {
            dhgjfs = new POIFSFileSystem(new FileInputStream(savePath));
            HSSFWorkbook dhgjwb = new HSSFWorkbook(dhgjfs);
            String sheetName = "产品改善情况";
            HSSFSheet dhgjst = dhgjwb.getSheet(sheetName);
            if (dhgjwb != null) {
                if (dhgjPicColAndRow != null && dhgjPicColAndRow.size() > 0) {
                    for (ColAndRow colAndRow : dhgjPicColAndRow) {
                        String cellValue = PoiUtil.getCellValue(dhgjst, colAndRow.getStartCol(), colAndRow.getStartRow());
                        if (cellValue != null && !cellValue.trim().equals("")) {
                            PoiUtil.stringToImage(picBaseLocation + "/QC-PHOTO-JPEG/" + encodeQcNo + "/" + qualityCheckItem.getQc2ItCatNoSuffix() + "/" + cellValue, dhgjwb, dhgjst, colAndRow.getStartCol(), colAndRow.getStartRow(), colAndRow.getEndCol(), colAndRow.getEndRow());
                        }
                    }
                }
            }
            dhgjFileOut = new FileOutputStream(savePath);
            // 写入excel文件
            dhgjwb.write(dhgjFileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dhgjFileOut != null) {
                try {
                    dhgjFileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //SFCSBG
        POIFSFileSystem sfcsbgfs;
        FileOutputStream sfcsbgFileOut = null;
        try {
            sfcsbgfs = new POIFSFileSystem(new FileInputStream(savePath));
            HSSFWorkbook sfcsbgwb = new HSSFWorkbook(sfcsbgfs);
            String sheetName = "水分测试报告";
            HSSFSheet sfcsbgst = sfcsbgwb.getSheet(sheetName);
            if (sfcsbgwb != null) {
                String cellValue = PoiUtil.getCellValue(sfcsbgst, 2, 37);
                if (cellValue != null && !cellValue.trim().equals("")) {
                    PoiUtil.stringToImage(picBaseLocation + "/QC-PHOTO-JPEG/" + encodeQcNo + "/" + qualityCheckItem.getQc2ItCatNoSuffix() + "/" + cellValue, sfcsbgwb, sfcsbgst, 2, 37, 11, 46);
                }
            }
            sfcsbgFileOut = new FileOutputStream(savePath);
            // 写入excel文件
            sfcsbgwb.write(sfcsbgFileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sfcsbgFileOut != null) {
                try {
                    sfcsbgFileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //CC
        POIFSFileSystem ccfs;
        FileOutputStream ccFileOut = null;
        try {
            ccfs = new POIFSFileSystem(new FileInputStream(savePath));
            HSSFWorkbook ccwb = new HSSFWorkbook(ccfs);
            String sheetName = "尺寸检测表";
            HSSFSheet ccst = ccwb.getSheet(sheetName);
            if (ccwb != null) {
                String cellValue = PoiUtil.getCellValue(ccst, 0, 1);
                if (cellValue != null && !cellValue.trim().equals("")) {
                    PoiUtil.stringToImage(picBaseLocation + "/QC-PHOTO-JPEG/" + encodeQcNo + "/" + qualityCheckItem.getQc2ItCatNoSuffix() + "/" + cellValue, ccwb, ccst, 0, 1, 13, 1);
                }
            }
            ccFileOut = new FileOutputStream(savePath);
            // 写入excel文件
            ccwb.write(ccFileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ccFileOut != null) {
                try {
                    ccFileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //CSYQ
        List<ColAndRow> csyqPicColAndRow = getQcColAndRowByModule("CSYQ");
        POIFSFileSystem csyqfs;
        FileOutputStream csyqFileOut = null;
        try {

            csyqfs = new POIFSFileSystem(new FileInputStream(savePath));
            HSSFWorkbook csyqwb = new HSSFWorkbook(csyqfs);
            String sheetName = "测试要求";
            HSSFSheet csyqst = csyqwb.getSheet(sheetName);
            if (csyqwb != null) {
                if (csyqPicColAndRow != null && csyqPicColAndRow.size() > 0) {
                    for (ColAndRow colAndRow : csyqPicColAndRow) {
                        String cellValue = PoiUtil.getCellValue(csyqst, colAndRow.getStartCol(), colAndRow.getStartRow());
                        if (cellValue != null && !cellValue.trim().equals("")) {
                            PoiUtil.stringToImage(picBaseLocation + "/QC-PHOTO-JPEG/" + encodeQcNo + "/" + qualityCheckItem.getQc2ItCatNoSuffix() + "/" + cellValue, csyqwb, csyqst, colAndRow.getStartCol(), colAndRow.getStartRow(), colAndRow.getEndCol(), colAndRow.getEndRow());
                        }
                    }
                }
            }
            csyqFileOut = new FileOutputStream(savePath);
            // 写入excel文件
            csyqwb.write(csyqFileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csyqFileOut != null) {
                try {
                    csyqFileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private List<ColAndRow> getQcColAndRowByModule(String module) {
        List<ColAndRow> colAndRowList = new ArrayList<ColAndRow>();
        if (module.equals("BZWLQD")) {
            colAndRowList.add(new ColAndRow(0, 5));
            colAndRowList.add(new ColAndRow(0, 6));
            colAndRowList.add(new ColAndRow(0, 7));
            colAndRowList.add(new ColAndRow(0, 8));
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 12));
        }
        if (module.equals("DHGJ")) {
            colAndRowList.add(new ColAndRow(1, 4));
            colAndRowList.add(new ColAndRow(1, 5));
            colAndRowList.add(new ColAndRow(1, 6));
            colAndRowList.add(new ColAndRow(1, 7));
            colAndRowList.add(new ColAndRow(1, 8));
            colAndRowList.add(new ColAndRow(1, 9));
            colAndRowList.add(new ColAndRow(1, 10));
            colAndRowList.add(new ColAndRow(1, 11));
            colAndRowList.add(new ColAndRow(1, 12));
            colAndRowList.add(new ColAndRow(1, 13));
            colAndRowList.add(new ColAndRow(1, 14));
            colAndRowList.add(new ColAndRow(1, 15));
            colAndRowList.add(new ColAndRow(1, 16));
        }
        if (module.equals("CSYQ")) {
            colAndRowList.add(new ColAndRow(1, 4));
            colAndRowList.add(new ColAndRow(1, 8, 1, 13));
        }
        return colAndRowList;
    }

    private List<ColAndRow> getBomColAndRowByTemplateName(String qcTemplateName) {
        List<ColAndRow> colAndRowList = new ArrayList<ColAndRow>();
        if (qcTemplateName.equals("bbq1")) {
            colAndRowList.add(new ColAndRow(0, 8));
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 12));
            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 21));
            colAndRowList.add(new ColAndRow(0, 22));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
            colAndRowList.add(new ColAndRow(0, 26));
            colAndRowList.add(new ColAndRow(0, 27));
            colAndRowList.add(new ColAndRow(0, 28));
            colAndRowList.add(new ColAndRow(0, 29));
            colAndRowList.add(new ColAndRow(0, 30));
        }
        if (qcTemplateName.equals("chair1")) {
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 21));
            colAndRowList.add(new ColAndRow(0, 22));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
            colAndRowList.add(new ColAndRow(0, 26));
            colAndRowList.add(new ColAndRow(0, 27));
            colAndRowList.add(new ColAndRow(0, 28));
            colAndRowList.add(new ColAndRow(0, 29));
            colAndRowList.add(new ColAndRow(0, 30));
            colAndRowList.add(new ColAndRow(0, 31));
            colAndRowList.add(new ColAndRow(0, 32));
            colAndRowList.add(new ColAndRow(0, 33));
        }
        if (qcTemplateName.equals("cushion1")) {
            colAndRowList.add(new ColAndRow(0, 8));
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));

            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
        }
        if (qcTemplateName.equals("desk1")) {
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
        }
        if (qcTemplateName.equals("fitment1")) {
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 12));
            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));

            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 21));
            colAndRowList.add(new ColAndRow(0, 22));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
        }
        if (qcTemplateName.equals("misc1")) {
            colAndRowList.add(new ColAndRow(0, 8));
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 12));
            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 21));
            colAndRowList.add(new ColAndRow(0, 22));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
            colAndRowList.add(new ColAndRow(0, 26));
            colAndRowList.add(new ColAndRow(0, 27));
        }
        if (qcTemplateName.equals("piaopeng1")) {
            colAndRowList.add(new ColAndRow(0, 8));
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 12));
            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 21));
            colAndRowList.add(new ColAndRow(0, 22));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
            colAndRowList.add(new ColAndRow(0, 26));
            colAndRowList.add(new ColAndRow(0, 27));
            colAndRowList.add(new ColAndRow(0, 28));
            colAndRowList.add(new ColAndRow(0, 29));
            colAndRowList.add(new ColAndRow(0, 30));
            colAndRowList.add(new ColAndRow(0, 31));
            colAndRowList.add(new ColAndRow(0, 32));
            colAndRowList.add(new ColAndRow(0, 33));
            colAndRowList.add(new ColAndRow(0, 34));
            colAndRowList.add(new ColAndRow(0, 35));
            colAndRowList.add(new ColAndRow(0, 36));
        }
        if (qcTemplateName.equals("sofa1")) {
            colAndRowList.add(new ColAndRow(0, 12));
            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
            colAndRowList.add(new ColAndRow(0, 26));
            colAndRowList.add(new ColAndRow(0, 27));
            colAndRowList.add(new ColAndRow(0, 28));
            colAndRowList.add(new ColAndRow(0, 29));
            colAndRowList.add(new ColAndRow(0, 30));
            colAndRowList.add(new ColAndRow(0, 31));
        }
        if (qcTemplateName.equals("umbrella1")) {
            colAndRowList.add(new ColAndRow(0, 8));
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 12));
            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 21));
            colAndRowList.add(new ColAndRow(0, 22));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
            colAndRowList.add(new ColAndRow(0, 26));
            colAndRowList.add(new ColAndRow(0, 27));
            colAndRowList.add(new ColAndRow(0, 28));
            colAndRowList.add(new ColAndRow(0, 29));
            colAndRowList.add(new ColAndRow(0, 30));
            colAndRowList.add(new ColAndRow(0, 31));
            colAndRowList.add(new ColAndRow(0, 32));
            colAndRowList.add(new ColAndRow(0, 33));
            colAndRowList.add(new ColAndRow(0, 34));
            colAndRowList.add(new ColAndRow(0, 35));
            colAndRowList.add(new ColAndRow(0, 36));
            colAndRowList.add(new ColAndRow(0, 37));
        }
        if (qcTemplateName.endsWith("yangpeng1")) {
            colAndRowList.add(new ColAndRow(0, 8));
            colAndRowList.add(new ColAndRow(0, 9));
            colAndRowList.add(new ColAndRow(0, 10));
            colAndRowList.add(new ColAndRow(0, 11));
            colAndRowList.add(new ColAndRow(0, 12));
            colAndRowList.add(new ColAndRow(0, 13));
            colAndRowList.add(new ColAndRow(0, 14));
            colAndRowList.add(new ColAndRow(0, 15));
            colAndRowList.add(new ColAndRow(0, 16));
            colAndRowList.add(new ColAndRow(0, 17));
            colAndRowList.add(new ColAndRow(0, 18));
            colAndRowList.add(new ColAndRow(0, 19));
            colAndRowList.add(new ColAndRow(0, 20));
            colAndRowList.add(new ColAndRow(0, 21));
            colAndRowList.add(new ColAndRow(0, 22));
            colAndRowList.add(new ColAndRow(0, 23));
            colAndRowList.add(new ColAndRow(0, 24));
            colAndRowList.add(new ColAndRow(0, 25));
            colAndRowList.add(new ColAndRow(0, 26));
            colAndRowList.add(new ColAndRow(0, 27));
            colAndRowList.add(new ColAndRow(0, 28));
            colAndRowList.add(new ColAndRow(0, 29));
            colAndRowList.add(new ColAndRow(0, 30));
            colAndRowList.add(new ColAndRow(0, 31));
            colAndRowList.add(new ColAndRow(0, 32));
            colAndRowList.add(new ColAndRow(0, 33));
            colAndRowList.add(new ColAndRow(0, 34));
            colAndRowList.add(new ColAndRow(0, 35));
            colAndRowList.add(new ColAndRow(0, 36));
            colAndRowList.add(new ColAndRow(0, 37));
            colAndRowList.add(new ColAndRow(0, 38));
            colAndRowList.add(new ColAndRow(0, 39));
        }
        return colAndRowList;
    }

    //	private void concatQcItem(QualityCheckItem qualityCheckItem) {
    //		String encodeQcNo = Base64.encode(qualityCheckItem.getQc2No(), "utf-8");
    //		String itCatNoSuffix = qualityCheckItem.getQc2ItCatNoSuffix();
    //		Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
    //		String baselocation = tempPropertiesMap.get("baselocation");
    //		String saveBasePath = baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix;
    //		try {
    //			HSSFWorkbook resultwbt = new  HSSFWorkbook();
    //			//BOM
    //			String qcTemplateName = itemMgr.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
    //			if(qcTemplateName != null && qcTemplateName.trim().endsWith("")){
    //				String bomExcelFileName = getBomTemplateExcelName(qcTemplateName);
    //				POIFSFileSystem bomfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/" + bomExcelFileName));
    //				HSSFWorkbook bomwb = new HSSFWorkbook(bomfs);
    //				resultwbt = ExcelManager.copyRows(bomwb,resultwbt);
    //			}
    //
    //			//BZMX
    //			POIFSFileSystem bzmxfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/包装明细.xls"));
    //			HSSFWorkbook bzmxwb = new HSSFWorkbook(bzmxfs);
    //			resultwbt = ExcelManager.copyRows(bzmxwb,resultwbt);
    //
    //			//BZ
    //			POIFSFileSystem bzfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/包装测试.xls"));
    //			HSSFWorkbook bzwb = new HSSFWorkbook(bzfs);
    //			resultwbt = ExcelManager.copyRows(bzwb,resultwbt);
    //
    //			//BZWLQD
    //			POIFSFileSystem bzwlqdfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/包装物料清单.xls"));
    //			HSSFWorkbook bzwlqdwb = new HSSFWorkbook(bzwlqdfs);
    //			resultwbt = ExcelManager.copyRows(bzwlqdwb,resultwbt);
    //
    //			//CP
    //			POIFSFileSystem cpfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/产品.xls"));
    //			HSSFWorkbook cpwb = new HSSFWorkbook(cpfs);
    //			resultwbt = ExcelManager.copyRows(cpwb,resultwbt);
    //
    //			//DHGJ
    //			POIFSFileSystem dhgjfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/产品改善情况.xls"));
    //			HSSFWorkbook dhgjwb = new HSSFWorkbook(dhgjfs);
    //			resultwbt = ExcelManager.copyRows(dhgjwb,resultwbt);
    //
    //			//YHZJ
    //			POIFSFileSystem yhzjfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/验货总结报告(抽检).xls"));
    //			HSSFWorkbook yhzjwb = new HSSFWorkbook(yhzjfs);
    //			resultwbt = ExcelManager.copyRows(yhzjwb,resultwbt);
    //
    //			//SFCSBG
    //			if(qualityCheckItem.isQc2HaveSfcsbg()){
    //				POIFSFileSystem sfcsbgfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/水分测试报告.xls"));
    //				HSSFWorkbook sfcsbgwb = new HSSFWorkbook(sfcsbgfs);
    //				resultwbt = ExcelManager.copyRows(sfcsbgwb,resultwbt);
    //			}
    //
    //			//YSJC
    //			if(qualityCheckItem.isQc2HaveYsjc()){
    //				POIFSFileSystem ysjcfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/颜色对比检查表.xls"));
    //				HSSFWorkbook ysjcwb = new HSSFWorkbook(ysjcfs);
    //				resultwbt = ExcelManager.copyRows(ysjcwb,resultwbt);
    //			}
    //
    //			//FYHSM
    //			if(qualityCheckItem.isQc2HaveFyhsm()){
    //				POIFSFileSystem fyhsmfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/复验货申明.xls"));
    //				HSSFWorkbook fyhsmwb = new HSSFWorkbook(fyhsmfs);
    //				resultwbt = ExcelManager.copyRows(fyhsmwb,resultwbt);
    //			}
    //
    //			//ZCJYRBG
    //			if(qualityCheckItem.isQc2HaveZcjyrbg()){
    //				POIFSFileSystem zcjyrbgfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/QC制程检验日报表.xls"));
    //				HSSFWorkbook zcjyrbgwb = new HSSFWorkbook(zcjyrbgfs);
    //				resultwbt = ExcelManager.copyRows(zcjyrbgwb,resultwbt);
    //			}
    //
    //			//SCJHKZ
    //			if(qualityCheckItem.isQc2HaveScjhkz()){
    //				POIFSFileSystem scjhkzfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/生产计划控制表.xls"));
    //				HSSFWorkbook scjhkzwb = new HSSFWorkbook(scjhkzfs);
    //				resultwbt = ExcelManager.copyRows(scjhkzwb,resultwbt);
    //			}
    //
    //			//GFX
    //			if(qualityCheckItem.isQc2HaveGfx()){
    //				POIFSFileSystem gfxfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/高风险.xls"));
    //				HSSFWorkbook gfxwb = new HSSFWorkbook(gfxfs);
    //				resultwbt = ExcelManager.copyRows(gfxwb,resultwbt);
    //			}
    //
    //			//CLSJ
    //			if(qualityCheckItem.isQc2HaveClsj()){
    //				POIFSFileSystem clsjfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/材料送检记录.xls"));
    //				HSSFWorkbook clsjwb = new HSSFWorkbook(clsjfs);
    //				resultwbt = ExcelManager.copyRows(clsjwb,resultwbt);
    //			}
    //
    //			//WPCGZ
    //			if(qualityCheckItem.isQc2HaveWpcgz()){
    //				POIFSFileSystem wpcgzfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/WPC地板检验规章.xls"));
    //				HSSFWorkbook wpcgzwb = new HSSFWorkbook(wpcgzfs);
    //				resultwbt = ExcelManager.copyRows(wpcgzwb,resultwbt);
    //			}
    //
    //			//CSYQ
    //			if(qualityCheckItem.isQc2HaveCsyq()){
    //				POIFSFileSystem csyqfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/测试要求.xls"));
    //				HSSFWorkbook csyqwb = new HSSFWorkbook(csyqfs);
    //				resultwbt = ExcelManager.copyRows(csyqwb,resultwbt);
    //			}
    //
    //			//CC
    //			if(qualityCheckItem.isQc2HaveCc()){
    //				POIFSFileSystem ccfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/尺寸检测表.xls"));
    //				HSSFWorkbook ccwb = new HSSFWorkbook(ccfs);
    //				resultwbt = ExcelManager.copyRows(ccwb,resultwbt);
    //			}
    //			// source ,target 为,源sheet 页和目标sheet页,
    //			FileOutputStream fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
    //			resultwbt.write(fileOut);
    //			fileOut.flush();
    //			fileOut.close();
    //			System.out.println("生成完成!");
    //		} catch (Exception e) {
    //			e.printStackTrace();
    //		}
    //	}

    private void concatQcItem2(QualityCheckItem qualityCheckItem) {
        String encodeQcNo = Base64.encode(qualityCheckItem.getQc2No(), "utf-8");
        String itCatNoSuffix = qualityCheckItem.getQc2ItCatNoSuffix();
        Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
        String baselocation = tempPropertiesMap.get("baselocation");
        String saveBasePath = baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix;
        try {
            Common.newQcExcelFile(baselocation + "/" + encodeQcNo, baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");

            //封面
            FileInputStream baseInputStream;
            POIFSFileSystem basefs;
            HSSFWorkbook fmWbt = new HSSFWorkbook();

            POIFSFileSystem fmfs = new POIFSFileSystem(ExcelManager.class.getClassLoader().getResourceAsStream("qctemplates/封面页.xls"));
            HSSFWorkbook fmwb = new HSSFWorkbook(fmfs);
            fmWbt.createSheet("封面页");
            HSSFSheet targetfmSheet = fmWbt.getSheet("封面页");
            HSSFSheet sourcefmSheet = fmwb.getSheetAt(0);
            PoiUtil.copySheet(targetfmSheet, sourcefmSheet, fmWbt, fmwb);

            FileOutputStream fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            fmWbt.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //BOM
            //			String qcTemplateName = itemMgr.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
            //			if(qcTemplateName != null && qcTemplateName.triequalsWith("")){
            //				baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            //				basefs = new POIFSFileSystem(baseInputStream);
            //				HSSFWorkbook bomWbt = new  HSSFWorkbook(basefs);
            //
            //				POIFSFileSystem bomfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/BOM.xls"));
            //				HSSFWorkbook bomwb = new HSSFWorkbook(bomfs);
            //				bomWbt.createSheet("BOM");
            //				HSSFSheet targetBomSheet = bomWbt.getSheet("BOM");
            //				HSSFSheet sourceBomSheet = bomwb.getSheetAt(0);
            //				PoiUtil.copySheet(targetBomSheet, sourceBomSheet, bomWbt, bomwb);
            //
            //				fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            //				bomWbt.write(fileOut);
            //				fileOut.flush();
            //				fileOut.close();
            //			}

            //BZMX
            baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            basefs = new POIFSFileSystem(baseInputStream);
            HSSFWorkbook bzmxWbt = new HSSFWorkbook(basefs);

            POIFSFileSystem bzmxfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/包装明细.xls"));
            HSSFWorkbook bzmxwb = new HSSFWorkbook(bzmxfs);
            bzmxWbt.createSheet("包装明细");
            HSSFSheet targetBzmxSheet = bzmxWbt.getSheet("包装明细");
            HSSFSheet sourceBzmxSheet = bzmxwb.getSheetAt(0);
            PoiUtil.copySheet(targetBzmxSheet, sourceBzmxSheet, bzmxWbt, bzmxwb);

            fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            bzmxWbt.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //BZ
            baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            basefs = new POIFSFileSystem(baseInputStream);
            HSSFWorkbook bzWbt = new HSSFWorkbook(basefs);

            POIFSFileSystem bzfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/包装测试.xls"));
            HSSFWorkbook bzwb = new HSSFWorkbook(bzfs);
            bzWbt.createSheet("包装测试");
            HSSFSheet targetBzSheet = bzWbt.getSheet("包装测试");
            HSSFSheet sourceBzSheet = bzwb.getSheetAt(0);
            PoiUtil.copySheet(targetBzSheet, sourceBzSheet, bzWbt, bzwb);

            fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            bzWbt.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //BZWLQD
            baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            basefs = new POIFSFileSystem(baseInputStream);
            HSSFWorkbook bzwlqdWbt = new HSSFWorkbook(basefs);

            POIFSFileSystem bzwlqdfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/包装物料清单.xls"));
            HSSFWorkbook bzwlqdwb = new HSSFWorkbook(bzwlqdfs);
            bzwlqdWbt.createSheet("包装物料清单");
            HSSFSheet targetBzwlqdSheet = bzwlqdWbt.getSheet("包装物料清单");
            HSSFSheet sourceBzwlqdSheet = bzwlqdwb.getSheetAt(0);
            PoiUtil.copySheet(targetBzwlqdSheet, sourceBzwlqdSheet, bzwlqdWbt, bzwlqdwb);

            fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            bzwlqdWbt.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //CP
            baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            basefs = new POIFSFileSystem(baseInputStream);
            HSSFWorkbook cpWbt = new HSSFWorkbook(basefs);

            POIFSFileSystem cpfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/产品.xls"));
            HSSFWorkbook cpwb = new HSSFWorkbook(cpfs);
            cpWbt.createSheet("产品");
            HSSFSheet targetCpSheet = cpWbt.getSheet("产品");
            HSSFSheet sourceCpSheet = cpwb.getSheetAt(0);
            PoiUtil.copySheet(targetCpSheet, sourceCpSheet, cpWbt, cpwb);

            fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            cpWbt.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //DHGJ
            baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            basefs = new POIFSFileSystem(baseInputStream);
            HSSFWorkbook dhgjWbt = new HSSFWorkbook(basefs);

            POIFSFileSystem dhgjfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/产品改善情况.xls"));
            HSSFWorkbook dhgjwb = new HSSFWorkbook(dhgjfs);
            dhgjWbt.createSheet("产品改善情况");
            HSSFSheet targetDhgjSheet = dhgjWbt.getSheet("产品改善情况");
            HSSFSheet sourceDhgjSheet = dhgjwb.getSheetAt(0);
            PoiUtil.copySheet(targetDhgjSheet, sourceDhgjSheet, dhgjWbt, dhgjwb);

            fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            dhgjWbt.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //YHZJ
            baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            basefs = new POIFSFileSystem(baseInputStream);
            HSSFWorkbook yhzjWbt = new HSSFWorkbook(basefs);

            POIFSFileSystem yhzjfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/验货总结报告(抽检).xls"));
            HSSFWorkbook yhzjwb = new HSSFWorkbook(yhzjfs);
            yhzjWbt.createSheet("验货总结报告(抽检)");
            HSSFSheet targetYhzjSheet = yhzjWbt.getSheet("验货总结报告(抽检)");
            HSSFSheet sourceYhzjSheet = yhzjwb.getSheetAt(0);
            PoiUtil.copySheet(targetYhzjSheet, sourceYhzjSheet, yhzjWbt, yhzjwb);

            fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
            yhzjWbt.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //SFCSBG
            if (qualityCheckItem.isQc2HaveSfcsbg()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem sfcsbgfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/水分测试报告.xls"));
                HSSFWorkbook sfcsbgwb = new HSSFWorkbook(sfcsbgfs);
                resultwbt.createSheet("水分测试报告");
                HSSFSheet targetSfcsbgSheet = resultwbt.getSheet("水分测试报告");
                HSSFSheet sourceSfcsbgSheet = sfcsbgwb.getSheetAt(0);
                PoiUtil.copySheet(targetSfcsbgSheet, sourceSfcsbgSheet, resultwbt, sfcsbgwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //YSJC
            if (qualityCheckItem.isQc2HaveYsjc()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem ysjcfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/颜色对比检查表.xls"));
                HSSFWorkbook ysjcwb = new HSSFWorkbook(ysjcfs);
                resultwbt.createSheet("颜色对比检查表");
                HSSFSheet targetYsjcSheet = resultwbt.getSheet("颜色对比检查表");
                HSSFSheet sourceYsjcSheet = ysjcwb.getSheetAt(0);
                PoiUtil.copySheet(targetYsjcSheet, sourceYsjcSheet, resultwbt, ysjcwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //FYHSM
            if (qualityCheckItem.isQc2HaveFyhsm()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem fyhsmfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/复验货申明.xls"));
                HSSFWorkbook fyhsmwb = new HSSFWorkbook(fyhsmfs);
                resultwbt.createSheet("复验货申明");
                HSSFSheet targetFyhsmSheet = resultwbt.getSheet("复验货申明");
                HSSFSheet sourceFyhsmSheet = fyhsmwb.getSheetAt(0);
                PoiUtil.copySheet(targetFyhsmSheet, sourceFyhsmSheet, resultwbt, fyhsmwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //ZCJYRBG
            if (qualityCheckItem.isQc2HaveZcjyrbg()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem zcjyrbgfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/QC制程检验日报表.xls"));
                HSSFWorkbook zcjyrbgwb = new HSSFWorkbook(zcjyrbgfs);
                resultwbt.createSheet("QC制程检验日报表");
                HSSFSheet targetZcjyrbSheet = resultwbt.getSheet("QC制程检验日报表");
                HSSFSheet sourceZcjyrbSheet = zcjyrbgwb.getSheetAt(0);
                PoiUtil.copySheet(targetZcjyrbSheet, sourceZcjyrbSheet, resultwbt, zcjyrbgwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //SCJHKZ
            if (qualityCheckItem.isQc2HaveScjhkz()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem scjhkzfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/生产计划控制表.xls"));
                HSSFWorkbook scjhkzwb = new HSSFWorkbook(scjhkzfs);
                resultwbt.createSheet("生产计划控制表");
                HSSFSheet targetSheet = resultwbt.getSheet("生产计划控制表");
                HSSFSheet sourceSheet = scjhkzwb.getSheetAt(0);
                PoiUtil.copySheet(targetSheet, sourceSheet, resultwbt, scjhkzwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //GFX
            if (qualityCheckItem.isQc2HaveGfx()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem gfxfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/高风险.xls"));
                HSSFWorkbook gfxwb = new HSSFWorkbook(gfxfs);
                resultwbt.createSheet("高风险");
                HSSFSheet targetSheet = resultwbt.getSheet("高风险");
                HSSFSheet sourceSheet = gfxwb.getSheetAt(0);
                PoiUtil.copySheet(targetSheet, sourceSheet, resultwbt, gfxwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //CLSJ
            if (qualityCheckItem.isQc2HaveClsj()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem clsjfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/材料送检记录.xls"));
                HSSFWorkbook clsjwb = new HSSFWorkbook(clsjfs);
                resultwbt.createSheet("材料送检记录");
                HSSFSheet targetSheet = resultwbt.getSheet("材料送检记录");
                HSSFSheet sourceSheet = clsjwb.getSheetAt(0);
                PoiUtil.copySheet(targetSheet, sourceSheet, resultwbt, clsjwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //WPCGZ
            if (qualityCheckItem.isQc2HaveWpcgz()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem wpcgzfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/WPC地板检验规章.xls"));
                HSSFWorkbook wpcgzwb = new HSSFWorkbook(wpcgzfs);
                resultwbt.createSheet("WPC地板检验规章");
                HSSFSheet targetSheet = resultwbt.getSheet("WPC地板检验规章");
                HSSFSheet sourceSheet = wpcgzwb.getSheetAt(0);
                PoiUtil.copySheet(targetSheet, sourceSheet, resultwbt, wpcgzwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //CSYQ
            if (qualityCheckItem.isQc2HaveCsyq()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem csyqfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/测试要求.xls"));
                HSSFWorkbook csyqwb = new HSSFWorkbook(csyqfs);
                resultwbt.createSheet("测试要求");
                HSSFSheet targetSheet = resultwbt.getSheet("测试要求");
                HSSFSheet sourceSheet = csyqwb.getSheetAt(0);
                PoiUtil.copySheet(targetSheet, sourceSheet, resultwbt, csyqwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

            //CC
            if (qualityCheckItem.isQc2HaveCc()) {
                baseInputStream = new FileInputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                basefs = new POIFSFileSystem(baseInputStream);
                HSSFWorkbook resultwbt = new HSSFWorkbook(basefs);

                POIFSFileSystem ccfs = new POIFSFileSystem(new FileInputStream(saveBasePath + "/尺寸检测表.xls"));
                HSSFWorkbook ccwb = new HSSFWorkbook(ccfs);
                resultwbt.createSheet("尺寸检测表");
                HSSFSheet targetSheet = resultwbt.getSheet("尺寸检测表");
                HSSFSheet sourceSheet = ccwb.getSheetAt(0);
                PoiUtil.copySheet(targetSheet, sourceSheet, resultwbt, ccwb);

                fileOut = new FileOutputStream(baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix + ".xls");
                resultwbt.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }
            System.out.println("生成完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportQcItem(QualityCheckItem qualityCheckItem) {
        String encodeQcNo = Base64.encode(qualityCheckItem.getQc2No(), "utf-8");
        String itCatNoSuffix = qualityCheckItem.getQc2ItCatNoSuffix();
        Workbook workbook = null;

        //BOM
        String qcTemplateName = itemMgr.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
        if (qcTemplateName != null && qcTemplateName.trim().endsWith("")) {
            String bomExcelFileName = getBomTemplateExcelName(qcTemplateName);
            String detailVoStr = qualityCheckItem.getQc2ItDetails();
            if (detailVoStr == null || !detailVoStr.startsWith("{")) {
                detailVoStr = "{}";
            }
            Map<String, Object> detailVo = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                //把detail data json string 转成map对象
                detailVo = mapper.readValue(detailVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("itemtemplates/" + bomExcelFileName, detailVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "BOM.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //BZMX
        String bzmxVoStr = qualityCheckItem.getQc2Bzmx();
        if (bzmxVoStr == null || !bzmxVoStr.startsWith("{")) {
            bzmxVoStr = "{}";
        }
        Map<String, Object> bzmxVo = new HashMap<String, Object>();
        ObjectMapper bzmxMapper = new ObjectMapper();
        try {
            bzmxVo = bzmxMapper.readValue(bzmxVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            workbook = ExcelManager.importToExcel("qctemplates/包装明细.xls", bzmxVo);
            ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "包装明细.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //BZ
        String bzVoStr = qualityCheckItem.getQc2Bz();
        if (bzVoStr == null || !bzVoStr.startsWith("{")) {
            bzVoStr = "{}";
        }
        Map<String, Object> bzVo = new HashMap<String, Object>();
        ObjectMapper bzMapper = new ObjectMapper();
        try {
            bzVo = bzMapper.readValue(bzVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            workbook = ExcelManager.importToExcel("qctemplates/包装测试.xls", bzVo);
            ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "包装测试.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //BZWLQD
        String bzwlqdVoStr = qualityCheckItem.getQc2Bzwlqd();
        if (bzwlqdVoStr == null || !bzwlqdVoStr.startsWith("{")) {
            bzwlqdVoStr = "{}";
        }
        Map<String, Object> bzwlqdVo = new HashMap<String, Object>();
        ObjectMapper bzwlqdMapper = new ObjectMapper();
        try {
            bzwlqdVo = bzwlqdMapper.readValue(bzwlqdVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            workbook = ExcelManager.importToExcel("qctemplates/包装物料清单.xls", bzwlqdVo);
            ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "包装物料清单.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //CP
        String cpVoStr = qualityCheckItem.getQc2Cp();
        if (cpVoStr == null || !cpVoStr.startsWith("{")) {
            cpVoStr = "{}";
        }
        Map<String, Object> cpVo = new HashMap<String, Object>();
        ObjectMapper cpMapper = new ObjectMapper();
        try {
            cpVo = cpMapper.readValue(cpVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            workbook = ExcelManager.importToExcel("qctemplates/产品.xls", cpVo);
            ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "产品.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //DHGJ
        String dhgjVoStr = qualityCheckItem.getQc2Dhgj();
        if (dhgjVoStr == null || !dhgjVoStr.startsWith("{")) {
            dhgjVoStr = "{}";
        }
        Map<String, Object> dhgjVo = new HashMap<String, Object>();
        ObjectMapper dhgjMapper = new ObjectMapper();
        try {
            dhgjVo = dhgjMapper.readValue(dhgjVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            workbook = ExcelManager.importToExcel("qctemplates/产品改善情况.xls", dhgjVo);
            ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "产品改善情况.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //YHZJ
        String yhzjVoStr = qualityCheckItem.getQc2Yhzj();
        if (yhzjVoStr == null || !yhzjVoStr.startsWith("{")) {
            yhzjVoStr = "{}";
        }
        Map<String, Object> yhzjVo = new HashMap<String, Object>();
        ObjectMapper yhzjMapper = new ObjectMapper();
        try {
            yhzjVo = yhzjMapper.readValue(yhzjVoStr, new TypeReference<HashMap<String, Object>>() {
            });
            workbook = ExcelManager.importToExcel("qctemplates/验货总结报告(抽检).xls", yhzjVo);
            ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "验货总结报告(抽检).xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //SFCSBG
        if (qualityCheckItem.isQc2HaveSfcsbg()) {
            String sfcsbgVoStr = qualityCheckItem.getQc2Sfcsbg();
            if (sfcsbgVoStr == null || !sfcsbgVoStr.startsWith("{")) {
                sfcsbgVoStr = "{}";
            }
            Map<String, Object> sfcsbgVo = new HashMap<String, Object>();
            ObjectMapper sfcsbgMapper = new ObjectMapper();
            try {
                sfcsbgVo = sfcsbgMapper.readValue(sfcsbgVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/水分测试报告.xls", sfcsbgVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "水分测试报告.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //YSJC
        if (qualityCheckItem.isQc2HaveYsjc()) {
            String ysjcVoStr = qualityCheckItem.getQc2Ysjc();
            if (ysjcVoStr == null || !ysjcVoStr.startsWith("{")) {
                ysjcVoStr = "{}";
            }
            Map<String, Object> ysjcVo = new HashMap<String, Object>();
            ObjectMapper ysjcMapper = new ObjectMapper();
            try {
                ysjcVo = ysjcMapper.readValue(ysjcVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/颜色对比检查表.xls", ysjcVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "颜色对比检查表.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //FYHSM
        if (qualityCheckItem.isQc2HaveFyhsm()) {
            String fyhsmVoStr = qualityCheckItem.getQc2Fyhsm();
            if (fyhsmVoStr == null || !fyhsmVoStr.startsWith("{")) {
                fyhsmVoStr = "{}";
            }
            Map<String, Object> fyhsmVo = new HashMap<String, Object>();
            ObjectMapper fyhsmMapper = new ObjectMapper();
            try {
                fyhsmVo = fyhsmMapper.readValue(fyhsmVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/复验货申明.xls", fyhsmVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "复验货申明.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //ZCJYRBG
        if (qualityCheckItem.isQc2HaveZcjyrbg()) {
            String zcjyrbgVoStr = qualityCheckItem.getQc2Zcjyrbg();
            if (zcjyrbgVoStr == null || !zcjyrbgVoStr.startsWith("{")) {
                zcjyrbgVoStr = "{}";
            }
            Map<String, Object> zcjyrbgVo = new HashMap<String, Object>();
            ObjectMapper zcjyrbgMapper = new ObjectMapper();
            try {
                zcjyrbgVo = zcjyrbgMapper.readValue(zcjyrbgVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/QC制程检验日报表.xls", zcjyrbgVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "QC制程检验日报表.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //SCJHKZ
        if (qualityCheckItem.isQc2HaveScjhkz()) {
            String scjhkzVoStr = qualityCheckItem.getQc2Scjhkz();
            if (scjhkzVoStr == null || !scjhkzVoStr.startsWith("{")) {
                scjhkzVoStr = "{}";
            }
            Map<String, Object> scjhkzVo = new HashMap<String, Object>();
            ObjectMapper scjhkzMapper = new ObjectMapper();
            try {
                scjhkzVo = scjhkzMapper.readValue(scjhkzVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/生产计划控制表.xls", scjhkzVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "生产计划控制表.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //GFX
        if (qualityCheckItem.isQc2HaveGfx()) {
            String gfxVoStr = qualityCheckItem.getQc2Gfx();
            if (gfxVoStr == null || !gfxVoStr.startsWith("{")) {
                gfxVoStr = "{}";
            }
            Map<String, Object> gfxVo = new HashMap<String, Object>();
            ObjectMapper gfxMapper = new ObjectMapper();
            try {
                gfxVo = gfxMapper.readValue(gfxVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/高风险.xls", gfxVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "高风险.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //CLSJ
        if (qualityCheckItem.isQc2HaveClsj()) {
            String clsjVoStr = qualityCheckItem.getQc2Clsj();
            if (clsjVoStr == null || !clsjVoStr.startsWith("{")) {
                clsjVoStr = "{}";
            }
            Map<String, Object> clsjVo = new HashMap<String, Object>();
            ObjectMapper clsjMapper = new ObjectMapper();
            try {
                clsjVo = clsjMapper.readValue(clsjVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/材料送检记录.xls", clsjVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "材料送检记录.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //WPCGZ
        if (qualityCheckItem.isQc2HaveWpcgz()) {
            String wpcgzVoStr = qualityCheckItem.getQc2Wpcgz();
            if (wpcgzVoStr == null || !wpcgzVoStr.startsWith("{")) {
                wpcgzVoStr = "{}";
            }
            Map<String, Object> wpcgzVo = new HashMap<String, Object>();
            ObjectMapper wpcgzMapper = new ObjectMapper();
            try {
                wpcgzVo = wpcgzMapper.readValue(wpcgzVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/WPC地板检验规章.xls", wpcgzVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "WPC地板检验规章.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //CSYQ
        if (qualityCheckItem.isQc2HaveCsyq()) {
            String csyqVoStr = qualityCheckItem.getQc2Csyq();
            if (csyqVoStr == null || !csyqVoStr.startsWith("{")) {
                csyqVoStr = "{}";
            }
            Map<String, Object> csyqVo = new HashMap<String, Object>();
            ObjectMapper csyqMapper = new ObjectMapper();
            try {
                csyqVo = csyqMapper.readValue(csyqVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/测试要求.xls", csyqVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "测试要求.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //CC
        if (qualityCheckItem.isQc2HaveCc()) {
            String ccVoStr = qualityCheckItem.getQc2Cc();
            if (ccVoStr == null || !ccVoStr.startsWith("{")) {
                ccVoStr = "{}";
            }
            Map<String, Object> ccVo = new HashMap<String, Object>();
            ObjectMapper ccMapper = new ObjectMapper();
            try {
                ccVo = ccMapper.readValue(ccVoStr, new TypeReference<HashMap<String, Object>>() {
                });
                workbook = ExcelManager.importToExcel("qctemplates/尺寸检测表.xls", ccVo);
                ExcelManager.outputExcelFileToLocal(workbook, encodeQcNo, itCatNoSuffix, "尺寸检测表.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getBomTemplateExcelName(String qcTemplateName) {
        String excelName = "";
        if (qcTemplateName.endsWith("bbq1")) {
            excelName = "BBQ.xls";
        }
        if (qcTemplateName.endsWith("chair1")) {
            excelName = "椅类.xls";
        }
        if (qcTemplateName.endsWith("cushion1")) {
            excelName = "座垫(软垫).xls";
        }
        if (qcTemplateName.endsWith("desk1")) {
            excelName = "台类.xls";
        }
        if (qcTemplateName.endsWith("fitment1")) {
            excelName = "板式家具.xls";
        }
        if (qcTemplateName.endsWith("misc1")) {
            excelName = "杂货.xls";
        }
        if (qcTemplateName.endsWith("piaopeng1")) {
            excelName = "飘篷.xls";
        }
        if (qcTemplateName.endsWith("sofa1")) {
            excelName = "沙发类.xls";
        }
        if (qcTemplateName.endsWith("umbrella1")) {
            excelName = "伞类.xls";
        }
        if (qcTemplateName.endsWith("yangpeng1")) {
            excelName = "阳篷类.xls";
        }
        return excelName;
    }

    private List<String> getRemoveBomList(String qcTemplateName) {
        List<String> removeBomList = new ArrayList<String>();
        removeBomList.add("BBQ");
        removeBomList.add("板式家具");
        removeBomList.add("飘篷");
        removeBomList.add("伞类");
        removeBomList.add("沙发类");
        removeBomList.add("台类");
        removeBomList.add("阳蓬类");
        removeBomList.add("椅类");
        removeBomList.add("杂货");
        removeBomList.add("座垫(软垫)");
        if (qcTemplateName.endsWith("bbq1")) {
            removeBomList.remove("BBQ");
        }
        if (qcTemplateName.endsWith("chair1")) {
            removeBomList.remove("椅类");
        }
        if (qcTemplateName.endsWith("cushion1")) {
            removeBomList.remove("座垫(软垫)");
        }
        if (qcTemplateName.endsWith("desk1")) {
            removeBomList.remove("台类");
        }
        if (qcTemplateName.endsWith("fitment1")) {
            removeBomList.remove("板式家具");
        }
        if (qcTemplateName.endsWith("misc1")) {
            removeBomList.remove("杂货");
        }
        if (qcTemplateName.endsWith("piaopeng1")) {
            removeBomList.remove("飘篷");
        }
        if (qcTemplateName.endsWith("sofa1")) {
            removeBomList.remove("沙发类");
        }
        if (qcTemplateName.endsWith("umbrella1")) {
            removeBomList.remove("伞类");
        }
        if (qcTemplateName.endsWith("yangpeng1")) {
            removeBomList.remove("阳蓬类");
        }
        return removeBomList;
    }

    private String getBomTemplateSheetName(String qcTemplateName) {
        String excelName = "";
        if (qcTemplateName.endsWith("bbq1")) {
            excelName = "BBQ";
        }
        if (qcTemplateName.endsWith("chair1")) {
            excelName = "椅类";
        }
        if (qcTemplateName.endsWith("cushion1")) {
            excelName = "座垫(软垫)";
        }
        if (qcTemplateName.endsWith("desk1")) {
            excelName = "台类";
        }
        if (qcTemplateName.endsWith("fitment1")) {
            excelName = "板式家具";
        }
        if (qcTemplateName.endsWith("misc1")) {
            excelName = "杂货";
        }
        if (qcTemplateName.endsWith("piaopeng1")) {
            excelName = "飘篷";
        }
        if (qcTemplateName.endsWith("sofa1")) {
            excelName = "沙发类";
        }
        if (qcTemplateName.endsWith("umbrella1")) {
            excelName = "伞类";
        }
        if (qcTemplateName.endsWith("yangpeng1")) {
            excelName = "阳篷类";
        }
        return excelName;
    }

    @Override
    public List<QualityCheckVo> searchQualityCheckVo(QualityCheckConditionVo vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<QualityCheckVo> qcList = qcMapper.findPageBreakByCondition(vo,
                rb);
        return qcList;
    }

    @Override
    public Integer searchQualityCheckVoNum(QualityCheckConditionVo vo) {
        Integer count = qcMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public boolean finishQc(String qcNo) {
        Integer count = qcMapper.finishQc(qcNo);
        if (count >= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkExportQc(String encodeQcNo) {
        Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
        String baselocation = tempPropertiesMap.get("baselocation");
        String downBasepath = baselocation + "/" + encodeQcNo;
        return Common.confirmDir(downBasepath);
    }

    @Override
    public QualityCheckItem getQcItemByUniqueKey(String qc2ItCatNoSuffix) {
        return qcMapper.getQcItemByUniqueKey(qc2ItCatNoSuffix);
    }

    @Override
    public boolean deleteQc(String qcNo) {
        try {
            qcMapper.deleteQc2(qcNo);
            qcMapper.deleteQc1(qcNo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
