package sop.filegen;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:35
 * @Package: sop.filegen
 */

@XmlRootElement
@XmlType
public class BarcodeGenRequest extends GenRequest {

    /**
     *
     */

    private static final long serialVersionUID = 1L;

    // OMR Left position of the OMR
    private float left;
    // OMR Top position of the last OMR i.e. position 0
    private float top;
    // OMR Width of the OMR
    private float width;
    // OMR Height
    private float height;

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
