package sop.services.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.mapper.ChargeMapper;
import dwz.persistence.mapper.CurrencyMapper;
import dwz.persistence.mapper.CustomerMapper;
import dwz.persistence.mapper.FactoryMapper;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.PayTermMapper;
import dwz.persistence.mapper.SaleOrderMapper;
import dwz.persistence.mapper.StaffMapper;
import sop.persistence.beans.Charge;
import sop.persistence.beans.Currency;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.OfferSheetItem;
import sop.persistence.beans.SaleOrder;
import sop.persistence.beans.SaleOrderCharge;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.SaleOrderLc;
import sop.persistence.beans.SaleOrderSbk;
import sop.services.SaleOrderServiceMgr;
import sop.vo.CustomerVo;
import sop.vo.FactoryVo;
import sop.vo.PayTermVo;
import sop.vo.SaleOrderCombos;
import sop.vo.SaleOrderDetailsVo;
import sop.vo.SaleOrderVo;
import sop.vo.SoItemMasterVo;
import sop.vo.SoPoConditionVo;
import sop.vo.SoSearchItemConditionVo;
import sop.vo.StaffVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:52
 * @Package: sop.services.impl
 */

@Service(SaleOrderServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class SaleOrderServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements SaleOrderServiceMgr {

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CurrencyMapper currMapper;

    @Autowired
    private FactoryMapper factoryMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private PayTermMapper payTermMapper;

    @Autowired
    private ChargeMapper chargeMapper;

    @Override
    public SaleOrderCombos getSaleOrderCombos() {
        SaleOrderCombos saleOrderCombos = new SaleOrderCombos();
        List<CustomerVo> customers = customerMapper.getAllCustomers();
        List<Currency> currs = currMapper.getAllCurrs();
        List<FactoryVo> factories = factoryMapper.getAllFactories();
        List<PayTermVo> payTerms = payTermMapper.getAllPayTermVo();
        List<StaffVo> staffs = staffMapper.getAllStaffs();
        saleOrderCombos.setCustomers(customers);
        saleOrderCombos.setCurrs(currs);
        saleOrderCombos.setFactories(factories);
        saleOrderCombos.setPayTerms(payTerms);
        saleOrderCombos.setStaffs(staffs);
        return saleOrderCombos;
    }

    @Override
    public List<SoItemMasterVo> getItemListByCondition(SoSearchItemConditionVo vo) {
        List<SoItemMasterVo> itemVoList = null;
        if (vo.getModule() == null || vo.getModule().equals("") || vo.getModule().equals("item")) {
            itemVoList = itemMapper.getSoItemListByCondition(vo);
        } else if (vo.getModule().equals("offersheet")) {
            itemVoList = itemMapper.getSoItemListFromOffNoByCondition(vo);
        } else {
            itemVoList = itemMapper.getSoItemListFromSoNoByCondition(vo);
        }
        return itemVoList;
    }

    @Override
    public Map<String, SaleOrderItem> getSaleOrderItems(String[] offItemIds) {
        Map<String, SaleOrderItem> saleOrderItems = null;
//		if(offItemIds != null && offItemIds.length > 0){
//			saleOrderItems = new LinkedHashMap<String, SaleOrderItem>();
//			for (String offItemId : offItemIds) {
//				ItemMaster itemMaster = itemMapper.getItemMasterByItCatNoSuffix(offItemId);
//				if(itemMaster != null && itemMaster.getId() != null){
//					SaleOrderItem saleOrderItem = new SaleOrderItem(itemMaster);
//					saleOrderItems.put(itemMaster.getItCatNoSuffix(), saleOrderItem);
//				}
//			}
//		}
        return saleOrderItems;
    }

    @Override
    public List<SaleOrderVo> searchSaleOrderVo(SoPoConditionVo vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<SaleOrderVo> saleOrderList = saleOrderMapper.findPageBreakByCondition(vo,
                rb);
        return saleOrderList;
    }

    @Override
    public Integer searchSaleOrderVoNum(SoPoConditionVo vo) {
        Integer count = saleOrderMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public Map<String, SaleOrderItem> updateSaleOrderItems(
            Map<String, SaleOrderItem> saleOrderItems,
            Map<String, SaleOrderItem> getSaleOrderItems) {
        if (saleOrderItems == null) {
            saleOrderItems = new LinkedHashMap<String, SaleOrderItem>();
            if (getSaleOrderItems != null) {
                saleOrderItems = getSaleOrderItems;
            }
        } else {
            if (getSaleOrderItems != null && getSaleOrderItems.size() > 0) {
                Iterator iter = getSaleOrderItems.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    if (!saleOrderItems.containsKey(key)) {
                        SaleOrderItem val = (SaleOrderItem) entry.getValue();
                        saleOrderItems.put(key, val);
                    }
                }
            }
        }
        return saleOrderItems;
    }

    @Override
    public Checker checkSaleOrder(SaleOrderDetailsVo currentSaleOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currentSaleOrder != null) {
            if (currentSaleOrder.getSoNo() == null || currentSaleOrder.getSoNo().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写完整SaleOrder No., 保存失败");
            } else {
                Integer count = 0;
                count = saleOrderMapper.getCountBySaleOrder(currentSaleOrder);
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该SaleOrder No.已存在");
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean addSaleOrder(SaleOrderDetailsVo currentSaleOrder) throws ServiceException {
        try {
            Date date = new Date();
            currentSaleOrder.setCrtDate(date);
            currentSaleOrder.setModDate(date);
            SaleOrder saleOrder = (SaleOrder) currentSaleOrder;
            Integer flag = saleOrderMapper.insertSaleOrder(saleOrder);
            if (flag > 0) {
                //Sale Order Items insert
                Map<String, SaleOrderItem> saleOrderItems = currentSaleOrder.getSaleOrderItems();
                if (saleOrderItems != null && saleOrderItems.size() > 0) {
                    Iterator iter = saleOrderItems.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderItem saleOrderItem = saleOrderItems.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderItem.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderItem.setSo2No(currentSaleOrder.getSoNo());

                        saleOrderItem.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderItem.setCrtDate(date);
                        saleOrderItem.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderItem.setModDate(date);
                        if (saleOrderItem.getSo2OffShNo().equals("")) {
                            saleOrderItem.setSo2OffShNo("0000000000");
                        }
                        if (saleOrderItem.getSo2OffShNoPfix().equals("")) {
                            saleOrderItem.setSo2OffShNoPfix("0000");
                        }
                        saleOrderMapper.insertSaleOrderItem(saleOrderItem);
                    }
                }

                //Sale Order Charge insert
//				if(currentSaleOrder.isHaveCharge() && currentSaleOrder.getSaleOrderCharge() != null && !currentSaleOrder.getSaleOrderCharge().getSo5ChgCode().equals("")){
//					SaleOrderCharge saleOrderCharge = currentSaleOrder.getSaleOrderCharge();
//					//从saleOrder获取cocode和sono
//					saleOrderCharge.setCoCode(currentSaleOrder.getCoCode());
//					saleOrderCharge.setSo5No(currentSaleOrder.getSoNo());
//
//					saleOrderCharge.setCrtUsr(currentSaleOrder.getCrtUsr());
//					saleOrderCharge.setCrtDate(date);
//					saleOrderCharge.setModUsr(currentSaleOrder.getModUsr());
//					saleOrderCharge.setModDate(date);
//					saleOrderMapper.insertSaleOrderCharge(saleOrderCharge);
//				}
                Map<String, SaleOrderCharge> saleOrderCharges = currentSaleOrder.getSaleOrderCharges();
                if (saleOrderCharges != null && saleOrderCharges.size() > 0) {
                    Iterator iter = saleOrderCharges.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderCharge saleOrderCharge = saleOrderCharges.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderCharge.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderCharge.setSo5No(currentSaleOrder.getSoNo());

                        saleOrderCharge.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderCharge.setCrtDate(date);
                        saleOrderCharge.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderCharge.setModDate(date);
                        saleOrderMapper.insertSaleOrderCharge(saleOrderCharge);
                        saleOrderMapper.insertSaleOrderCharge(saleOrderCharge);
                    }
                }

                //Sale Order Sbk insert
//				if(currentSaleOrder.isHaveSbk() && currentSaleOrder.getSaleOrderSbk() != null && currentSaleOrder.getSaleOrderSbk().getSo3ExpShpDate() != null){
//					SaleOrderSbk saleOrderSbk = currentSaleOrder.getSaleOrderSbk();
//					//从saleOrder获取cocode和sono
//					saleOrderSbk.setCoCode(currentSaleOrder.getCoCode());
//					saleOrderSbk.setSo3No(currentSaleOrder.getSoNo());
//
//					saleOrderSbk.setCrtUsr(currentSaleOrder.getCrtUsr());
//					saleOrderSbk.setCrtDate(date);
//					saleOrderSbk.setModUsr(currentSaleOrder.getModUsr());
//					saleOrderSbk.setModDate(date);
//					saleOrderMapper.insertSaleOrderSbk(saleOrderSbk);
//				}
                Map<String, SaleOrderSbk> saleOrderSbks = currentSaleOrder.getSaleOrderSbks();
                if (saleOrderSbks != null && saleOrderSbks.size() > 0) {
                    Iterator iter = saleOrderSbks.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderSbk saleOrderSbk = saleOrderSbks.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderSbk.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderSbk.setSo3No(currentSaleOrder.getSoNo());

                        saleOrderSbk.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderSbk.setCrtDate(date);
                        saleOrderSbk.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderSbk.setModDate(date);
                        saleOrderMapper.insertSaleOrderSbk(saleOrderSbk);
                    }
                }

                //Sale Order Lc insert
//				if(currentSaleOrder.isHaveLc() && currentSaleOrder.getSaleOrderLc() != null && !currentSaleOrder.getSaleOrderLc().getSo4LcNo().equals("")){
//					SaleOrderLc saleOrderLc = currentSaleOrder.getSaleOrderLc();
//					//从saleOrder获取cocode和sono
//					saleOrderLc.setCoCode(currentSaleOrder.getCoCode());
//					saleOrderLc.setSo4No(currentSaleOrder.getSoNo());
//
//					saleOrderLc.setCrtUsr(currentSaleOrder.getCrtUsr());
//					saleOrderLc.setCrtDate(date);
//					saleOrderLc.setModUsr(currentSaleOrder.getModUsr());
//					saleOrderLc.setModDate(date);
//					saleOrderMapper.insertSaleOrderLc(saleOrderLc);
//				}
                Map<String, SaleOrderLc> saleOrderLcs = currentSaleOrder.getSaleOrderLcs();
                if (saleOrderLcs != null && saleOrderLcs.size() > 0) {
                    Iterator iter = saleOrderLcs.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderLc saleOrderLc = saleOrderLcs.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderLc.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderLc.setSo4No(currentSaleOrder.getSoNo());

                        saleOrderLc.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderLc.setCrtDate(date);
                        saleOrderLc.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderLc.setModDate(date);
                        saleOrderMapper.insertSaleOrderLc(saleOrderLc);
                    }
                }

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public SaleOrder getSaleOrderBySaleOrderVo(SaleOrderVo saleOrderVo) {
        SaleOrder saleOrder = saleOrderMapper.getSaleOrderBySaleOrderVo(saleOrderVo);
        if (saleOrder != null) {
//			List<SaleOrderItem> saleOrderItemList = saleOrderMapper.getSaleOrderItemListBySaleOrderVo(saleOrderVo);
//			if(saleOrderItemList != null){
//				Map<String, SaleOrderItem> saleOrderItems = saleOrderItemListToMap(saleOrderItemList);
//				saleOrder.setSaleOrderItems(saleOrderItems);
//			}
        }
        return saleOrder;
    }

    private Map<String, SaleOrderItem> saleOrderItemListToMap(List<SaleOrderItem> saleOrderItemList) {
        Map<String, SaleOrderItem> saleOrderItems = null;
        if (saleOrderItemList != null && saleOrderItemList.size() > 0) {
            saleOrderItems = new LinkedHashMap<String, SaleOrderItem>();
            for (int i = 0; i < saleOrderItemList.size(); i++) {
                SaleOrderItem saleOrderItem = saleOrderItemList.get(i);
                saleOrderItems.put(saleOrderItem.getSo2ItCat() + saleOrderItem.getSo2ItNo() + saleOrderItem.getSo2ItSuffix(), saleOrderItem);
            }
        }
        return saleOrderItems;
    }

    @Override
    public boolean updateSaleOrder(SaleOrderDetailsVo currentSaleOrder) throws ServiceException {
        try {
            Date date = new Date();
            currentSaleOrder.setModDate(date);
            SaleOrder saleOrder = (SaleOrder) currentSaleOrder;
            Integer flag = saleOrderMapper.updateSaleOrder(saleOrder);
            if (flag > 0) {
                String fkSoNo = currentSaleOrder.getSoNo();

                //清理Sale Order Items再重新insert
                saleOrderMapper.deleteSaleOrderItems(fkSoNo);
                Map<String, SaleOrderItem> saleOrderItems = currentSaleOrder.getSaleOrderItems();
                if (saleOrderItems != null && saleOrderItems.size() > 0) {
                    Iterator iter = saleOrderItems.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderItem saleOrderItem = saleOrderItems.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderItem.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderItem.setSo2No(currentSaleOrder.getSoNo());

                        saleOrderItem.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderItem.setCrtDate(date);
                        saleOrderItem.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderItem.setModDate(date);
                        if (saleOrderItem.getSo2OffShNo() == null || saleOrderItem.getSo2OffShNo().equals("")) {
                            saleOrderItem.setSo2OffShNo("0000000000");
                        }
                        if (saleOrderItem.getSo2OffShNoPfix() == null || saleOrderItem.getSo2OffShNoPfix().equals("")) {
                            saleOrderItem.setSo2OffShNoPfix("0000");
                        }
                        saleOrderMapper.insertSaleOrderItem(saleOrderItem);
                    }
                }

                //清理Sale Order Charge再重新insert
                saleOrderMapper.deleteSaleOrderCharge(fkSoNo);
                Map<String, SaleOrderCharge> saleOrderCharges = currentSaleOrder.getSaleOrderCharges();
                if (saleOrderCharges != null && saleOrderCharges.size() > 0) {
                    Iterator iter = saleOrderCharges.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderCharge saleOrderCharge = saleOrderCharges.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderCharge.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderCharge.setSo5No(currentSaleOrder.getSoNo());

                        saleOrderCharge.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderCharge.setCrtDate(date);
                        saleOrderCharge.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderCharge.setModDate(date);
                        saleOrderMapper.insertSaleOrderCharge(saleOrderCharge);
                    }
                }

                //清理Sale Order Sbk再重新insert
                saleOrderMapper.deleteSaleOrderSbk(fkSoNo);
                Map<String, SaleOrderSbk> saleOrderSbks = currentSaleOrder.getSaleOrderSbks();
                if (saleOrderSbks != null && saleOrderSbks.size() > 0) {
                    Iterator iter = saleOrderSbks.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderSbk saleOrderSbk = saleOrderSbks.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderSbk.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderSbk.setSo3No(currentSaleOrder.getSoNo());

                        saleOrderSbk.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderSbk.setCrtDate(date);
                        saleOrderSbk.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderSbk.setModDate(date);
                        saleOrderMapper.insertSaleOrderSbk(saleOrderSbk);
                    }
                }

                //清理Sale Order Lc再重新insert
                saleOrderMapper.deleteSaleOrderLc(fkSoNo);
                Map<String, SaleOrderLc> saleOrderLcs = currentSaleOrder.getSaleOrderLcs();
                if (saleOrderLcs != null && saleOrderLcs.size() > 0) {
                    Iterator iter = saleOrderLcs.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        SaleOrderLc saleOrderLc = saleOrderLcs.get(key);
                        //从saleOrder获取cocode和sono
                        saleOrderLc.setCoCode(currentSaleOrder.getCoCode());
                        saleOrderLc.setSo4No(currentSaleOrder.getSoNo());

                        saleOrderLc.setCrtUsr(currentSaleOrder.getCrtUsr());
                        saleOrderLc.setCrtDate(date);
                        saleOrderLc.setModUsr(currentSaleOrder.getModUsr());
                        saleOrderLc.setModDate(date);
                        saleOrderMapper.insertSaleOrderLc(saleOrderLc);
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, SaleOrderItem> removeOffItem(String offItCatNoSuffix,
                                                    Map<String, SaleOrderItem> saleOrderItems) {
        if (saleOrderItems != null && saleOrderItems.size() > 0) {
            saleOrderItems.remove(offItCatNoSuffix);
        }
        return saleOrderItems;
    }

    @Override
    public String[] filterGetOffItemIds(String[] offItemIds,
                                        Map<String, SaleOrderItem> saleOrderItems) {
        String[] getItemIds = null;
        if (offItemIds != null && offItemIds.length > 0) {
            List<String> getItemIdsList = new ArrayList<String>();
            for (String offItemId : offItemIds) {
                if (!saleOrderItems.containsKey(offItemId)) {
                    getItemIdsList.add(offItemId);
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
    public boolean delSaleOrderBySaleOrderVo(SaleOrderVo saleOrderVo) {
        //先删除saleOrder items
//		saleOrderMapper.deleteOfferItem(new SaleOrder(saleOrderVo));
        //再删除saleOrder
//		Integer flag = saleOrderMapper.deleteSaleOrder(saleOrderVo);
//		if(flag > 0){
//			return true;
//		}else{
//			return false;
//		}
        return false;
    }

    @Override
    public SaleOrderDetailsVo getSaleOrderDetailVoByFkSoNo(String fkSoNo) {
        SaleOrderDetailsVo saleOrderDetailVo = null;
        SaleOrder saleOrder = saleOrderMapper.getSaleOrderByFkSoNo(fkSoNo);
        if (saleOrder != null) {
            saleOrderDetailVo = new SaleOrderDetailsVo(saleOrder);
            List<SaleOrderItem> saleOrderItemList = saleOrderMapper.getSaleOrderItemListByFkSoNo(fkSoNo);
            if (saleOrderItemList != null) {
                Map<String, SaleOrderItem> saleOrderItems = saleOrderItemListToMap(saleOrderItemList);
                saleOrderDetailVo.setSaleOrderItems(saleOrderItems);
            }
            List<SaleOrderCharge> saleOrderChargeList = saleOrderMapper.getSaleOrderChargeListByFkSoNo(fkSoNo);
            if (saleOrderChargeList != null) {
                Map<String, SaleOrderCharge> saleOrderCharges = saleOrderChargeListToMap(saleOrderChargeList);
                saleOrderDetailVo.setSaleOrderCharges(saleOrderCharges);
            }
            List<SaleOrderSbk> saleOrderSbkList = saleOrderMapper.getSaleOrderSbkListByFkSoNo(fkSoNo);
            if (saleOrderSbkList != null) {
                Map<String, SaleOrderSbk> saleOrderSbks = saleOrderSbkListToMap(saleOrderSbkList);
                saleOrderDetailVo.setSaleOrderSbks(saleOrderSbks);
            }
            List<SaleOrderLc> saleOrderLcList = saleOrderMapper.getSaleOrderLcListByFkSoNo(fkSoNo);
            if (saleOrderLcList != null) {
                Map<String, SaleOrderLc> saleOrderLcs = saleOrderLcListToMap(saleOrderLcList);
                saleOrderDetailVo.setSaleOrderLcs(saleOrderLcs);
            }
        }
        return saleOrderDetailVo;
    }

    private Map<String, SaleOrderLc> saleOrderLcListToMap(List<SaleOrderLc> saleOrderLcList) {
        Map<String, SaleOrderLc> saleOrderLcs = null;
        if (saleOrderLcList != null && saleOrderLcList.size() > 0) {
            saleOrderLcs = new LinkedHashMap<String, SaleOrderLc>();
            for (int i = 0; i < saleOrderLcList.size(); i++) {
                SaleOrderLc saleOrderLc = saleOrderLcList.get(i);
                saleOrderLcs.put(saleOrderLc.getEncodeSo4LcNo(), saleOrderLc);
            }
        }
        return saleOrderLcs;
    }

    private Map<String, SaleOrderSbk> saleOrderSbkListToMap(List<SaleOrderSbk> saleOrderSbkList) {
        Map<String, SaleOrderSbk> saleOrderSbks = null;
        if (saleOrderSbkList != null && saleOrderSbkList.size() > 0) {
            saleOrderSbks = new LinkedHashMap<String, SaleOrderSbk>();
            for (int i = 0; i < saleOrderSbkList.size(); i++) {
                SaleOrderSbk saleOrderSbk = saleOrderSbkList.get(i);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(saleOrderSbk.getSo3ExpShpDate());
                saleOrderSbks.put(dateString, saleOrderSbk);
            }
        }
        return saleOrderSbks;
    }

    private Map<String, SaleOrderCharge> saleOrderChargeListToMap(List<SaleOrderCharge> saleOrderChargeList) {
        Map<String, SaleOrderCharge> saleOrderCharges = null;
        if (saleOrderChargeList != null && saleOrderChargeList.size() > 0) {
            saleOrderCharges = new LinkedHashMap<String, SaleOrderCharge>();
            for (int i = 0; i < saleOrderChargeList.size(); i++) {
                SaleOrderCharge saleOrderCharge = saleOrderChargeList.get(i);
                saleOrderCharges.put(saleOrderCharge.getEncodeSo5ChgCode(), saleOrderCharge);
            }
        }
        return saleOrderCharges;
    }

    @Override
    public Checker checkDecimal(SaleOrderDetailsVo currentSaleOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currentSaleOrder != null) {
            if (currentSaleOrder.getSoLshpDate() == null || currentSaleOrder.getSoEtd() == null) {
                checker.setSuccess(true);
                //checker.setReturnStr("请填写完整CUSTOMER SHIP DATE和 FACTORY SHIP DATE, 保存失败");
            } else {
                if (currentSaleOrder.getSoEtd().after(currentSaleOrder.getSoLshpDate())) {
                    checker.setSuccess(false);
                    checker.setReturnStr("FACTORY SHIP DATE不能在CUSTOMER SHIP DATE之后, 保存失败");
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean delSaleOrderByFkSoNo(String fkSoNo) {
        try {
            //清理Sale Order Items
            saleOrderMapper.deleteSaleOrderItems(fkSoNo);
            //清理Sale Order Charge
            saleOrderMapper.deleteSaleOrderCharge(fkSoNo);
            //清理Sale Order Sbk
            saleOrderMapper.deleteSaleOrderSbk(fkSoNo);
            //清理Sale Order Lc
            saleOrderMapper.deleteSaleOrderLc(fkSoNo);
            //清理Sale Order
            saleOrderMapper.deleteSaleOrder(fkSoNo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String[] filterGetSaleOrderChargeCodes(String[] soChargeCodes,
                                                  Map<String, SaleOrderCharge> saleOrderCharges) {
        String[] getChargeCodes = null;
        if (soChargeCodes != null && soChargeCodes.length > 0) {
            if (saleOrderCharges == null || saleOrderCharges.size() < 1) {
                getChargeCodes = soChargeCodes;
            } else {
                List<String> getChargeCodesList = new ArrayList<String>();
                for (String soChargeCode : soChargeCodes) {
                    if (!saleOrderCharges.containsKey(soChargeCode)) {
                        getChargeCodesList.add(soChargeCode);
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
    public Map<String, SaleOrderCharge> getSaleOrderCharges(String[] soChargeCodes) {
        Map<String, SaleOrderCharge> saleOrderCharges = null;
        if (soChargeCodes != null && soChargeCodes.length > 0) {
            saleOrderCharges = new LinkedHashMap<String, SaleOrderCharge>();
            for (String soChargeCode : soChargeCodes) {
                Charge charge = chargeMapper.getChargeByFkChgCode(soChargeCode);
                if (charge != null && charge.getChgCode() != null) {
                    SaleOrderCharge saleOrderCharge = new SaleOrderCharge(charge);
                    saleOrderCharges.put(saleOrderCharge.getEncodeSo5ChgCode(), saleOrderCharge);
                }
            }
        }
        return saleOrderCharges;
    }

    @Override
    public Map<String, SaleOrderCharge> updateSaleOrderCharges(
            Map<String, SaleOrderCharge> saleOrderCharges,
            Map<String, SaleOrderCharge> getSaleOrderCharges) {
        if (saleOrderCharges == null) {
            saleOrderCharges = new LinkedHashMap<String, SaleOrderCharge>();
            if (getSaleOrderCharges != null) {
                saleOrderCharges = getSaleOrderCharges;
            }
        } else {
            if (getSaleOrderCharges != null && getSaleOrderCharges.size() > 0) {
                Iterator iter = getSaleOrderCharges.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    if (!saleOrderCharges.containsKey(key)) {
                        SaleOrderCharge val = (SaleOrderCharge) entry.getValue();
                        saleOrderCharges.put(key, val);
                    }
                }
            }
        }
        return saleOrderCharges;
    }

    @Override
    public Map<String, SaleOrderCharge> removeSoCharge(String soChargeCode,
                                                       Map<String, SaleOrderCharge> saleOrderCharges) {
        if (saleOrderCharges != null && saleOrderCharges.size() > 0) {
            saleOrderCharges.remove(soChargeCode);
        }
        return saleOrderCharges;
    }

    @Override
    public Checker checkSaleOrderCharge(SaleOrderCharge saleOrderCharge,
                                        SaleOrderDetailsVo currentSaleOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (saleOrderCharge != null) {
            if (saleOrderCharge.getSo5ChgCode() == null || saleOrderCharge.getSo5ChgCode().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Chg/Ded Code, 保存失败");
            } else {
                if (currentSaleOrder.getSaleOrderCharges() != null) {
                    if (currentSaleOrder.getSaleOrderCharges().containsKey(saleOrderCharge.getEncodeSo5ChgCode())) {
                        checker.setSuccess(false);
                        checker.setReturnStr("该Sale Order 已含有此Chg/Ded Code");
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
    public Checker checkSaleOrderChargeDecimal(SaleOrderCharge saleOrderCharge) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (saleOrderCharge != null) {
            if (saleOrderCharge.getSo5ChgRate() != null) {
                if (saleOrderCharge.getSo5ChgRate().doubleValue() < -9999.99999 || saleOrderCharge.getSo5ChgRate().doubleValue() > 9999.99999) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Rate(%)必须在[-9999.99999, 9999.99999]范围内");
                    return checker;
                }
            }
            if (saleOrderCharge.getSo5ChgAmt() != null) {
                if (saleOrderCharge.getSo5ChgAmt().doubleValue() < -9999999.99 || saleOrderCharge.getSo5ChgAmt().doubleValue() > 9999999.99) {
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
    public Checker checkSaleOrderSbk(SaleOrderSbk saleOrderSbk,
                                     SaleOrderDetailsVo currentSaleOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (saleOrderSbk != null) {
            if (saleOrderSbk.getSo3ExpShpDate() == null || saleOrderSbk.getSo3ExpShpDate().toString().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Expected ship. Date, 保存失败");
            } else {
                if (currentSaleOrder.getSaleOrderSbks() != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter.format(saleOrderSbk.getSo3ExpShpDate());
                    if (currentSaleOrder.getSaleOrderSbks().containsKey(dateString)) {
                        checker.setSuccess(false);
                        checker.setReturnStr("该Sale Order 已含有此Expected ship. Date");
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
    public Checker checkSaleOrderSbkDecimal(SaleOrderSbk saleOrderSbk) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (saleOrderSbk != null) {
            if (saleOrderSbk.getSo3ExpShpQty() != null) {
                if (saleOrderSbk.getSo3ExpShpQty().doubleValue() < -99999999.99 || saleOrderSbk.getSo3ExpShpQty().doubleValue() > 99999999.99) {
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
    public Map<String, SaleOrderSbk> removeSoSbk(String fkExpShpDate,
                                                 Map<String, SaleOrderSbk> saleOrderSbks) {
        if (saleOrderSbks != null && saleOrderSbks.size() > 0) {
            saleOrderSbks.remove(fkExpShpDate);
        }
        return saleOrderSbks;
    }

    @Override
    public Checker checkSaleOrderLc(SaleOrderLc saleOrderLc,
                                    SaleOrderDetailsVo currentSaleOrder) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (saleOrderLc != null) {
            if (saleOrderLc.getSo4LcNo() == null || saleOrderLc.getSo4LcNo().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写Chg/Ded Code, 保存失败");
            } else {
                if (currentSaleOrder.getSaleOrderLcs() != null) {
                    if (currentSaleOrder.getSaleOrderLcs().containsKey(saleOrderLc.getEncodeSo4LcNo())) {
                        checker.setSuccess(false);
                        checker.setReturnStr("该Sale Order 已含有此L/C No.");
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
    public Checker checkSaleOrderLcDecimal(SaleOrderLc saleOrderLc) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (saleOrderLc != null) {
            if (saleOrderLc.getSo4LcAmt() != null) {
                if (saleOrderLc.getSo4LcAmt().doubleValue() < -99999999.99 || saleOrderLc.getSo4LcAmt().doubleValue() > 99999999.99) {
                    checker.setSuccess(false);
                    checker.setReturnStr("L/C Amount必须在[-99999999.99, 99999999.99]范围内");
                    return checker;
                }
            }
            if (saleOrderLc.getSo4SettleAmt() != null) {
                if (saleOrderLc.getSo4SettleAmt().doubleValue() < -99999999.99 || saleOrderLc.getSo4SettleAmt().doubleValue() > 99999999.99) {
                    checker.setSuccess(false);
                    checker.setReturnStr("Total Settled Amount必须在[-99999999.99, 99999999.99]范围内");
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
    public Map<String, SaleOrderLc> removeSoLc(String fkLcNo,
                                               Map<String, SaleOrderLc> saleOrderLcs) {
        if (saleOrderLcs != null && saleOrderLcs.size() > 0) {
            saleOrderLcs.remove(fkLcNo);
        }
        return saleOrderLcs;
    }

    @Override
    public String[] filterGetSoItemIds(String[] soItemIds,
                                       Map<String, SaleOrderItem> saleOrderItems) {
        String[] getItemIds = null;
        if (soItemIds != null && soItemIds.length > 0) {
            List<String> getItemIdsList = new ArrayList<String>();
            if (saleOrderItems == null) saleOrderItems = new LinkedHashMap<String, SaleOrderItem>();
            for (String soItemId : soItemIds) {
                String[] soItemIdArray = soItemId.split("#");
                String itemId = soItemIdArray[2];
                if (!saleOrderItems.containsKey(itemId)) {
                    getItemIdsList.add(soItemId);
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
    public Map<String, SaleOrderItem> getSaleOrderItems(String module,
                                                        String[] preSoItemIds) {
        Map<String, SaleOrderItem> saleOrderItems = null;
        if (preSoItemIds != null && preSoItemIds.length > 0) {
            saleOrderItems = returnSaleOrderItems(module, preSoItemIds);
        }
        return saleOrderItems;
    }

    private Map<String, SaleOrderItem> returnSaleOrderItems(String module,
                                                            String[] soItemIds) {
        Map<String, SaleOrderItem> saleOrderItems = new LinkedHashMap<String, SaleOrderItem>();
        if (module.equals("item")) {
            for (String soItemId : soItemIds) {
                String[] soItemIdArray = soItemId.split("#");
                String itemId = soItemIdArray[2];
                ItemMaster itemMaster = itemMapper.getItemMasterByItCatNoSuffix(itemId);
                if (itemMaster != null && itemMaster.getId() != null) {
                    SaleOrderItem saleOrderItem = new SaleOrderItem(itemMaster);
                    saleOrderItems.put(itemMaster.getItCatNoSuffix(), saleOrderItem);
                }
            }
        } else if (module.equals("offersheet")) {
            for (String soItemId : soItemIds) {
                String[] soItemIdArray = soItemId.split("#");
                String offPreNo = soItemIdArray[1];
                String[] offPreNoArray = offPreNo.split("\\^");
                String offPre = offPreNoArray[0];
                String offNo = offPreNoArray[1];
                String itemId = soItemIdArray[2];
                if (itemId.length() == 9) {
                    String itCat = itemId.substring(0, 2);
                    String itNo = itemId.substring(2, 7);
                    String itSuffix = itemId.substring(7, 9);
                    SoItemMasterVo saleOrderItemSearch = new SoItemMasterVo(offPre, offNo, itCat, itNo, itSuffix);
                    OfferSheetItem offerSheetItem = itemMapper.getOffItemByOffItem(saleOrderItemSearch);
                    if (offerSheetItem != null) {
                        SaleOrderItem saleOrderItem = new SaleOrderItem(offerSheetItem);
                        saleOrderItems.put(saleOrderItem.getSo2ItCat() + saleOrderItem.getSo2ItNo() + saleOrderItem.getSo2ItSuffix(), saleOrderItem);
                    }
                }
            }
        } else {
            for (String soItemId : soItemIds) {
                String[] soItemIdArray = soItemId.split("#");
                String soNo = soItemIdArray[0];
                String itemId = soItemIdArray[2];
                if (itemId.length() == 9) {
                    String itCat = itemId.substring(0, 2);
                    String itNo = itemId.substring(2, 7);
                    String itSuffix = itemId.substring(7, 9);
                    SoItemMasterVo saleOrderItemSearch = new SoItemMasterVo(soNo, itCat, itNo, itSuffix);
                    SaleOrderItem saleOrderItem = itemMapper.getSoItemBySoItem(saleOrderItemSearch);
                    if (saleOrderItem != null) {
                        saleOrderItems.put(saleOrderItem.getSo2ItCat() + saleOrderItem.getSo2ItNo() + saleOrderItem.getSo2ItSuffix(), saleOrderItem);
                    }
                }
            }
        }
        return saleOrderItems;
    }

    @Override
    public Map<String, SaleOrderItem> removeSoItem(String soItCatNoSuffix,
                                                   Map<String, SaleOrderItem> saleOrderItems) {
        if (saleOrderItems != null && saleOrderItems.size() > 0) {
            saleOrderItems.remove(soItCatNoSuffix);
        }
        return saleOrderItems;
    }

    @Override
    public void clearSo(SaleOrderDetailsVo currentSaleOrder) {
        currentSaleOrder.setSoNo(null);
    }

    @Override
    public void updateItemAmount(SaleOrderDetailsVo currentSaleOrder) {
        if (currentSaleOrder != null) {
            Map<String, SaleOrderItem> saleOrderItems = currentSaleOrder.getSaleOrderItems();
            if (saleOrderItems != null && saleOrderItems.size() > 0) {
                List<BigDecimal> saleOrderItemAmounts = new ArrayList<BigDecimal>();
                Iterator iter = saleOrderItems.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    SaleOrderItem saleOrderItem = (SaleOrderItem) entry.getValue();
                    BigDecimal saleOrderItemAmount = saleOrderItem.getSo2Amt();
                    if (saleOrderItemAmount != null) {
                        saleOrderItemAmounts.add(saleOrderItemAmount);
                    }
                }
                BigDecimal saleOrderItemAmountTotal = sumSoAmount(saleOrderItemAmounts);
                currentSaleOrder.setSoOrdTotAmt(saleOrderItemAmountTotal);
                BigDecimal saleOrderChargeAmountTotal = currentSaleOrder.getSoOrdTotChg();
                BigDecimal saleOrderTotNet = sumSoTotNet(saleOrderItemAmountTotal, saleOrderChargeAmountTotal);
                currentSaleOrder.setSoOrdTotNet(saleOrderTotNet);
            } else {
                BigDecimal saleOrderItemAmountTotal = new BigDecimal(0);
                currentSaleOrder.setSoOrdTotAmt(saleOrderItemAmountTotal);
                BigDecimal saleOrderChargeAmountTotal = currentSaleOrder.getSoOrdTotChg();
                BigDecimal saleOrderTotNet = sumSoTotNet(saleOrderItemAmountTotal, saleOrderChargeAmountTotal);
                currentSaleOrder.setSoOrdTotNet(saleOrderTotNet);
            }
        }
    }

    private BigDecimal sumSoTotNet(BigDecimal saleOrderItemAmountTotal,
                                   BigDecimal saleOrderChargeAmountTotal) {
        if (saleOrderItemAmountTotal != null) {
            if (saleOrderChargeAmountTotal != null) {
                return saleOrderItemAmountTotal.add(saleOrderChargeAmountTotal);
            } else {
                return saleOrderItemAmountTotal;
            }
        } else {
            if (saleOrderChargeAmountTotal != null) {
                return saleOrderChargeAmountTotal;
            } else {
                return null;
            }
        }
    }

    private BigDecimal sumSoAmount(List<BigDecimal> saleOrderAmounts) {
        BigDecimal saleOrderAmountTotal = new BigDecimal("0");
        if (saleOrderAmounts != null && saleOrderAmounts.size() > 0) {
            for (BigDecimal saleOrderAmount : saleOrderAmounts) {
                if (saleOrderAmount != null) {
                    saleOrderAmountTotal = saleOrderAmountTotal.add(saleOrderAmount);
                }
            }
        }
        return saleOrderAmountTotal;
    }

    @Override
    public void updateChargeAmount(SaleOrderDetailsVo currentSaleOrder) {
        if (currentSaleOrder != null) {
            Map<String, SaleOrderCharge> saleOrderCharges = currentSaleOrder.getSaleOrderCharges();
            if (saleOrderCharges != null && saleOrderCharges.size() > 0) {
                List<BigDecimal> saleOrderChargeAmounts = new ArrayList<BigDecimal>();
                Iterator iter = saleOrderCharges.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    SaleOrderCharge saleOrderCharge = (SaleOrderCharge) entry.getValue();
                    BigDecimal saleOrderChargeAmount = saleOrderCharge.getSo5ChgAmt();
                    if (saleOrderChargeAmount != null) {
                        saleOrderChargeAmounts.add(saleOrderChargeAmount);
                    }
                }
                BigDecimal saleOrderChargeAmountTotal = sumSoAmount(saleOrderChargeAmounts);
                currentSaleOrder.setSoOrdTotChg(saleOrderChargeAmountTotal);
                BigDecimal saleOrderItemAmountTotal = currentSaleOrder.getSoOrdTotAmt();
                BigDecimal saleOrderTotNet = sumSoTotNet(saleOrderItemAmountTotal, saleOrderChargeAmountTotal);
                currentSaleOrder.setSoOrdTotNet(saleOrderTotNet);
            } else {
                BigDecimal saleOrderChargeAmountTotal = new BigDecimal(0);
                currentSaleOrder.setSoOrdTotChg(saleOrderChargeAmountTotal);
                BigDecimal saleOrderItemAmountTotal = currentSaleOrder.getSoOrdTotAmt();
                BigDecimal saleOrderTotNet = sumSoTotNet(saleOrderItemAmountTotal, saleOrderChargeAmountTotal);
                currentSaleOrder.setSoOrdTotNet(saleOrderTotNet);
            }
        }
    }
}
