package sop.filegen;

import org.springframework.validation.BindingResult;

import sop.exception.ServiceVLDException;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:36
 * @Package: sop.filegen
 */

public class FileGenerationException extends ServiceVLDException implements java.io.Serializable {
    public FileGenerationException(Exception e) {
        super(new Object(), "");
        this.reject("E-GEN-ERROR", e.getMessage());
    }

    public FileGenerationException(String message) {
        super(new Object(), "");
        this.reject("E-GEN-ERROR", message);
    }

    public FileGenerationException(BindingResult bindingResult) {
        super(bindingResult);
    }

    public FileGenerationException(Object target, String objectName) {
        super(target, objectName);
    }

}
