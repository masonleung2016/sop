package sop.services.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.vo.Checker;
import dwz.persistence.mapper.ChargeMapper;
import dwz.persistence.mapper.CurrencyMapper;
import dwz.persistence.mapper.FactoryMapper;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.PayTermMapper;
import dwz.persistence.mapper.PoMapper;
import sop.persistence.beans.Charge;
import sop.persistence.beans.Currency;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.OfferSheetItem;
import sop.persistence.beans.PurchaseOrder;
import sop.persistence.beans.PurchaseOrderCharge;
import sop.persistence.beans.PurchaseOrderItem;
import sop.persistence.beans.PurchaseOrderLc;
import sop.persistence.beans.PurchaseOrderSbk;
import sop.persistence.beans.SaleOrderCharge;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.SaleOrderLc;
import sop.persistence.beans.SaleOrderSbk;
import sop.services.PoServiceMgr;
import sop.services.SaleOrderServiceMgr;
import sop.vo.FactoryVo;
import sop.vo.PayTermVo;
import sop.vo.PoItemMasterVo;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.PurchaseOrderCombos;
import sop.vo.PurchaseOrderDetailsVo;
import sop.vo.PurchaseOrderVo;
import sop.vo.SaleOrderDetailsVo;
import sop.vo.SoPoConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:49
 * @Package: sop.services.impl
 */


