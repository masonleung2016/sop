package dwz.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.BaseConditionVO;
import sop.persistence.beans.CustAttn;
import sop.persistence.beans.Customer;
import sop.vo.CustomerVo;


/**
 * @Author: LCF
 * @Date: 2020/1/8 17:20
 * @Package: dwz.persistence.mapper
 */

@Repository
public interface CustomerMapper extends BaseMapper<Customer, String> {

    // 查询
    List<CustomerVo> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

    void updateStatus(@Param("code") String code, @Param("deleted") boolean b, @Param("modUsr") String modUsr, @Param("modDate") Date date);

    Customer getCustomerByCode(@Param("cuCode") String cuCode);

    List<Customer> findAllCustomers();

    Integer deleteCustomer(String fkCuCode);

    Integer getCountByFkCuCode(String fkCuCode);

    List<CustomerVo> getAllCustomers();

    List<CustAttn> getAttnsByCuCode(String fkCuCode);

    void insertCustAttn(CustAttn custAttn);

    void delCustAttns(String fkCuCode);

    String getCustomerNameByCuCode(String fkCuCode);
}
