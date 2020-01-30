package dwz.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.Company;
import sop.persistence.beans.ProductCategory;
import sop.vo.CompanyVo;
import sop.vo.ProductCategoryVo;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:28
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface ProductcatMapper extends BaseMapper<Company, Integer> {

    List<CompanyVo> getComListByCondition(BaseConditionVO vo, RowBounds rb);

    Integer getComListNumByCondition(BaseConditionVO vo);

    Company getCompanyByFkCoCode(String fkCoCode);

    void updateCompany(Company currentCom);

    Integer getCountByFkCoCode(String fkCoCode);

    Integer insertCompany(Company currentCom);

    List<ProductCategoryVo> getProductcatListByCondition(BaseConditionVO vo,RowBounds rb);

    Integer getProductcatListNumByCondition(BaseConditionVO vo);

    ProductCategory getProductcatByFkCatCode(String fkCatCode);

    void updateProductcat(ProductCategory currentProductcat);

    Integer getCountByFkCatCode(String fkCatCode);

    Integer insertProductcat(ProductCategory currentProductcat);

    Integer deleteProductcat(String fkCatCode);

    List<ProductCategoryVo> getAllProductCats();

}
