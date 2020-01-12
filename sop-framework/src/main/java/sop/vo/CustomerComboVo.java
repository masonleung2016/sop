package sop.vo;

import java.util.List;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:35
 * @Package: sop.vo
 */

public class CustomerComboVo {
    private List<PayTermVo> payTerms;
    private List<GlInterfaceVo> glInterfaces;

    public List<PayTermVo> getPayTerms() {
        return payTerms;
    }

    public void setPayTerms(List<PayTermVo> payTerms) {
        this.payTerms = payTerms;
    }

    public List<GlInterfaceVo> getGlInterfaces() {
        return glInterfaces;
    }

    public void setGlInterfaces(List<GlInterfaceVo> glInterfaces) {
        this.glInterfaces = glInterfaces;
    }
}