@Service(PoServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class PoServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements PoServiceMgr {

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

    @Override
    public List<PurchaseOrderVo> searchPurchaseOrderVo(SoPoConditionVo vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<PurchaseOrderVo> poList = poMapper.findPageBreakByCondition(vo,
                rb);
        return poList;
    }

    @Override
    public Integer searchPurchaseOrderVoNum(SoPoConditionVo vo) {
        Integer count = poMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public PurchaseOrderCombos getPurchaseOrderCombos() {
        PurchaseOrderCombos purchaseOrderCombos = new PurchaseOrderCombos();
        List<Currency> currs = currMapper.getAllCurrs();
        List<FactoryVo> factories = factoryMapper.getAllFactories();
        List<PayTermVo> payTerms = payTermMapper.getAllPayTermVo();
        purchaseOrderCombos.setCurrs(currs);
        purchaseOrderCombos.setFactories(factories);
        purchaseOrderCombos.setPayTerms(payTerms);
        return purchaseOrderCombos;
    }

    @Override
    public PurchaseOrderDetailsVo getPurchaseOrderDetailVoByFkPoNo(String fkPoNo) {
        PurchaseOrderDetailsVo purchaseOrderDetailVo = null;
        PurchaseOrder purchaseOrder = poMapper.getPurchaseOrderByFkPoNo(fkPoNo);
        if (purchaseOrder != null) {
            purchaseOrderDetailVo = new PurchaseOrderDetailsVo(purchaseOrder);
            List<PurchaseOrderItem> purchaseOrderItemList = poMapper.getPurchaseOrderItemListByFkPoNo(fkPoNo);
            if (purchaseOrderItemList != null) {
                Map<String, PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemListToMap(purchaseOrderItemList);
                purchaseOrderDetailVo.setPurchaseOrderItems(purchaseOrderItems);
            }
            List<PurchaseOrderCharge> purchaseOrderChargeList = poMapper.getPurchaseOrderChargeListByFkPoNo(fkPoNo);
            if (purchaseOrderChargeList != null) {
                Map<String, PurchaseOrderCharge> purchaseOrderCharges = purchaseOrderChargeListToMap(purchaseOrderChargeList);
                purchaseOrderDetailVo.setPurchaseOrderCharges(purchaseOrderCharges);
            }
//			PurchaseOrderSbk purchaseOrderSbk = poMapper.getPurchaseOrderSbkByFkPoNo(fkPoNo);
//			if(purchaseOrderSbk != null){
//				purchaseOrderDetailVo.setHaveSbk(true);
//				purchaseOrderDetailVo.setPurchaseOrderSbk(purchaseOrderSbk);
//			}
            List<PurchaseOrderSbk> purchaseOrderSbkList = poMapper.getPurchaseOrderSbkListByFkPoNo(fkPoNo);
            if (purchaseOrderSbkList != null) {
                Map<String, PurchaseOrderSbk> purchaseOrderSbks = purchaseOrderSbkListToMap(purchaseOrderSbkList);
                purchaseOrderDetailVo.setPurchaseOrderSbks(purchaseOrderSbks);
            }
//			PurchaseOrderLc purchaseOrderLc = poMapper.getPurchaseOrderLcByFkPoNo(fkPoNo);
//			if(purchaseOrderLc != null){
//				purchaseOrderDetailVo.setHaveLc(true);
//				purchaseOrderDetailVo.setPurchaseOrderLc(purchaseOrderLc);
//			}
            List<PurchaseOrderLc> purchaseOrderLcList = poMapper.getPurchaseOrderLcListByFkPoNo(fkPoNo);
            if (purchaseOrderLcList != null) {
                Map<String, PurchaseOrderLc> purchaseOrderLcs = purchaseOrderLcListToMap(purchaseOrderLcList);
                purchaseOrderDetailVo.setPurchaseOrderLcs(purchaseOrderLcs);
            }
        }
        return purchaseOrderDetailVo;
    }

    @Override
    public PurchaseOrderDetailsVo getPurchaseOrderDetailVoItemsByFkPoNo(String fkPoNo) {
        PurchaseOrderDetailsVo purchaseOrderDetailVo = null;
        PurchaseOrder purchaseOrder = poMapper.getPurchaseOrderByFkPoNo(fkPoNo);
        if (purchaseOrder != null) {
            purchaseOrderDetailVo = new PurchaseOrderDetailsVo(purchaseOrder);
            List<PurchaseOrderItem> purchaseOrderItemList = poMapper.getPurchaseOrderItemListByFkPoNo(fkPoNo);
            if (purchaseOrderItemList != null) {
                Map<String, PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemListToMap(purchaseOrderItemList);
                purchaseOrderDetailVo.setPurchaseOrderItems(purchaseOrderItems);
            }
        }
        return purchaseOrderDetailVo;
    }

    private Map<String, PurchaseOrderLc> purchaseOrderLcListToMap(
            List<PurchaseOrderLc> purchaseOrderLcList) {
        Map<String, PurchaseOrderLc> purchaseOrderLcs = null;
        if (purchaseOrderLcList != null && purchaseOrderLcList.size() > 0) {
            purchaseOrderLcs = new HashMap<String, PurchaseOrderLc>();
            for (int i = 0; i < purchaseOrderLcList.size(); i++) {
                PurchaseOrderLc purchaseOrderLc = purchaseOrderLcList.get(i);
                purchaseOrderLcs.put(purchaseOrderLc.getEncodePo4LcNo(), purchaseOrderLc);
            }
        }
        return purchaseOrderLcs;
    }

    private Map<String, PurchaseOrderSbk> purchaseOrderSbkListToMap(
            List<PurchaseOrderSbk> purchaseOrderSbkList) {
        Map<String, PurchaseOrderSbk> purchaseOrderSbks = null;
        if (purchaseOrderSbkList != null && purchaseOrderSbkList.size() > 0) {
            purchaseOrderSbks = new HashMap<String, PurchaseOrderSbk>();
            for (int i = 0; i < purchaseOrderSbkList.size(); i++) {
                PurchaseOrderSbk purchaseOrderSbk = purchaseOrderSbkList.get(i);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(purchaseOrderSbk.getPo3ExpShpDate());
                purchaseOrderSbks.put(dateString, purchaseOrderSbk);
            }
        }
        return purchaseOrderSbks;
    }

    private Map<String, PurchaseOrderCharge> purchaseOrderChargeListToMap(
            List<PurchaseOrderCharge> purchaseOrderChargeList) {
        Map<String, PurchaseOrderCharge> purchaseOrderCharges = null;
        if (purchaseOrderChargeList != null && purchaseOrderChargeList.size() > 0) {
            purchaseOrderCharges = new LinkedHashMap<String, PurchaseOrderCharge>();
            for (int i = 0; i < purchaseOrderChargeList.size(); i++) {
                PurchaseOrderCharge purchaseOrderCharge = purchaseOrderChargeList.get(i);
                purchaseOrderCharges.put(purchaseOrderCharge.getEncodePo5ChgCode(), purchaseOrderCharge);
            }
        }
        return purchaseOrderCharges;
    }

    private Map<String, PurchaseOrderItem> purchaseOrderItemListToMap(List<PurchaseOrderItem> purchaseOrderItemList) {
        Map<String, PurchaseOrderItem> purchaseOrderItems = null;
        if (purchaseOrderItemList != null && purchaseOrderItemList.size() > 0) {
            purchaseOrderItems = new LinkedHashMap<String, PurchaseOrderItem>();
            for (int i = 0; i < purchaseOrderItemList.size(); i++) {
                PurchaseOrderItem purchaseOrderItem = purchaseOrderItemList.get(i);
                purchaseOrderItems.put(purchaseOrderItem.getPo2ItCat() + purchaseOrderItem.getPo2ItNo() + purchaseOrderItem.getPo2ItSuffix(), purchaseOrderItem);
            }
        }
        return purchaseOrderItems;
    }

    @Override
    public boolean updatePurchaseOrder(PurchaseOrderDetailsVo currentPurchaseOrder) {
        try {
            Date date = new Date();
            currentPurchaseOrder.setModDate(date);
            PurchaseOrder purchaseOrder = (PurchaseOrder) currentPurchaseOrder;
            Integer flag = poMapper.updatePurchaseOrder(purchaseOrder);
            if (flag > 0) {
                String fkPoNo = currentPurchaseOrder.getPoNo();

                //清理Purchase Order Items再重新insert
                poMapper.deletePurchaseOrderItems(fkPoNo);
                Map<String, PurchaseOrderItem> purchaseOrderItems = currentPurchaseOrder.getPurchaseOrderItems();
                if (purchaseOrderItems != null && purchaseOrderItems.size() > 0) {
                    Iterator iter = purchaseOrderItems.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderItem purchaseOrderItem = purchaseOrderItems.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderItem.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderItem.setPo2No(currentPurchaseOrder.getPoNo());

                        purchaseOrderItem.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderItem.setCrtDate(date);
                        purchaseOrderItem.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderItem.setModDate(date);
                        if (purchaseOrderItem.getPo2SoNo().equals("")) {
                            purchaseOrderItem.setPo2SoNo("0000000000");
                        }
                        poMapper.insertPurchaseOrderItem(purchaseOrderItem);
                    }
                }

                //清理Purchase Order Sbk再重新insert
                poMapper.deletePurchaseOrderSbks(fkPoNo);
//				if(currentPurchaseOrder.isHaveSbk() && currentPurchaseOrder.getPurchaseOrderSbk() != null && currentPurchaseOrder.getPurchaseOrderSbk().getPo3ExpShpDate() != null){
//					PurchaseOrderSbk purchaseOrderSbk = currentPurchaseOrder.getPurchaseOrderSbk();
//					//从purchaseOrder获取cocode和sono
//					purchaseOrderSbk.setCoCode(currentPurchaseOrder.getCoCode());
//					purchaseOrderSbk.setPo3No(currentPurchaseOrder.getPoNo());
//
//					purchaseOrderSbk.setCrtUsr(currentPurchaseOrder.getCrtUsr());
//					purchaseOrderSbk.setCrtDate(date);
//					purchaseOrderSbk.setModUsr(currentPurchaseOrder.getModUsr());
//					purchaseOrderSbk.setModDate(date);
//					poMapper.insertPurchaseOrderSbk(purchaseOrderSbk);
//				}
                Map<String, PurchaseOrderSbk> purchaseOrderSbks = currentPurchaseOrder.getPurchaseOrderSbks();
                if (purchaseOrderSbks != null && purchaseOrderSbks.size() > 0) {
                    Iterator iter = purchaseOrderSbks.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderSbk purchaseOrderSbk = purchaseOrderSbks.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderSbk.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderSbk.setPo3No(currentPurchaseOrder.getPoNo());

                        purchaseOrderSbk.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderSbk.setCrtDate(date);
                        purchaseOrderSbk.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderSbk.setModDate(date);
                        poMapper.insertPurchaseOrderSbk(purchaseOrderSbk);
                    }
                }

                //清理Purchase Order Charge再重新insert
                poMapper.deletePurchaseOrderCharges(fkPoNo);
//				if(currentPurchaseOrder.isHaveCharge() && currentPurchaseOrder.getPurchaseOrderCharge() != null && !currentPurchaseOrder.getPurchaseOrderCharge().getPo5ChgCode().equals("")){
//					PurchaseOrderCharge purchaseOrderCharge = currentPurchaseOrder.getPurchaseOrderCharge();
//					//从purchaseOrder获取cocode和sono
//					purchaseOrderCharge.setCoCode(currentPurchaseOrder.getCoCode());
//					purchaseOrderCharge.setPo5No(currentPurchaseOrder.getPoNo());
//
//					purchaseOrderCharge.setCrtUsr(currentPurchaseOrder.getCrtUsr());
//					purchaseOrderCharge.setCrtDate(date);
//					purchaseOrderCharge.setModUsr(currentPurchaseOrder.getModUsr());
//					purchaseOrderCharge.setModDate(date);
//					poMapper.insertPurchaseOrderCharge(purchaseOrderCharge);
//				}
                Map<String, PurchaseOrderCharge> purchaseOrderCharges = currentPurchaseOrder.getPurchaseOrderCharges();
                if (purchaseOrderCharges != null && purchaseOrderCharges.size() > 0) {
                    Iterator iter = purchaseOrderCharges.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderCharge purchaseOrderCharge = purchaseOrderCharges.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderCharge.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderCharge.setPo5No(currentPurchaseOrder.getPoNo());

                        purchaseOrderCharge.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderCharge.setCrtDate(date);
                        purchaseOrderCharge.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderCharge.setModDate(date);
                        poMapper.insertPurchaseOrderCharge(purchaseOrderCharge);
                    }
                }

                //清理Purchase Order Lc再重新insert
                poMapper.deletePurchaseOrderLcs(fkPoNo);
//				if(currentPurchaseOrder.isHaveLc() && currentPurchaseOrder.getPurchaseOrderLc() != null && !currentPurchaseOrder.getPurchaseOrderLc().getPo4LcNo().equals("")){
//					PurchaseOrderLc purchaseOrderLc = currentPurchaseOrder.getPurchaseOrderLc();
//					//从purchaseOrder获取cocode和sono
//					purchaseOrderLc.setCoCode(currentPurchaseOrder.getCoCode());
//					purchaseOrderLc.setPo4No(currentPurchaseOrder.getPoNo());
//
//					purchaseOrderLc.setCrtUsr(currentPurchaseOrder.getCrtUsr());
//					purchaseOrderLc.setCrtDate(date);
//					purchaseOrderLc.setModUsr(currentPurchaseOrder.getModUsr());
//					purchaseOrderLc.setModDate(date);
//					poMapper.insertPurchaseOrderLc(purchaseOrderLc);
//				}
                Map<String, PurchaseOrderLc> purchaseOrderLcs = currentPurchaseOrder.getPurchaseOrderLcs();
                if (purchaseOrderLcs != null && purchaseOrderLcs.size() > 0) {
                    Iterator iter = purchaseOrderLcs.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderLc purchaseOrderLc = purchaseOrderLcs.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderLc.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderLc.setPo4No(currentPurchaseOrder.getPoNo());

                        purchaseOrderLc.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderLc.setCrtDate(date);
                        purchaseOrderLc.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderLc.setModDate(date);
                        poMapper.insertPurchaseOrderLc(purchaseOrderLc);
                    }
                }

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Checker checkPurchaseOrder(PurchaseOrderDetailsVo currentPurchaseOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currentPurchaseOrder != null) {
            if (currentPurchaseOrder.getPoNo() == null || currentPurchaseOrder.getPoNo().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写完整PurchaseOrder No., 保存失败");
            } else {
                Integer count = 0;
                count = poMapper.getCountByPurchaseOrder(currentPurchaseOrder);
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该PurchaseOrder No.已存在");
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkDecimal(PurchaseOrderDetailsVo currentPurchaseOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
//		if(currentSaleOrder != null){
//			if(currentSaleOrder.getOffShNoPfix() == null || currentSaleOrder.getOffShNoPfix().trim().equals("") || currentSaleOrder.getOffShNo() == null || currentSaleOrder.getOffShNo().trim().equals("")){
//				checker.setSuccess(false);
//				checker.setReturnStr("请填写完整SaleOrder No., 保存失败");
//			}else{
//				Integer count = 0;
//				count = poMapper.getCountBySaleOrder(currentSaleOrder);
//				if(count > 0){
//					checker.setSuccess(false);
//					checker.setReturnStr("该SaleOrder No.已存在");
//				}
//			}
//		}else{
//			checker.setSuccess(false);
//			checker.setReturnStr("信息为空,保存失败");
//		}
        return checker;
    }

    @Override
    public boolean addPurchaseOrder(PurchaseOrderDetailsVo currentPurchaseOrder) {
        try {
            Date date = new Date();
            currentPurchaseOrder.setCrtDate(date);
            currentPurchaseOrder.setModDate(date);
            PurchaseOrder purchaseOrder = (PurchaseOrder) currentPurchaseOrder;
            Integer flag = poMapper.insertPurchaseOrder(purchaseOrder);
            if (flag > 0) {
                //清理Purchase Order Items再重新insert
                Map<String, PurchaseOrderItem> purchaseOrderItems = currentPurchaseOrder.getPurchaseOrderItems();
                if (purchaseOrderItems != null && purchaseOrderItems.size() > 0) {
                    Iterator iter = purchaseOrderItems.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderItem purchaseOrderItem = purchaseOrderItems.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderItem.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderItem.setPo2No(currentPurchaseOrder.getPoNo());

                        purchaseOrderItem.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderItem.setCrtDate(date);
                        purchaseOrderItem.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderItem.setModDate(date);
                        if (purchaseOrderItem.getPo2SoNo() == null || purchaseOrderItem.getPo2SoNo().equals("")) {
                            purchaseOrderItem.setPo2SoNo("0000000000");
                        }
                        poMapper.insertPurchaseOrderItem(purchaseOrderItem);
                    }
                }

                //清理Purchase Order Charge再重新insert
//				if(currentPurchaseOrder.isHaveCharge() && currentPurchaseOrder.getPurchaseOrderCharge() != null && !currentPurchaseOrder.getPurchaseOrderCharge().getPo5ChgCode().equals("")){
//					PurchaseOrderCharge purchaseOrderCharge = currentPurchaseOrder.getPurchaseOrderCharge();
//					//从purchaseOrder获取cocode和sono
//					purchaseOrderCharge.setCoCode(currentPurchaseOrder.getCoCode());
//					purchaseOrderCharge.setPo5No(currentPurchaseOrder.getPoNo());
//
//					purchaseOrderCharge.setCrtUsr(currentPurchaseOrder.getCrtUsr());
//					purchaseOrderCharge.setCrtDate(date);
//					purchaseOrderCharge.setModUsr(currentPurchaseOrder.getModUsr());
//					purchaseOrderCharge.setModDate(date);
//					poMapper.insertPurchaseOrderCharge(purchaseOrderCharge);
//				}
                Map<String, PurchaseOrderCharge> purchaseOrderCharges = currentPurchaseOrder.getPurchaseOrderCharges();
                if (purchaseOrderCharges != null && purchaseOrderCharges.size() > 0) {
                    Iterator iter = purchaseOrderCharges.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderCharge purchaseOrderCharge = purchaseOrderCharges.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderCharge.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderCharge.setPo5No(currentPurchaseOrder.getPoNo());

                        purchaseOrderCharge.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderCharge.setCrtDate(date);
                        purchaseOrderCharge.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderCharge.setModDate(date);
                        poMapper.insertPurchaseOrderCharge(purchaseOrderCharge);
                    }
                }

                //清理Purchase Order Sbk再重新insert
//				if(currentPurchaseOrder.isHaveSbk() && currentPurchaseOrder.getPurchaseOrderSbk() != null && currentPurchaseOrder.getPurchaseOrderSbk().getPo3ExpShpDate() != null){
//					PurchaseOrderSbk purchaseOrderSbk = currentPurchaseOrder.getPurchaseOrderSbk();
//					//从purchaseOrder获取cocode和sono
//					purchaseOrderSbk.setCoCode(currentPurchaseOrder.getCoCode());
//					purchaseOrderSbk.setPo3No(currentPurchaseOrder.getPoNo());
//
//					purchaseOrderSbk.setCrtUsr(currentPurchaseOrder.getCrtUsr());
//					purchaseOrderSbk.setCrtDate(date);
//					purchaseOrderSbk.setModUsr(currentPurchaseOrder.getModUsr());
//					purchaseOrderSbk.setModDate(date);
//					poMapper.insertPurchaseOrderSbk(purchaseOrderSbk);
//				}
                Map<String, PurchaseOrderSbk> purchaseOrderSbks = currentPurchaseOrder.getPurchaseOrderSbks();
                if (purchaseOrderSbks != null && purchaseOrderSbks.size() > 0) {
                    Iterator iter = purchaseOrderSbks.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderSbk purchaseOrderSbk = purchaseOrderSbks.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderSbk.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderSbk.setPo3No(currentPurchaseOrder.getPoNo());

                        purchaseOrderSbk.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderSbk.setCrtDate(date);
                        purchaseOrderSbk.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderSbk.setModDate(date);
                        poMapper.insertPurchaseOrderSbk(purchaseOrderSbk);
                    }
                }

                //清理Purchase Order Lc再重新insert
//				if(currentPurchaseOrder.isHaveLc() && currentPurchaseOrder.getPurchaseOrderLc() != null && !currentPurchaseOrder.getPurchaseOrderLc().getPo4LcNo().equals("")){
//					PurchaseOrderLc purchaseOrderLc = currentPurchaseOrder.getPurchaseOrderLc();
//					//从purchaseOrder获取cocode和sono
//					purchaseOrderLc.setCoCode(currentPurchaseOrder.getCoCode());
//					purchaseOrderLc.setPo4No(currentPurchaseOrder.getPoNo());
//
//					purchaseOrderLc.setCrtUsr(currentPurchaseOrder.getCrtUsr());
//					purchaseOrderLc.setCrtDate(date);
//					purchaseOrderLc.setModUsr(currentPurchaseOrder.getModUsr());
//					purchaseOrderLc.setModDate(date);
//					poMapper.insertPurchaseOrderLc(purchaseOrderLc);
//				}
                Map<String, PurchaseOrderLc> purchaseOrderLcs = currentPurchaseOrder.getPurchaseOrderLcs();
                if (purchaseOrderLcs != null && purchaseOrderLcs.size() > 0) {
                    Iterator iter = purchaseOrderLcs.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        PurchaseOrderLc purchaseOrderLc = purchaseOrderLcs.get(key);
                        //从purchaseOrder获取cocode和sono
                        purchaseOrderLc.setCoCode(currentPurchaseOrder.getCoCode());
                        purchaseOrderLc.setPo4No(currentPurchaseOrder.getPoNo());

                        purchaseOrderLc.setCrtUsr(currentPurchaseOrder.getCrtUsr());
                        purchaseOrderLc.setCrtDate(date);
                        purchaseOrderLc.setModUsr(currentPurchaseOrder.getModUsr());
                        purchaseOrderLc.setModDate(date);
                        poMapper.insertPurchaseOrderLc(purchaseOrderLc);
                    }
                }

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String[] filterGetPurchaseOrderChargeCodes(String[] poChargeCodes,
                                                      Map<String, PurchaseOrderCharge> purchaseOrderCharges) {
        String[] getChargeCodes = null;
        if (poChargeCodes != null && poChargeCodes.length > 0) {
            if (purchaseOrderCharges == null || purchaseOrderCharges.size() < 1) {
                getChargeCodes = poChargeCodes;
            } else {
                List<String> getChargeCodesList = new ArrayList<String>();
                for (String poChargeCode : poChargeCodes) {
                    if (!purchaseOrderCharges.containsKey(poChargeCode)) {
                        getChargeCodesList.add(poChargeCode);
                    }
                }
                Integer getChargeCodesListCount = getChargeCodesList.size();
                if (getChargeCodesListCount > 0) {
                    getChargeCodes = new String[getChargeCodesListCount];
                    getChargeCodes = getChargeCodesList.toArray(getChargeCodes);
                }
            }
        }
        return getChargeCodes;
    }

    @Override
    public Map<String, PurchaseOrderCharge> getPurchaseOrderCharges(
            String[] poChargeCodes) {
        Map<String, PurchaseOrderCharge> purchaseOrderCharges = null;
        if (poChargeCodes != null && poChargeCodes.length > 0) {
            purchaseOrderCharges = new LinkedHashMap<String, PurchaseOrderCharge>();
            for (String poChargeCode : poChargeCodes) {
                Charge charge = chargeMapper.getChargeByFkChgCode(poChargeCode);
                if (charge != null && charge.getChgCode() != null) {
                    PurchaseOrderCharge purchaseOrderCharge = new PurchaseOrderCharge(charge);
                    purchaseOrderCharges.put(purchaseOrderCharge.getEncodePo5ChgCode(), purchaseOrderCharge);
                }
            }
        }
        return purchaseOrderCharges;
    }

    @Override
    public Map<String, PurchaseOrderCharge> updatePurchaseOrderCharges(
            Map<String, PurchaseOrderCharge> purchaseOrderCharges,
            Map<String, PurchaseOrderCharge> getPurchaseOrderCharges) {
        if (purchaseOrderCharges == null) {
            purchaseOrderCharges = new LinkedHashMap<String, PurchaseOrderCharge>();
            if (getPurchaseOrderCharges != null) {
                purchaseOrderCharges = getPurchaseOrderCharges;
            }
        } else {
            if (getPurchaseOrderCharges != null && getPurchaseOrderCharges.size() > 0) {
                Iterator iter = getPurchaseOrderCharges.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    if (!purchaseOrderCharges.containsKey(key)) {
                        PurchaseOrderCharge val = (PurchaseOrderCharge) entry.getValue();
                        purchaseOrderCharges.put(key, val);
                    }
                }
            }
        }
        return purchaseOrderCharges;
    }

    @Override
    public Map<String, PurchaseOrderCharge> removePoCharge(String poChargeCode,
                                                           Map<String, PurchaseOrderCharge> purchaseOrderCharges) {
        if (purchaseOrderCharges != null && purchaseOrderCharges.size() > 0) {
            purchaseOrderCharges.remove(poChargeCode);
        }
        return purchaseOrderCharges;
    }

    @Override
    public Checker checkPurchaseOrderCharge(
            PurchaseOrderCharge purchaseOrderCharge,
            PurchaseOrderDetailsVo currentPurchaseOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (purchaseOrderCharge != null) {
            if (purchaseOrderCharge.getPo5ChgCode() == null || purchaseOrderCharge.getPo5ChgCode().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Chg/Ded Code, 保存失败");
            } else {
                if (currentPurchaseOrder.getPurchaseOrderCharges() != null) {
                    if (currentPurchaseOrder.getPurchaseOrderCharges().containsKey(purchaseOrderCharge.getEncodePo5ChgCode())) {
                        checker.setSuccess(false);
                        checker.setReturnStr("该Purchase Order 已含有此Chg/Ded Code");
                    }
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkPurchaseOrderChargeDecimal(
            PurchaseOrderCharge purchaseOrderCharge) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (purchaseOrderCharge != null) {
            if (purchaseOrderCharge.getPo5ChgRate() != null) {
                if (purchaseOrderCharge.getPo5ChgRate().doubleValue() < -9999.99999 || purchaseOrderCharge.getPo5ChgRate().doubleValue() > 9999.99999) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Rate(%)必须在[-9999.99999, 9999.99999]范围内");
                    return checker;
                }
            }
            if (purchaseOrderCharge.getPo5ChgAmt() != null) {
                if (purchaseOrderCharge.getPo5ChgAmt().doubleValue() < -9999999.99 || purchaseOrderCharge.getPo5ChgAmt().doubleValue() > 9999999.99) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Amount必须在[-9999999.99, 9999999.99]范围内");
                    return checker;
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkPurchaseOrderSbk(PurchaseOrderSbk purchaseOrderSbk,
                                         PurchaseOrderDetailsVo currentPurchaseOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (purchaseOrderSbk != null) {
            if (purchaseOrderSbk.getPo3ExpShpDate() == null || purchaseOrderSbk.getPo3ExpShpDate().toString().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Expected ship. Date, 保存失败");
            } else {
                if (currentPurchaseOrder.getPurchaseOrderSbks() != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter.format(purchaseOrderSbk.getPo3ExpShpDate());
                    if (currentPurchaseOrder.getPurchaseOrderSbks().containsKey(dateString)) {
                        checker.setSuccess(false);
                        checker.setReturnStr("该Purchase Order 已含有此Expected ship. Date");
                    }
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkPurchaseOrderSbkDecimal(
            PurchaseOrderSbk purchaseOrderSbk) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (purchaseOrderSbk != null) {
            if (purchaseOrderSbk.getPo3ExpShpQty() != null) {
                if (purchaseOrderSbk.getPo3ExpShpQty().doubleValue() < -99999999.99 || purchaseOrderSbk.getPo3ExpShpQty().doubleValue() > 99999999.99) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Expected ship. Qty必须在[-99999999.99, 99999999.99]范围内");
                    return checker;
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Map<String, PurchaseOrderSbk> removePoSbk(String fkExpShpDate,
                                                     Map<String, PurchaseOrderSbk> purchaseOrderSbks) {
        if (purchaseOrderSbks != null && purchaseOrderSbks.size() > 0) {
            purchaseOrderSbks.remove(fkExpShpDate);
        }
        return purchaseOrderSbks;
    }

    @Override
    public Checker checkPurchaseOrderLc(PurchaseOrderLc purchaseOrderLc,
                                        PurchaseOrderDetailsVo currentPurchaseOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (purchaseOrderLc != null) {
            if (purchaseOrderLc.getPo4LcNo() == null || purchaseOrderLc.getPo4LcNo().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Chg/Ded Code, 保存失败");
            } else {
                if (currentPurchaseOrder.getPurchaseOrderLcs() != null) {
                    if (currentPurchaseOrder.getPurchaseOrderLcs().containsKey(purchaseOrderLc.getEncodePo4LcNo())) {
                        checker.setSuccess(false);
                        checker.setReturnStr("该Purchase Order 已含有此L/C No.");
                    }
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkPurchaseOrderLcDecimal(PurchaseOrderLc purchaseOrderLc) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (purchaseOrderLc != null) {
            if (purchaseOrderLc.getPo4LcAmt() != null) {
                if (purchaseOrderLc.getPo4LcAmt().doubleValue() < -99999999.99 || purchaseOrderLc.getPo4LcAmt().doubleValue() > 99999999.99) {
                    checker.setSuccess(false);
                    checker.setReturnStr("L/C Amount必须在[-99999999.99, 99999999.99]范围内");
                    return checker;
                }
            }
            if (purchaseOrderLc.getPo4PaidAmt() != null) {
                if (purchaseOrderLc.getPo4PaidAmt().doubleValue() < -99999999.99 || purchaseOrderLc.getPo4PaidAmt().doubleValue() > 99999999.99) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Paid Amount必须在[-99999999.99, 99999999.99]范围内");
                    return checker;
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Map<String, PurchaseOrderLc> removePoLc(String fkLcNo,
                                                   Map<String, PurchaseOrderLc> purchaseOrderLcs) {
        if (purchaseOrderLcs != null && purchaseOrderLcs.size() > 0) {
            purchaseOrderLcs.remove(fkLcNo);
        }
        return purchaseOrderLcs;
    }

    @Override
    public Map<String, PurchaseOrderItem> removeSoItem(String poItCatNoSuffix,
                                                       Map<String, PurchaseOrderItem> purchaseOrderItems) {
        if (purchaseOrderItems != null && purchaseOrderItems.size() > 0) {
            purchaseOrderItems.remove(poItCatNoSuffix);
        }
        return purchaseOrderItems;
    }

    @Override
    public List<PoItemMasterVo> getItemListByCondition(
            PoSearchItemConditionVo vo) {
        List<PoItemMasterVo> itemVoList = null;
        if (vo.getModule() == null || vo.getModule().equals("") || vo.getModule().equals("item")) {
            itemVoList = itemMapper.getPoItemListByCondition(vo);
        } else if (vo.getModule().equals("offersheet")) {
            itemVoList = itemMapper.getPoItemListFromOffNoByCondition(vo);
        } else if (vo.getModule().equals("so")) {
            itemVoList = itemMapper.getPoItemListFromSoNoByCondition(vo);
        } else if (vo.getModule().equals("po")) {
            itemVoList = itemMapper.getPoItemListFromPoNoByCondition(vo);
        }
        return itemVoList;
    }

    @Override
    public String[] filterGetPoItemIds(String[] poItemIds,
                                       Map<String, PurchaseOrderItem> purchaseOrderItems) {
        String[] getItemIds = null;
        if (poItemIds != null && poItemIds.length > 0) {
            List<String> getItemIdsList = new ArrayList<String>();
            if (purchaseOrderItems == null) purchaseOrderItems = new LinkedHashMap<String, PurchaseOrderItem>();
            for (String poItemId : poItemIds) {
                String[] poItemIdArray = poItemId.split("#");
                String itemId = poItemIdArray[3];
                if (!purchaseOrderItems.containsKey(itemId)) {
                    getItemIdsList.add(poItemId);
                }
            }
            Integer getItemIdsListCount = getItemIdsList.size();
            if (getItemIdsListCount > 0) {
                getItemIds = new String[getItemIdsListCount];
                getItemIds = getItemIdsList.toArray(getItemIds);
            }
        }
        return getItemIds;
    }

    @Override
    public Map<String, PurchaseOrderItem> getPurchaseOrderItems(String module,
                                                                String[] prePoItemIds) {
        Map<String, PurchaseOrderItem> purchaseOrderItems = null;
        if (prePoItemIds != null && prePoItemIds.length > 0) {
            purchaseOrderItems = returnPurchaseOrderItems(module, prePoItemIds);
        }
        return purchaseOrderItems;
    }

    private Map<String, PurchaseOrderItem> returnPurchaseOrderItems(
            String module, String[] poItemIds) {
        Map<String, PurchaseOrderItem> purchaseOrderItems = new LinkedHashMap<String, PurchaseOrderItem>();
        if (module.equals("item")) {
            for (String poItemId : poItemIds) {
                String[] poItemIdArray = poItemId.split("#");
                String itemId = poItemIdArray[3];
                ItemMaster itemMaster = itemMapper.getItemMasterByItCatNoSuffix(itemId);
                if (itemMaster != null && itemMaster.getId() != null) {
                    PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem(itemMaster);
                    purchaseOrderItems.put(itemMaster.getItCatNoSuffix(), purchaseOrderItem);
                }
            }
        } else if (module.equals("offersheet")) {
            for (String poItemId : poItemIds) {
                String[] poItemIdArray = poItemId.split("#");
                String offPreNo = poItemIdArray[2];
                String[] offPreNoArray = offPreNo.split("\\^");
                String offPre = offPreNoArray[0];
                String offNo = offPreNoArray[1];
                String itemId = poItemIdArray[3];
                if (itemId.length() == 9) {
                    String itCat = itemId.substring(0, 2);
                    String itNo = itemId.substring(2, 7);
                    String itSuffix = itemId.substring(7, 9);
                    PoItemMasterVo purchaseOrderItemSearch = new PoItemMasterVo(offPre, offNo, itCat, itNo, itSuffix, "offersheet");
                    OfferSheetItem offerSheetItem = itemMapper.getOffItemByOffItem(purchaseOrderItemSearch);
                    if (offerSheetItem != null) {
                        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem(offerSheetItem);
                        purchaseOrderItems.put(purchaseOrderItem.getPo2ItCat() + purchaseOrderItem.getPo2ItNo() + purchaseOrderItem.getPo2ItSuffix(), purchaseOrderItem);
                    }
                }
            }
        } else if (module.equals("so")) {
            for (String poItemId : poItemIds) {
                String[] poItemIdArray = poItemId.split("#");
                String soNo = poItemIdArray[1];
                String itemId = poItemIdArray[3];
                if (itemId.length() == 9) {
                    String itCat = itemId.substring(0, 2);
                    String itNo = itemId.substring(2, 7);
                    String itSuffix = itemId.substring(7, 9);
                    PoItemMasterVo purchaseOrderItemSearch = new PoItemMasterVo(soNo, itCat, itNo, itSuffix, "so");
                    SaleOrderItem saleOrderItem = itemMapper.getSoItemBySoItem(purchaseOrderItemSearch);
                    if (saleOrderItem != null) {
                        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem(saleOrderItem);
                        purchaseOrderItems.put(purchaseOrderItem.getPo2ItCat() + purchaseOrderItem.getPo2ItNo() + purchaseOrderItem.getPo2ItSuffix(), purchaseOrderItem);
                    }
                }
            }
        } else if (module.equals("po")) {
            for (String poItemId : poItemIds) {
                String[] poItemIdArray = poItemId.split("#");
                String poNo = poItemIdArray[0];
                String itemId = poItemIdArray[3];
                if (itemId.length() == 9) {
                    String itCat = itemId.substring(0, 2);
                    String itNo = itemId.substring(2, 7);
                    String itSuffix = itemId.substring(7, 9);
                    PoItemMasterVo purchaseOrderItemSearch = new PoItemMasterVo(poNo, itCat, itNo, itSuffix, "po");
                    PurchaseOrderItem purchaseOrderItem = itemMapper.getPoItemByPoItem(purchaseOrderItemSearch);
                    if (purchaseOrderItem != null) {
                        purchaseOrderItems.put(purchaseOrderItem.getPo2ItCat() + purchaseOrderItem.getPo2ItNo() + purchaseOrderItem.getPo2ItSuffix(), purchaseOrderItem);
                    }
                }
            }
        }
        return purchaseOrderItems;
    }

    @Override
    public Map<String, PurchaseOrderItem> updatePurchaseOrderItems(
            Map<String, PurchaseOrderItem> purchaseOrderItems,
            Map<String, PurchaseOrderItem> getPurchaseOrderItems) {
        if (purchaseOrderItems == null) {
            purchaseOrderItems = new LinkedHashMap<String, PurchaseOrderItem>();
            if (getPurchaseOrderItems != null) {
                purchaseOrderItems = getPurchaseOrderItems;
            }
        } else {
            if (getPurchaseOrderItems != null && getPurchaseOrderItems.size() > 0) {
                Iterator iter = getPurchaseOrderItems.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    if (!purchaseOrderItems.containsKey(key)) {
                        PurchaseOrderItem val = (PurchaseOrderItem) entry.getValue();
                        purchaseOrderItems.put(key, val);
                    }
                }
            }
        }
        return purchaseOrderItems;
    }

    @Override
    public void getPurchaseOrderBySelectSoId(PurchaseOrderDetailsVo currentPurchaseOrder, String selectSoId) {
        SaleOrderDetailsVo saleOrder = saleOrderMgr.getSaleOrderDetailVoByFkSoNo(selectSoId);
        if (saleOrder != null) {
            getValuesFromSo(currentPurchaseOrder, saleOrder);
        }
    }

    private void getValuesFromSo(PurchaseOrderDetailsVo currentPurchaseOrder,
                                 SaleOrderDetailsVo saleOrder) {
        currentPurchaseOrder.setPoNo(saleOrder.getSoNo());
        currentPurchaseOrder.setPoCnf(saleOrder.getSoCnf());
        currentPurchaseOrder.setPoCnfPort(saleOrder.getSoCnfPort());
        currentPurchaseOrder.setPoContReq(saleOrder.getSoContReq());
        currentPurchaseOrder.setPoCurr(saleOrder.getSoCurr());
        currentPurchaseOrder.setPoDelDetails(saleOrder.getSoDelDetails());
        currentPurchaseOrder.setPoDepDate(saleOrder.getSoDepDate());
        currentPurchaseOrder.setPoDepPaid(saleOrder.getSoDepPaid());
        currentPurchaseOrder.setPoDepRatio(saleOrder.getSoDepRatio());
        currentPurchaseOrder.setPoDest(saleOrder.getSoDest());
        currentPurchaseOrder.setPoEtdDate(saleOrder.getSoEtd());
        currentPurchaseOrder.setPoFob(saleOrder.getSoFob());
        currentPurchaseOrder.setPoFobPort(saleOrder.getSoFobPort());
        currentPurchaseOrder.setPoLshpDate(saleOrder.getSoLshpDate());
        currentPurchaseOrder.setPoSoNoRef(saleOrder.getSoNo());
        currentPurchaseOrder.setPoRouting(saleOrder.getSoRouting());
        if (saleOrder.getSaleOrderItems() != null && saleOrder.getSaleOrderItems().size() > 0) {
            currentPurchaseOrder.setPurchaseOrderItems(saleOrderItemsToPurchaseOrderItems(saleOrder.getSaleOrderItems()));
        }
        if (saleOrder.getSaleOrderLcs() != null && saleOrder.getSaleOrderLcs().size() > 0) {
            currentPurchaseOrder.setPurchaseOrderLcs(saleOrderLcsToPurchaseOrderLcs(saleOrder.getSaleOrderLcs()));
        }
        if (saleOrder.getSaleOrderCharges() != null && saleOrder.getSaleOrderCharges().size() > 0) {
            currentPurchaseOrder.setPurchaseOrderCharges(saleOrderChargesToPurchaseOrderCharges(saleOrder.getSaleOrderCharges()));
        }
        if (saleOrder.getSaleOrderSbks() != null && saleOrder.getSaleOrderSbks().size() > 0) {
            currentPurchaseOrder.setPurchaseOrderSbks(saleOrderSbksToPurchaseOrderSbks(saleOrder.getSaleOrderSbks()));
        }
    }

    private Map<String, PurchaseOrderSbk> saleOrderSbksToPurchaseOrderSbks(
            Map<String, SaleOrderSbk> saleOrderSbks) {
        Map<String, PurchaseOrderSbk> getPurchaseOrderSbks = new HashMap<String, PurchaseOrderSbk>();
        Iterator iter = saleOrderSbks.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderSbk saleOrderSbk = saleOrderSbks.get(key);
            PurchaseOrderSbk getPurchaseOrderSbk = new PurchaseOrderSbk(saleOrderSbk);
            getPurchaseOrderSbks.put(key, getPurchaseOrderSbk);
        }
        return getPurchaseOrderSbks;
    }

    private Map<String, PurchaseOrderCharge> saleOrderChargesToPurchaseOrderCharges(
            Map<String, SaleOrderCharge> saleOrderCharges) {
        Map<String, PurchaseOrderCharge> getPurchaseOrderCharges = new HashMap<String, PurchaseOrderCharge>();
        Iterator iter = saleOrderCharges.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderCharge saleOrderCharge = saleOrderCharges.get(key);
            PurchaseOrderCharge getPurchaseOrderCharge = new PurchaseOrderCharge(saleOrderCharge);
            getPurchaseOrderCharges.put(key, getPurchaseOrderCharge);
        }
        return getPurchaseOrderCharges;
    }

    private Map<String, PurchaseOrderLc> saleOrderLcsToPurchaseOrderLcs(
            Map<String, SaleOrderLc> saleOrderLcs) {
        Map<String, PurchaseOrderLc> getPurchaseOrderLcs = new HashMap<String, PurchaseOrderLc>();
        Iterator iter = saleOrderLcs.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderLc saleOrderLc = saleOrderLcs.get(key);
            PurchaseOrderLc getPurchaseOrderLc = new PurchaseOrderLc(saleOrderLc);
            getPurchaseOrderLcs.put(key, getPurchaseOrderLc);
        }
        return getPurchaseOrderLcs;
    }

    private Map<String, PurchaseOrderItem> saleOrderItemsToPurchaseOrderItems(
            Map<String, SaleOrderItem> saleOrderItems) {
        Map<String, PurchaseOrderItem> getPurchaseOrderItems = new HashMap<String, PurchaseOrderItem>();
        Iterator iter = saleOrderItems.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            SaleOrderItem saleOrderItem = saleOrderItems.get(key);
            PurchaseOrderItem getPurchaseOrderItem = new PurchaseOrderItem(saleOrderItem);
            getPurchaseOrderItems.put(key, getPurchaseOrderItem);
        }
        return getPurchaseOrderItems;
    }

    @Override
    public boolean delPurchaseOrderByFkPoNo(String fkPoNo) {
        try {
            //清理Sale Order Items
            poMapper.deletePurchaseOrderItems(fkPoNo);
            //清理Sale Order Charge
            poMapper.deletePurchaseOrderCharges(fkPoNo);
            //清理Sale Order Sbk
            poMapper.deletePurchaseOrderSbks(fkPoNo);
            //清理Sale Order Lc
            poMapper.deletePurchaseOrderLcs(fkPoNo);
            //清理Sale Order
            poMapper.deletePurchaseOrder(fkPoNo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void clearPo(PurchaseOrderDetailsVo currentPurchaseOrder) {
        currentPurchaseOrder.setPoNo(null);
    }

    @Override
    public void updateItemAmount(PurchaseOrderDetailsVo currentPurchaseOrder) {
        if (currentPurchaseOrder != null) {
            Map<String, PurchaseOrderItem> purchaseOrderItems = currentPurchaseOrder.getPurchaseOrderItems();
            if (purchaseOrderItems != null && purchaseOrderItems.size() > 0) {
                List<BigDecimal> purchaseOrderItemAmounts = new ArrayList<BigDecimal>();
                Iterator iter = purchaseOrderItems.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem) entry.getValue();
                    BigDecimal purchaseOrderItemAmount = purchaseOrderItem.getPo2Amt();
                    if (purchaseOrderItemAmount != null) {
                        purchaseOrderItemAmounts.add(purchaseOrderItemAmount);
                    }
                }
                BigDecimal purchaseOrderItemAmountTotal = sumPoAmount(purchaseOrderItemAmounts);
                currentPurchaseOrder.setPoOrdTotAmt(purchaseOrderItemAmountTotal);
                BigDecimal purchaseOrderChargeAmountTotal = currentPurchaseOrder.getPoOrdTotChg();
                BigDecimal purchaseOrderTotNet = sumPoTotNet(purchaseOrderItemAmountTotal, purchaseOrderChargeAmountTotal);
                currentPurchaseOrder.setPoOrdTotNet(purchaseOrderTotNet);
            } else {
                BigDecimal purchaseOrderItemAmountTotal = new BigDecimal(0);
                currentPurchaseOrder.setPoOrdTotAmt(purchaseOrderItemAmountTotal);
                BigDecimal purchaseOrderChargeAmountTotal = currentPurchaseOrder.getPoOrdTotChg();
                BigDecimal purchaseOrderTotNet = sumPoTotNet(purchaseOrderItemAmountTotal, purchaseOrderChargeAmountTotal);
                currentPurchaseOrder.setPoOrdTotNet(purchaseOrderTotNet);
            }
        }
    }

    private BigDecimal sumPoTotNet(BigDecimal purchaseOrderItemAmountTotal,
                                   BigDecimal purchaseOrderChargeAmountTotal) {
        if (purchaseOrderItemAmountTotal != null) {
            if (purchaseOrderChargeAmountTotal != null) {
                return purchaseOrderItemAmountTotal.add(purchaseOrderChargeAmountTotal);
            } else {
                return purchaseOrderItemAmountTotal;
            }
        } else {
            if (purchaseOrderChargeAmountTotal != null) {
                return purchaseOrderChargeAmountTotal;
            } else {
                return null;
            }
        }
    }

    private BigDecimal sumPoAmount(List<BigDecimal> purchaseOrderAmounts) {
        BigDecimal purchaseOrderAmountTotal = new BigDecimal("0");
        if (purchaseOrderAmounts != null && purchaseOrderAmounts.size() > 0) {
            for (BigDecimal purchaseOrderAmount : purchaseOrderAmounts) {
                if (purchaseOrderAmount != null) {
                    purchaseOrderAmountTotal = purchaseOrderAmountTotal.add(purchaseOrderAmount);
                }
            }
        }
        return purchaseOrderAmountTotal;
    }

    @Override
    public void updateChargeAmount(PurchaseOrderDetailsVo currentPurchaseOrder) {
        if (currentPurchaseOrder != null) {
            Map<String, PurchaseOrderCharge> purchaseOrderCharges = currentPurchaseOrder.getPurchaseOrderCharges();
            if (purchaseOrderCharges != null && purchaseOrderCharges.size() > 0) {
                List<BigDecimal> purchaseOrderChargeAmounts = new ArrayList<BigDecimal>();
                Iterator iter = purchaseOrderCharges.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    PurchaseOrderCharge purchaseOrderCharge = (PurchaseOrderCharge) entry.getValue();
                    BigDecimal purchaseOrderChargeAmount = purchaseOrderCharge.getPo5ChgAmt();
                    if (purchaseOrderChargeAmount != null) {
                        purchaseOrderChargeAmounts.add(purchaseOrderChargeAmount);
                    }
                }
                BigDecimal purchaseOrderChargeAmountTotal = sumPoAmount(purchaseOrderChargeAmounts);
                currentPurchaseOrder.setPoOrdTotChg(purchaseOrderChargeAmountTotal);
                BigDecimal purchaseOrderItemAmountTotal = currentPurchaseOrder.getPoOrdTotAmt();
                BigDecimal purchaseOrderTotNet = sumPoTotNet(purchaseOrderItemAmountTotal, purchaseOrderChargeAmountTotal);
                currentPurchaseOrder.setPoOrdTotNet(purchaseOrderTotNet);
            } else {
                BigDecimal purchaseOrderChargeAmountTotal = new BigDecimal(0);
                currentPurchaseOrder.setPoOrdTotChg(purchaseOrderChargeAmountTotal);
                BigDecimal purchaseOrderItemAmountTotal = currentPurchaseOrder.getPoOrdTotAmt();
                BigDecimal purchaseOrderTotNet = sumPoTotNet(purchaseOrderItemAmountTotal, purchaseOrderChargeAmountTotal);
                currentPurchaseOrder.setPoOrdTotNet(purchaseOrderTotNet);
            }
        }
    }

}
