package sop.services;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.ItemType;
import sop.persistence.beans.Template;
import sop.vo.ItemMasterComboVo;
import sop.vo.ItemMasterVo;
import sop.vo.SearchItemConditionVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:03
 * @Package: sop.services
 */

public interface ItemServiceMgr extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "itemServiceMgr";

    List<ItemMasterVo> getItemListByCondition(SearchItemConditionVo vo);

    void getItemListNum(SearchItemConditionVo vo);

    ItemMasterComboVo getItemMasterCombos();

    List<ItemType> getItemTypes();

    ItemMaster getItemMasterById(Integer fkItem);

    List<Template> getTemplatesByFkItemType(Integer fkItemType);


    List<Template> getAllTemplates();

    Checker checkItem(ItemMaster currentItem);

    Integer addItem(ItemMaster currentItem);

    boolean editItem(ItemMaster currentItem);

    String getTemplateNameByItCatNoSuffix(String offItCatNoSuffix);

    ItemMaster getItemMasterByItCatNoSuffix(String itCatNoSuffix);

    Checker checkDecimal(ItemMaster currentItem);

    boolean deleteItem(Integer fkItem) throws ServiceException;

    String getItemPartPic(String partPic, String fileName, String url);

    void clearItemIdAndPic(ItemMaster currentItem);

//	List<ItemMaster> getAllItemMasterTypes();
//
//	List<Template> getTemplatesByItemMasterType(String type);
//
//	boolean saveItem(Map<String, Object> json);
//
//	List<Template> getTemplatesByFkItemMaster(Integer fkItemMaster);

}
