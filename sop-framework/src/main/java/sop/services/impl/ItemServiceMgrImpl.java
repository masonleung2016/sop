package sop.services.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.mapper.CurrencyMapper;
import dwz.persistence.mapper.FactoryMapper;
import dwz.persistence.mapper.ItemMapper;
import dwz.persistence.mapper.ProductcatMapper;
import dwz.persistence.mapper.StaffMapper;
import dwz.persistence.mapper.UomMapper;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.Currency;
import sop.persistence.beans.ItemMaster;
import sop.persistence.beans.ItemType;
import sop.persistence.beans.Template;
import sop.services.ItemServiceMgr;
import sop.utils.Common;
import sop.vo.FactoryVo;
import sop.vo.ItemMasterComboVo;
import sop.vo.ItemMasterVo;
import sop.vo.ProductCategoryVo;
import sop.vo.SearchItemConditionVo;
import sop.vo.StaffVo;
import sop.vo.UomVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:43
 * @Package: sop.services.impl
 */


@Service(ItemServiceMgr.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class ItemServiceMgrImpl extends AbstractBusinessObjectServiceMgr
        implements ItemServiceMgr {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UomMapper uomMapper;

    @Autowired
    private CurrencyMapper currMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private FactoryMapper factoryMapper;

    @Autowired
    private ProductcatMapper productCatMapper;

    @Override
    public List<ItemMasterVo> getItemListByCondition(SearchItemConditionVo vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ItemMasterVo> itemVoList = itemMapper.getItemListByCondition(vo, rb);
        return itemVoList;
    }

    @Override
    public void getItemListNum(SearchItemConditionVo vo) {
        Integer count = itemMapper.getItemListNumByCondition(vo);
        vo.setTotalCount(count);
    }

    @Override
    public ItemMasterComboVo getItemMasterCombos() {
        ItemMasterComboVo itemMasterCombos = new ItemMasterComboVo();
        List<ItemType> itemTypes = itemMapper.getAllItemTypes();
        List<UomVo> uoms = uomMapper.getAllUoms();
        List<Currency> currs = currMapper.getAllCurrs();
        List<StaffVo> staffs = staffMapper.getAllStaffs();
        List<FactoryVo> factories = factoryMapper.getAllFactories();
        List<ProductCategoryVo> productCats = productCatMapper.getAllProductCats();
        itemMasterCombos.setItemTypes(itemTypes);
        itemMasterCombos.setUoms(uoms);
        itemMasterCombos.setCurrs(currs);
        itemMasterCombos.setStaffs(staffs);
        itemMasterCombos.setFactories(factories);
        itemMasterCombos.setProductCats(productCats);
        return itemMasterCombos;
    }


    @Override
    public List<ItemType> getItemTypes() {
        List<ItemType> itemTypes = itemMapper.getAllItemTypes();
        return itemTypes;
    }

    @Override
    public ItemMaster getItemMasterById(Integer fkItem) {
        ItemMaster itemMaster = itemMapper.getItemMasterById(fkItem);
        return itemMaster;
    }

    @Override
    public List<Template> getTemplatesByFkItemType(Integer fkItemType) {
        List<Template> templates = itemMapper.getTemplatesByFkItemType(fkItemType);
        return templates;
    }

    @Override
    public List<Template> getAllTemplates() {
        List<Template> templates = itemMapper.getAllTemplates();
        return templates;
    }

    @Override
    public Checker checkItem(ItemMaster currentItem) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");
        if (currentItem != null) {
            if (currentItem.getItCatNoSuffix() == null || currentItem.getItCatNoSuffix().trim().equals("") || currentItem.getItCatNoSuffix().trim().length() != 9) {
                checker.setSuccess(false);
                checker.setReturnStr("请填写完整Item No., 保存失败");
            } else {
                Integer count = 0;
                if (currentItem.getId() == null) {
                    count = itemMapper.getCountByFkItCatNoSuffix(currentItem.getItCatNoSuffix());
                } else {
                    count = itemMapper.getCountByFkItCatNoSuffixAndId(currentItem);
                }
                if (count > 0) {
                    checker.setSuccess(false);
                    checker.setReturnStr("该Item No.已存在");
                } else {

                }
            }
        } else {
            checker.setSuccess(false);
            checker.setReturnStr("信息为空,保存失败");
        }
        return checker;
    }

    @Override
    public Checker checkDecimal(ItemMaster currentItem) {
        Checker checker = new Checker();
        checker.setSuccess(true);
        checker.setReturnStr("信息无误");

        checkDouble("Base U.Price", currentItem.getItBasePrice(), -99999.999d, 99999.999d, checker);

        checkDouble("A L", currentItem.getItPkgLCm0(), -999.9d, 999.9d, checker);
        checkDouble("A W", currentItem.getItPkgWCm0(), -999.9d, 999.9d, checker);
        checkDouble("A H", currentItem.getItPkgHCm0(), -999.9d, 999.9d, checker);

        checkDouble("B L", currentItem.getItPkgLCm1(), -999.9d, 999.9d, checker);
        checkDouble("B W", currentItem.getItPkgWCm1(), -999.9d, 999.9d, checker);
        checkDouble("B H", currentItem.getItPkgHCm1(), -999.9d, 999.9d, checker);

        checkDouble("C L", currentItem.getItPkgLCm2(), -999.9d, 999.9d, checker);
        checkDouble("C W", currentItem.getItPkgWCm2(), -999.9d, 999.9d, checker);
        checkDouble("C H", currentItem.getItPkgHCm2(), -999.9d, 999.9d, checker);

        checkDouble("D L", currentItem.getItPkgLCm3(), -999.9d, 999.9d, checker);
        checkDouble("D W", currentItem.getItPkgWCm3(), -999.9d, 999.9d, checker);
        checkDouble("D H", currentItem.getItPkgHCm3(), -999.9d, 999.9d, checker);

        checkDouble("E L", currentItem.getItPkgLCm4(), -999.9d, 999.9d, checker);
        checkDouble("E W", currentItem.getItPkgWCm4(), -999.9d, 999.9d, checker);
        checkDouble("E H", currentItem.getItPkgHCm4(), -999.9d, 999.9d, checker);

        checkDouble("F L", currentItem.getItPkgLCm5(), -999.9d, 999.9d, checker);
        checkDouble("F W", currentItem.getItPkgWCm5(), -999.9d, 999.9d, checker);
        checkDouble("F H", currentItem.getItPkgHCm5(), -999.9d, 999.9d, checker);

        checkDouble("CBM", currentItem.getItCbmTotal(), -99.999d, 99.999d, checker);

        checkDouble("Net Wt.", currentItem.getItPkgNwKg(), -999.999d, 999.999d, checker);
        checkDouble("Grs. Wt.", currentItem.getItPkgGwKg(), -999.999d, 999.999d, checker);

        checkDouble("20'", currentItem.getItPkg20Qty(), -9999999999d, 9999999999d, checker);
        checkDouble("40'", currentItem.getItPkg40Qty(), -9999999999d, 9999999999d, checker);
        checkDouble("40'HQ", currentItem.getItPkg40HQQty(), -9999999999d, 9999999999d, checker);

        checkDouble("Cost A", currentItem.getItFtyFobCost(), -99999.999d, 99999.999d, checker);

        checkDouble("Cost B", currentItem.getItFtyFobCostB(), -99999.999d, 99999.999d, checker);

        return checker;
    }

    private Checker checkDouble(String label, BigDecimal val, double from, double to, Checker checker) {
        if (val != null) {
            if (val.doubleValue() < from || val.doubleValue() > to) {
                checker.setSuccess(false);
                checker.setReturnStr("(" + label + ") must in range [" + from + ", " + to + "]");
            }
        }
        return checker;
    }

    @Override
    public Integer addItem(ItemMaster currentItem) {
        try {
            Date date = new Date();
            currentItem.setCrtDate(date);
            currentItem.setModDate(date);
            Integer flag = itemMapper.insertItem(currentItem);
            if (flag > 0) {
                return currentItem.getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean editItem(ItemMaster currentItem) {
        try {
            Date date = new Date();
            currentItem.setModDate(date);
            Integer flag = itemMapper.updateItem(currentItem);
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getTemplateNameByItCatNoSuffix(String itCatNoSuffix) {
        String templateName = itemMapper.getTemplateNameByItCatNoSuffix(itCatNoSuffix);
        return templateName;
    }

    @Override
    public boolean deleteItem(Integer fkItem) throws ServiceException {
        Integer flag = itemMapper.deleteItem(fkItem);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getItemPartPic(String partPic, String fileName, String url) {
        url = Base64.encode(url, "utf-8");
        Map<String, Object> partPicVo = new LinkedHashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            //把partPic data json string 转成map对象
            if (partPic == null || !partPic.startsWith("{")) {
                partPic = "{}";
            }
            partPicVo = mapper.readValue(partPic, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            partPicVo.put(fileName, url);
            partPic = Common.jsonMapToString(partPicVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partPic;
    }

    @Override
    public void clearItemIdAndPic(ItemMaster currentItem) {
        currentItem.setId(null);
        currentItem.setItCatNoSuffix(null);
        currentItem.setItCat(null);
        currentItem.setItNo(null);
        currentItem.setItSuffix(null);
        currentItem.setItName(null);
        currentItem.setPartPic("{}");
        currentItem.setItImage1("");
        currentItem.setItImage2("");
        currentItem.setItImage3("");
        currentItem.setItImage4("");
    }

    @Override
    public ItemMaster getItemMasterByItCatNoSuffix(String itCatNoSuffix) {
        return itemMapper.getItemMasterByItCatNoSuffix(itCatNoSuffix);
    }

//	@Override
//	public List<ItemType> getAllItemMasterTypes() {
//
//		List<ItemType> itemMasterTypes = itemMasterMapper.getAllItemMasterTypes();
//
//		return itemMasterTypes;
//	}
//
//	@Override
//	public List<Template> getTemplatesByItemMasterType(String type) {
//		List<Template> templates = itemMasterMapper.getTemplatesByItemMasterType(type);
//		return templates;
//	}
//
//	@Override
//	public boolean saveItem(Map<String, Object> json) {
//		if(validateJsonHead(json)){
//			json.put("date", new Date());
//			Integer insertId = itemMasterMapper.insertItem(json);
//			if(insertId >= 1){
//				return true;
//			}else{
//				return false;
//			}
//		}else{
//			return false;
//		}
//	}
//
//	/**
//	 * 校验item必填数据的完整性
//	 */
//	private boolean validateJsonHead(Map<String, Object> json){
//		if(json.get("productNo").toString().trim() != "" && json.get("productEngName").toString().trim() != "" && json.get("productChnName").toString().trim() != "" && json.get("fkTemplate").toString().trim() != ""){
//			return true;
//		}else{
//			return false;
//		}
//	}
//
//	@Override
//	public List<Template> getTemplatesByFkItemMaster(Integer fkItemMaster) {
//		List<Template> templates = itemMapper.getTemplatesByFkItemType(fkItemMaster);
//		return templates;
//	}
}
