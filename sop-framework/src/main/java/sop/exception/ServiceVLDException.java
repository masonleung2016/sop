package sop.exception;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:44
 * @Package: sop.exception
 */

public class ServiceVLDException extends BindException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ServiceVLDException(BindingResult bindingResult) {
        super(bindingResult);
    }

    public ServiceVLDException(Object target, String objectName) {
        super(target, objectName);
    }

    public String getPrettyMessage() {
        StringBuilder sb = new StringBuilder();
        if (this.hasErrors()) {
            for (Object o : this.getAllErrors()) {
                ObjectError oe = (ObjectError) o;
                sb.append(oe.getObjectName() + " has error: msg->" + oe.getDefaultMessage() + ", arguments->" + StringUtils.arrayToCommaDelimitedString(oe.getArguments()));
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
