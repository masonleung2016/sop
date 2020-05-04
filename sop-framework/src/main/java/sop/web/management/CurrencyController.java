package sop.web.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import sop.persistence.beans.Currency;
import sop.services.CurrencyServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:45
 * @Package: sop.web.management
 */

@Controller("management.currencyController")
@RequestMapping(value = "/management/currency")
public class CurrencyController extends BaseController {

    @Autowired
    private CurrencyServiceMgr currencyMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        
        List<Currency> currencyList = currencyMgr.searchCurrency(vo);
        
        Integer totalCount = currencyMgr.searchCurrencyNum(vo);

        vo.setTotalCount(totalCount);
        
        model.addAttribute("totalCount", totalCount);
        
        model.addAttribute("pageSize", vo.getPageSize());
        
        model.addAttribute("vo", vo);
        
        model.addAttribute("currencyList", currencyList);
        
        return "/management/currency/list";
    }

//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public ModelAndView save(Currency currency,HttpServletRequest request) {
//		try {
//			//TODO:不能直接判断code，因为code是可以改变的，他还是会认为是new一位新code.如果有id那就行，因为id不变。
//			Currency currencyFromDb = currencyMgr.getCurrencyByName(currency.getCcyCode());
//			if(currencyFromDb== null){
//				//save add currency
//				currencyMgr.addCurrency(currency);
//			}else{
//				//edit currency
//				currencyMgr.updateCurrency(currency);
//			}
//		} catch (ServiceException e) {
//			exception(e, request);
//		}
//		return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}
//
//	@RequestMapping("/edit/{ccyCode}")
//	@ResponseBody
//	public HashMap<String, Object> edit(@PathVariable("ccyCode") String ccyCode, Model model) {
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		Currency currency = currencyMgr.getCurrencyByName(ccyCode);
//		result.put("currency", currency);
//		return result;
//	}
//
//	@RequestMapping("/delete/{ccyCode}")
//	public ModelAndView delete(@PathVariable("ccyCode") String ccyCode, HttpServletRequest request) {
//			currencyMgr.deleteCurrency(ccyCode);
//			return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}

    @RequestMapping("/add")
    public String add() {
        return "/management/currency/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Currency currency) {
        
        Checker checker = currencyMgr.checkCurrency(currency);
        
        if (checker.isSuccess()) {
            
            Checker checkerDecimal = currencyMgr.checkDecimal(currency);
            
            if (checkerDecimal.isSuccess()) {
                
                try {
                    
                    currencyMgr.addCurrency(currency);
                    
                } catch (ServiceException e) {
                    
                    e.printStackTrace();
                    
                    return ajaxDoneError("Code 重复");
                    
                }
                
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
                
            } else {
                
                return ajaxDoneError(checkerDecimal.getReturnStr());
                
            }
            
        } else {
            
            return ajaxDoneError(checker.getReturnStr());
            
        }
    }

    @RequestMapping("/edit/{ccycode}")
    public String edit(@PathVariable("ccycode") String ccycode, Model model) {
        
        Currency currency = currencyMgr.getCurrencyByName(ccycode);
        
        model.addAttribute("currency", currency);
        
        return "/management/currency/edit";
        
    }

    @RequestMapping("/update")
    public ModelAndView update(Currency currency) {
        
        Checker checker = currencyMgr.checkCurrencyDecimal(currency);
        
        if (checker.isSuccess()) {
            
            try {
                
                currencyMgr.updateCurrency(currency);
                
            } catch (ServiceException e) {
                
                e.printStackTrace();
                
                return ajaxDoneError(e.getMessage());
                
            }
            
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
            
        } else {
            
            return ajaxDoneError(checker.getReturnStr());
            
        }
    }

    @RequestMapping("/delete/{fkCcyCode}")
    public ModelAndView delete(@PathVariable("fkCcyCode") String fkCcyCode) {
        
        try {
            
            if (currencyMgr.deleteCurrency(fkCcyCode)) {
                
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
                
            } else {
                
                return ajaxDoneError(getMessage("msg.operation.failure"));
                
            }
            
        } catch (ServiceException e) {
            
            return ajaxDoneError(e.toString());
            
        }
    }
}
