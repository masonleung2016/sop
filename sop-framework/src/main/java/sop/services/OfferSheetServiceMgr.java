package sop.services;

import java.util.List;
import java.util.Map;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import sop.persistence.beans.CustAttn;
import sop.persistence.beans.OfferSheet;
import sop.persistence.beans.OfferSheetItem;
import sop.vo.OffItemMasterVo;
import sop.vo.OffSearchItemConditionVo;
import sop.vo.OfferSheetCombos;
import sop.vo.OfferSheetConditionVo;
import sop.vo.OfferSheetVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:03
 * @Package: sop.services
 */

public interface OfferSheetServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "offerSheetServiceMgr";

    OfferSheetCombos getOfferSheetCombos();

    List<OffItemMasterVo> getItemListByCondition(OffSearchItemConditionVo vo);

    Map<String, OfferSheetItem> getOfferSheetItems(String module, String[] offItemIds);

    List<OfferSheetVo> searchOfferSheetVo(OfferSheetConditionVo vo);

    Integer searchOfferSheetVoNum(OfferSheetConditionVo vo);

    Map<String, OfferSheetItem> updateOfferSheetItems(
            Map<String, OfferSheetItem> offerSheetItems,
            Map<String, OfferSheetItem> getOfferSheetItems);

    Checker checkOfferSheet(OfferSheet currentOfferSheet);

    boolean addOfferSheet(OfferSheet currentOfferSheet) throws ServiceException;

    OfferSheet getOfferSheetByOfferSheetVo(OfferSheetVo offerSheetVo);

    boolean updateOfferSheet(OfferSheet currentOfferSheet) throws ServiceException;

    Map<String, OfferSheetItem> removeOffItem(String offItCatNoSuffix,
                                              Map<String, OfferSheetItem> offerSheetItems);

    String[] filterGetOffItemIds(String[] offItemIds,
                                 Map<String, OfferSheetItem> offerSheetItems);

    boolean delOfferSheetByOfferSheetVo(OfferSheetVo offerSheetVo);

    void handleOffVo(OfferSheetConditionVo vo);

    OfferSheet getOfferSheetByOffShNoPfixNo(String offShNoPfixNo);

    void clearOff(OfferSheet currentOfferSheet);

    boolean delOfferSheetByOffShNoPfixNo(String offShNoPfixNo);

    void getCustAttnsByCuCode(OfferSheet currentOfferSheet, OfferSheetCombos offerSheetCombos);

    List<CustAttn> getCustAttnsByCuCode(String fkCust);

}
