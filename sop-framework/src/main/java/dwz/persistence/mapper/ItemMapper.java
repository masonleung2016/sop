package dwz.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.ItemType;
import sop.persistence.beans.OfferSheetItem;
import sop.persistence.beans.PurchaseOrderItem;
import sop.persistence.beans.SaleOrderItem;
import sop.persistence.beans.Template;
import sop.vo.ItemMasterConditionVo;
import sop.vo.ItemMasterVo;
import sop.vo.OffItemMasterVo;
import sop.vo.OffSearchItemConditionVo;
import sop.vo.PoItemMasterVo;
import sop.vo.PoSearchItemConditionVo;
import sop.vo.SearchItemConditionVo;
import sop.vo.SoItemMasterVo;
import sop.vo.SoSearchItemConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:23
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface ItemMapper extends BaseMapper<ItemType, Integer> {

    // 查询所有itemmaster type
    List<ItemType> getAllItemMasterTypes();

    //根据itemmaster type获取对应的templates
    List<Template> getTemplatesByItemMasterType(String type);

    Integer insertItem(Map<String, Object> json);

    List<Template> getTemplatesByFkItemType(Integer fkItemType);

    List<Template> getAllTemplates();

    List<ItemMasterVo> getItemListByCondition(SearchItemConditionVo vo,
                                              RowBounds rb);

    Integer getItemListNumByCondition(SearchItemConditionVo vo);

    List<ItemType> getAllItemTypes();

    ItemMaster getItemMasterById(Integer fkItem);

    Integer getCountByFkItCatNoSuffix(String fkItCatNoSuffix);

    Integer insertItem(ItemMaster currentItem);

    Integer getCountByFkItCatNoSuffixAndId(ItemMaster currentItem);

    Integer updateItem(ItemMaster currentItem);

    List<OffItemMasterVo> getOffersheetItemListByCondition(OffSearchItemConditionVo vo);

    String getTemplateNameByItCatNoSuffix(String itCatNoSuffix);

    Integer deleteItem(Integer fkItem);

    ItemMaster getItemMasterByItCatNoSuffix(String itCatNoSuffix);

    List<OffItemMasterVo> getOffersheetItemListFromOffNoByCondition(OffSearchItemConditionVo vo);

    OfferSheetItem getOffItemByOffItem(OffItemMasterVo offerSheetItemSearch);

    List<SoItemMasterVo> getSoItemListByCondition(SoSearchItemConditionVo vo);

    List<SoItemMasterVo> getSoItemListFromSoNoByCondition(SoSearchItemConditionVo vo);

    List<SoItemMasterVo> getSoItemListFromOffNoByCondition(SoSearchItemConditionVo vo);

    SaleOrderItem getSoItemBySoItem(SoItemMasterVo saleOrderItemSearch);

    List<PoItemMasterVo> getPoItemListByCondition(PoSearchItemConditionVo vo);

    List<PoItemMasterVo> getPoItemListFromOffNoByCondition(PoSearchItemConditionVo vo);

    List<PoItemMasterVo> getPoItemListFromSoNoByCondition(PoSearchItemConditionVo vo);

    List<PoItemMasterVo> getPoItemListFromPoNoByCondition(PoSearchItemConditionVo vo);

    PurchaseOrderItem getPoItemByPoItem(PoItemMasterVo purchaseOrderItemSearch);

    String getPartPicByItCatNoSuffix(String itCatNoSuffix);

    List<Object> getItemMasterReport(ItemMasterConditionVo vo);
    
}
