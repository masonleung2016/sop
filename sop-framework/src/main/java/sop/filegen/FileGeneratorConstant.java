package sop.filegen;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:37
 * @Package: sop.filegen
 */


public class FileGeneratorConstant {

    public static final String SUCCESS = "200";
    public static final String FAILED = "500";

    public static final String REPORT_TYPE_ONLINE = "ONLINE";
    public static final String REPORT_TYPE_BATCH = "BATCH";

    // Output types:
    public static final String OUTPUT_TYPE_PDF = "PDF";
    public static final String OUTPUT_TYPE_ITEXT_PDF = "ITEXT_PDF";
    public static final String OUTPUT_TYPE_ITEXT_CSV = "ITEXT_CSV";
    public static final String OUTPUT_TYPE_XLS = "XLS";
    public static final String OUTPUT_TYPE_XLSX = "XLSX";
    public static final String OUTPUT_TYPE_DOC = "DOC";
    public static final String OUTPUT_TYPE_DOCX = "DOCX";
    public static final String OUTPUT_TYPE_CSV = "CSV";

    // Report processing status
    public static final String REPORT_STATUS_PROCESSING = "PROCESSING";
    public static final String REPORT_STATUS_COMPLETED = "COMPLETED";

    // A constant request user id for batch job-report generating request to
    // avoid validation error
    public static final String REQ_USER_ID_FOR_BATCH = "BATCH";

    public static final Integer NON_EXISTED_ID = -1;
}
