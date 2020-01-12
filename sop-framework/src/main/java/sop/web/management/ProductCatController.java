package sop.web.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.ProductCategory;
import sop.services.ProductcatServiceMgr;
import sop.vo.ProductCategoryVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:57
 * @Package: sop.web.management
 */

@Controller("management.productcatController")
@RequestMapping(value = "/management/productcat")
@SessionAttributes({"currentProductcat"})
public class ProductCatController extends BaseController {

    @Autowired
    private ProductcatServiceMgr productcatMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model, HttpServletRequest request) {
        List<ProductCategoryVo> productcatList = productcatMgr.getProductcatListByCondition(vo);
        productcatMgr.getProductcatListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("productcatList", productcatList);
        model.addAttribute("vo", vo);
        return "/management/productcat/list";
    }

//	@RequestMapping("/selectproductcat")
//	public String selectProductcat(@RequestParam(value = "fkCatCode") String fkCatCode, Model model, HttpServletRequest request) {
//		ProductCategory currentProductcat = productcatMgr.getProductcatById(fkCatCode);
//		if(currentProductcat != null){
//			currentProductcat.setEditMode(true);
//		}
//		model.addAttribute("currentProductcat", currentProductcat);
//		return "/management/productcat/productcatinfo";
//	}
//
//	@RequestMapping("/newproductcat")
//	public String newProductcat(Model model, HttpServletRequest request) {
//		ProductCategory currentProductcat = new ProductCategory();
//		currentProductcat.setEditMode(false);
//		model.addAttribute("currentProductcat", currentProductcat);
//		return "/management/productcat/productcatinfo";
//	}
//
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public ModelAndView save(ProductCategory currentProductcat) {
//		if(currentProductcat.isEditMode()){
//			if(productcatMgr.updateProductcat(currentProductcat)){
//				return ajaxDoneSuccess(getMessage("msg.operation.success"));
//			}else{
//				return ajaxDoneError(getMessage("msg.operation.failure"));
//			}
//		}else{
//			Checker checker = productcatMgr.checkProductcat(currentProductcat);
//			if(checker != null){
//				if(checker.isSuccess()){
//					if(productcatMgr.insertProductcat(currentProductcat)){
//						return ajaxDoneSuccess(getMessage("msg.operation.success"));
//					}else{
//						return ajaxDoneError(getMessage("msg.operation.failure"));
//					}
//				}else{
//					return ajaxDoneError(checker.getReturnStr());
//				}
//			}else{
//				return ajaxDoneError(getMessage("msg.operation.failure"));
//			}
//		}
//	}

    @RequestMapping("/edit/{fkCatCode}")
    public String edit(@PathVariable("fkCatCode") String fkCatCode, Model model, HttpServletRequest request) {
        fkCatCode = Base64.decode(fkCatCode, "utf-8");
        ProductCategory currentProductcat = productcatMgr.getProductcatById(fkCatCode);
        model.addAttribute("currentProductcat", currentProductcat);
        return "/management/productcat/edit";
    }

    @RequestMapping("/add")
    public String newCom(Model model, HttpServletRequest request) {
        return "/management/productcat/add";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentProductcat") ProductCategory currentProductcat, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentProductcat.setModUsr(currentUser.getUsrId());
        }
        if (productcatMgr.updateProductcat(currentProductcat)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(ProductCategory currentProductcat, HttpServletRequest request) {
        Checker checker = productcatMgr.checkProductcat(currentProductcat);
        if (checker.isSuccess()) {
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentProductcat.setCrtUsr(currentUser.getUsrId());
                currentProductcat.setModUsr(currentUser.getUsrId());
            }
            if (productcatMgr.insertProductcat(currentProductcat)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/delete/{fkCatCode}")
    public ModelAndView delete(@PathVariable("fkCatCode") String fkCatCode, HttpServletRequest request) {
        fkCatCode = Base64.decode(fkCatCode, "utf-8");
        try {
            if (productcatMgr.deleteProductcat(fkCatCode)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            return ajaxDoneError(e.toString());
        }
    }
}
