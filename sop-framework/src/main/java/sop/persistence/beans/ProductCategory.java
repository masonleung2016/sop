package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:58
 * @Package: sop.persistence.beans
 */


public class ProductCategory extends BaseBean {
    /**
     * table comp
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String catCode;
    private String catDesc;
    private boolean editMode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
