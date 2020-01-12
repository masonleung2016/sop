package sop.filegen.generator;

import sop.filegen.FileGenerationException;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 18:02
 * @Package: sop.filegen.generator.impl
 */

public interface FileGenerator {

    GenResult generate(GenRequest request) throws FileGenerationException;
}
