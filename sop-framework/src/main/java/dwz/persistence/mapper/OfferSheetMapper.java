package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import sop.persistence.beans.OfferSheet;
import sop.persistence.beans.OfferSheetItem;
import sop.reports.vo.OffAbCostPriceHead;
import sop.vo.ItemMasterConditionVo;
import sop.vo.OfferSheetConditionVo;
import sop.vo.OfferSheetVo;
import sop.vo.TxnVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:24
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface OfferSheetMapper extends BaseMapper<OfferSheet, Integer> {

    // 查询
    List<OfferSheetVo> findPageBreakByCondition(OfferSheetConditionVo vo, RowBounds rb);

    Integer findNumberByCondition(OfferSheetConditionVo vo);

    List<TxnVo> getAllTxns();

    Integer getCountByOfferSheet(OfferSheet currentOfferSheet);

    Integer insertOfferSheet(OfferSheet currentOfferSheet);

    void insertOfferSheetItem(OfferSheetItem offerSheetItem);

    OfferSheet getOfferSheetByOfferSheetVo(OfferSheetVo offerSheetVo);

    List<OfferSheetItem> getOfferSheetItemListByOfferSheetVo(
            OfferSheetVo offerSheetVo);

    Integer updateOfferSheet(OfferSheet currentOfferSheet);

    void deleteOfferItem(OfferSheet currentOfferSheet);

    Integer deleteOfferSheet(OfferSheetVo offerSheetVo);

    OfferSheet getOfferSheetByOffShNoPfixNo(String offShNoPfixNo);

    List<OfferSheetItem> getOfferSheetItemListByOffShNoPfixNo(
            String offShNoPfixNo);

    void deleteOfferItemByOffShNoPfixNo(String offShNoPfixNo);

    Integer deleteOfferSheetByOffShNoPfixNo(String offShNoPfixNo);

    OffAbCostPriceHead getAbCostPriceReport(OfferSheetConditionVo vo);

    List<Object> getOffAbCostPrice(
            OfferSheetConditionVo vo);

    List<Object> getOffListItem(ItemMasterConditionVo vo);

    List<Object> getCostPriceList(OfferSheetConditionVo vo);

    List<Object> getABPriceList(OfferSheetConditionVo vo);

    List<Object> getSimplifiedOff(OfferSheetConditionVo vo);
}
