package sop.persistence.beans;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:09
 * @Package: sop.persistence.beans
 */

public class Template extends AbstractDO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer fkItemMaster;
    private String name;
    private String labelEng;
    private String labelChn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkItemMaster() {
        return fkItemMaster;
    }

    public void setFkItemMaster(Integer fkItemMaster) {
        this.fkItemMaster = fkItemMaster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelEng() {
        return labelEng;
    }

    public void setLabelEng(String labelEng) {
        this.labelEng = labelEng;
    }

    public String getLabelChn() {
        return labelChn;
    }

    public void setLabelChn(String labelChn) {
        this.labelChn = labelChn;
    }


}
