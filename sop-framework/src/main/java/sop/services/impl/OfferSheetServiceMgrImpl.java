package sop.services.impl;

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
import dwz.persistence.mapper.CustomerMapper;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.OfferSheetMapper;
import dwz.persistence.mapper.TxnMapper;
import sop.persistence.beans.CustAttn;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.OfferSheet;
import sop.persistence.beans.OfferSheetItem;
import sop.services.OfferSheetServiceMgr;
import sop.vo.CustomerVo;
import sop.vo.OffItemMasterVo;
import sop.vo.OffSearchItemConditionVo;
import sop.vo.OfferSheetCombos;
import sop.vo.OfferSheetConditionVo;
import sop.vo.OfferSheetVo;
import sop.vo.TxnVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:46
 * @Package: sop.services.impl
 */

@Service(OfferSheetServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class OfferSheetServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements OfferSheetServiceMgr {

    @Autowired
    private OfferSheetMapper offerSheetMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TxnMapper txnMapper;

    @Override
    public OfferSheetCombos getOfferSheetCombos() {
        OfferSheetCombos offerSheetCombos = new OfferSheetCombos();
        List<TxnVo> txns = txnMapper.getAllTxns();
        List<CustomerVo> customers = customerMapper.getAllCustomers();
        offerSheetCombos.setTxns(txns);
        offerSheetCombos.setCustomers(customers);
        return offerSheetCombos;
    }

    @Override
    public List<OffItemMasterVo> getItemListByCondition(OffSearchItemConditionVo vo) {
        List<OffItemMasterVo> itemVoList = null;
        if (vo.getModule() == null || vo.getModule().equals("") || vo.getModule().equals("item")) {
            itemVoList = itemMapper.getOffersheetItemListByCondition(vo);
        } else {
            itemVoList = itemMapper.getOffersheetItemListFromOffNoByCondition(vo);
        }
        return itemVoList;
    }

    @Override
    public Map<String, OfferSheetItem> getOfferSheetItems(String module, String[] offItemIds) {
        Map<String, OfferSheetItem> offerSheetItems = null;
        if (offItemIds != null && offItemIds.length > 0) {
            offerSheetItems = returnOfferSheetItems(module, offItemIds);
        }
        return offerSheetItems;
    }

    private Map<String, OfferSheetItem> returnOfferSheetItems(String module, String[] offItemIds) {
        Map<String, OfferSheetItem> offerSheetItems = new LinkedHashMap<String, OfferSheetItem>();
        if (module.equals("item")) {
            for (String offItemId : offItemIds) {
                String[] offItemIdArray = offItemId.split("#");
                String itemId = offItemIdArray[1];
                ItemMaster itemMaster = itemMapper.getItemMasterByItCatNoSuffix(itemId);
                if (itemMaster != null && itemMaster.getId() != null) {
                    OfferSheetItem offerSheetItem = new OfferSheetItem(itemMaster);
                    offerSheetItems.put(itemMaster.getItCatNoSuffix(), offerSheetItem);
                }
            }
        } else {
            for (String offItemId : offItemIds) {
                String[] offItemIdArray = offItemId.split("#");
                String offPreNo = offItemIdArray[0];
                String[] offPreNoArray = offPreNo.split("\\^");
                String offPre = offPreNoArray[0];
                String offNo = offPreNoArray[1];
                String itemId = offItemIdArray[1];
                if (itemId.length() == 9) {
                    String itCat = itemId.substring(0, 2);
                    String itNo = itemId.substring(2, 7);
                    String itSuffix = itemId.substring(7, 9);
                    OffItemMasterVo offerSheetItemSearch = new OffItemMasterVo(offPre, offNo, itCat, itNo, itSuffix);
                    OfferSheetItem offerSheetItem = itemMapper.getOffItemByOffItem(offerSheetItemSearch);
                    if (offerSheetItem != null) {
                        offerSheetItems.put(offerSheetItem.getOff2ItCat() + offerSheetItem.getOff2ItNo() + offerSheetItem.getOff2ItSuffix(), offerSheetItem);
                    }
                }
            }
        }
        return offerSheetItems;
    }

    @Override
    public List<OfferSheetVo> searchOfferSheetVo(OfferSheetConditionVo vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<OfferSheetVo> offerSheetList = offerSheetMapper.findPageBreakByCondition(vo,
                rb);
        return offerSheetList;
    }

    @Override
    public Integer searchOfferSheetVoNum(OfferSheetConditionVo vo) {
        Integer count = offerSheetMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public Map<String, OfferSheetItem> updateOfferSheetItems(
            Map<String, OfferSheetItem> offerSheetItems,
            Map<String, OfferSheetItem> getOfferSheetItems) {
        if (offerSheetItems == null) {
            offerSheetItems = new LinkedHashMap<String, OfferSheetItem>();
            if (getOfferSheetItems != null) {
                offerSheetItems = getOfferSheetItems;
            }
        } else {
            if (getOfferSheetItems != null && getOfferSheetItems.size() > 0) {
                Iterator iter = getOfferSheetItems.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    if (!offerSheetItems.containsKey(key)) {
                        OfferSheetItem val = (OfferSheetItem) entry.getValue();
                        offerSheetItems.put(key, val);
                    }
                }
            }
        }
        return offerSheetItems;
    }

    @Override
    public Checker checkOfferSheet(OfferSheet currentOfferSheet) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currentOfferSheet != null) {
            if (currentOfferSheet.getOffShNoPfix() == null || currentOfferSheet.getOffShNoPfix().trim().equals("") || currentOfferSheet.getOffShNo() == null || currentOfferSheet.getOffShNo().trim().equals("")) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写完整OfferSheet No., 保存失败");
            } else {
                Integer count = 0;
                count = offerSheetMapper.getCountByOfferSheet(currentOfferSheet);
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该OfferSheet No.已存在");
                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public boolean addOfferSheet(OfferSheet currentOfferSheet) throws ServiceException {
        try {
            Date date = new Date();
            currentOfferSheet.setCrtDate(date);
            currentOfferSheet.setModDate(date);
            currentOfferSheet.setOffShNoPfixNo(currentOfferSheet.getOffShNoPfix() + currentOfferSheet.getOffShNo());
            Integer flag = offerSheetMapper.insertOfferSheet(currentOfferSheet);
            if (flag > 0) {
                Map<String, OfferSheetItem> offerSheetItems = currentOfferSheet.getOfferSheetItems();
                if (offerSheetItems != null && offerSheetItems.size() > 0) {
                    Iterator iter = offerSheetItems.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        OfferSheetItem offerSheetItem = offerSheetItems.get(key);
                        getOfferSheetCoCodeNoPrefix(offerSheetItem, currentOfferSheet);
                        offerSheetItem.setCrtUsr(currentOfferSheet.getCrtUsr());
                        offerSheetItem.setCrtDate(date);
                        offerSheetItem.setModUsr(currentOfferSheet.getModUsr());
                        offerSheetItem.setModDate(date);
                        offerSheetMapper.insertOfferSheetItem(offerSheetItem);
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

    private void getOfferSheetCoCodeNoPrefix(OfferSheetItem offerSheetItem, OfferSheet currentOfferSheet) {
        offerSheetItem.setCoCode(currentOfferSheet.getCoCode());
        offerSheetItem.setOff2ShNo(currentOfferSheet.getOffShNo());
        offerSheetItem.setOff2ShNoPfix(currentOfferSheet.getOffShNoPfix());
        offerSheetItem.setOff2ShNoPfixNo(currentOfferSheet.getOffShNoPfix() + currentOfferSheet.getOffShNo());
    }

    @Override
    public OfferSheet getOfferSheetByOfferSheetVo(OfferSheetVo offerSheetVo) {
        OfferSheet offerSheet = offerSheetMapper.getOfferSheetByOfferSheetVo(offerSheetVo);
        if (offerSheet != null) {
            List<OfferSheetItem> offerSheetItemList = offerSheetMapper.getOfferSheetItemListByOfferSheetVo(offerSheetVo);
            if (offerSheetItemList != null) {
                Map<String, OfferSheetItem> offerSheetItems = offerSheetItemListToMap(offerSheetItemList);
                offerSheet.setOfferSheetItems(offerSheetItems);
            }
        }
        return offerSheet;
    }

    private Map<String, OfferSheetItem> offerSheetItemListToMap(List<OfferSheetItem> offerSheetItemList) {
        Map<String, OfferSheetItem> offerSheetItems = null;
        if (offerSheetItemList != null && offerSheetItemList.size() > 0) {
            offerSheetItems = new LinkedHashMap<String, OfferSheetItem>();
            for (int i = 0; i < offerSheetItemList.size(); i++) {
                OfferSheetItem offSheetItem = offerSheetItemList.get(i);
                offerSheetItems.put(offSheetItem.getOff2ItCat() + offSheetItem.getOff2ItNo() + offSheetItem.getOff2ItSuffix(), offSheetItem);
            }
        }
        return offerSheetItems;
    }

    @Override
    public boolean updateOfferSheet(OfferSheet currentOfferSheet) throws ServiceException {
        try {
            Date date = new Date();
            currentOfferSheet.setCrtDate(date);
            currentOfferSheet.setModDate(date);
            Integer flag = offerSheetMapper.updateOfferSheet(currentOfferSheet);
            if (flag > 0) {
                offerSheetMapper.deleteOfferItem(currentOfferSheet);
                Map<String, OfferSheetItem> offerSheetItems = currentOfferSheet.getOfferSheetItems();
                if (offerSheetItems != null && offerSheetItems.size() > 0) {
                    Iterator iter = offerSheetItems.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        OfferSheetItem offerSheetItem = offerSheetItems.get(key);
                        getOfferSheetCoCodeNoPrefix(offerSheetItem, currentOfferSheet);
                        offerSheetItem.setCrtUsr(currentOfferSheet.getCrtUsr());
                        offerSheetItem.setCrtDate(date);
                        offerSheetItem.setModUsr(currentOfferSheet.getModUsr());
                        offerSheetItem.setModDate(date);
                        offerSheetMapper.insertOfferSheetItem(offerSheetItem);
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
    public Map<String, OfferSheetItem> removeOffItem(String offItCatNoSuffix,
                                                     Map<String, OfferSheetItem> offerSheetItems) {
        if (offerSheetItems != null && offerSheetItems.size() > 0) {
            offerSheetItems.remove(offItCatNoSuffix);
        }
        return offerSheetItems;
    }

    @Override
    public String[] filterGetOffItemIds(String[] offItemIds,
                                        Map<String, OfferSheetItem> offerSheetItems) {
        String[] getItemIds = null;
        if (offItemIds != null && offItemIds.length > 0) {
            List<String> getItemIdsList = new ArrayList<String>();
            if (offerSheetItems == null) offerSheetItems = new LinkedHashMap<String, OfferSheetItem>();
            for (String offItemId : offItemIds) {
                String[] offItemIdArray = offItemId.split("#");
                String itemId = offItemIdArray[1];
                if (!offerSheetItems.containsKey(itemId)) {
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
    public boolean delOfferSheetByOfferSheetVo(OfferSheetVo offerSheetVo) {
        //先删除offerSheet items
        offerSheetMapper.deleteOfferItem(new OfferSheet(offerSheetVo));
        //再删除offerSheet
        Integer flag = offerSheetMapper.deleteOfferSheet(offerSheetVo);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void handleOffVo(OfferSheetConditionVo vo) {
        if (vo != null) {
            if (vo.getFromPrefix() != null && !vo.getFromPrefix().trim().equals("")) {
                vo.setFrom(vo.getFromPrefix());
                if (vo.getFromNo() != null && !vo.getFromNo().trim().equals("")) {
                    vo.setFrom(vo.getFromPrefix() + vo.getFromNo());
                }
            } else {
                vo.setFrom(null);
            }
            if (vo.getToPrefix() != null && !vo.getToPrefix().trim().equals("")) {
                vo.setTo(vo.getToPrefix());
                if (vo.getToNo() != null && !vo.getToNo().trim().equals("")) {
                    vo.setTo(vo.getToPrefix() + vo.getToNo());
                }
            } else {
                vo.setTo(null);
            }
        }
    }

    @Override
    public OfferSheet getOfferSheetByOffShNoPfixNo(String offShNoPfixNo) {
        OfferSheet offerSheet = offerSheetMapper.getOfferSheetByOffShNoPfixNo(offShNoPfixNo);
        if (offerSheet != null) {
            List<OfferSheetItem> offerSheetItemList = offerSheetMapper.getOfferSheetItemListByOffShNoPfixNo(offShNoPfixNo);
            if (offerSheetItemList != null) {
                Map<String, OfferSheetItem> offerSheetItems = offerSheetItemListToMap(offerSheetItemList);
                offerSheet.setOfferSheetItems(offerSheetItems);
            }
        }
        return offerSheet;
    }

    @Override
    public void clearOff(OfferSheet currentOfferSheet) {
        currentOfferSheet.setOffShNoPfix(null);
        currentOfferSheet.setOffShNo(null);
        currentOfferSheet.setOffShNoPfixNo(null);
    }

    @Override
    public boolean delOfferSheetByOffShNoPfixNo(String offShNoPfixNo) {
        //先删除offerSheet items
        offerSheetMapper.deleteOfferItemByOffShNoPfixNo(offShNoPfixNo);
        //再删除offerSheet
        Integer flag = offerSheetMapper.deleteOfferSheetByOffShNoPfixNo(offShNoPfixNo);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void getCustAttnsByCuCode(OfferSheet currentOfferSheet, OfferSheetCombos offerSheetCombos) {
        String fkCuCode = currentOfferSheet.getOffCust();
        if (fkCuCode != null && !fkCuCode.equals("")) {
            List<CustAttn> custAttns = customerMapper.getAttnsByCuCode(fkCuCode);
            if (custAttns != null && custAttns.size() > 0) {
                offerSheetCombos.setCustAttns(custAttns);
            }
        }
    }

    @Override
    public List<CustAttn> getCustAttnsByCuCode(String fkCust) {
        List<CustAttn> custAttns = customerMapper.getAttnsByCuCode(fkCust);
        return custAttns;
    }
}
