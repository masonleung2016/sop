package sop.filegen.generator.impl;

import java.io.File;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:54
 * @Package: sop.filegen.generator.impl
 */

@SuppressWarnings("deprecation")
public class JrReportEngine {

    private static DataSource ds;

    private static JrReportEngine engine;

    private Map<String, String> jasperFiles = Collections.synchronizedMap(new HashMap<String, String>());

    private String reportPath;

    public static JrReportEngine getInstance() {
        if (engine == null) {
            engine = new JrReportEngine();
            engine.setDataSource(ds);
            engine.setReportPath(System.getProperty("rrr.config.path"));
            //engine.compileAllTemplates();
        }
        return engine;
    }

    public Map<String, String> getJasperFiles() {
        return jasperFiles;
    }

    public String getReportPath() {
        return reportPath;
    }


    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public DataSource getDataSource() {
        return ds;
    }

    public void setDataSource(DataSource dataSource) {
        ds = dataSource;
    }

    public void compileAllTemplates() {
        JrReportCompiler.compileAllTemplates(reportPath, jasperFiles);
    }

    private JasperPrint fillSQLReport(String mainReport, Map<String, Object> params) throws Exception {
        //JrReportCompiler.compileToFileWhenNeccessary(this.getReportPath(),this.jasperFiles, mainReport);

        //Assert.isTrue(jasperFiles.containsKey(mainReport), "report can not be able to locate. "+ mainReport);

        String jasperFile = JrReportEngine.class.getClassLoader().getResource("reportsTemplates").getPath() + "//" + mainReport + ".jasper";
        JrReportFiller filler = new JrReportFiller(this.getDataSource());
        return filler.fillReport(jasperFile, params);
    }

    public String exportPdfWithSQL(String mainReport, Map<String, Object> params, String outFile) throws Exception {
        JasperExportManager.exportReportToPdfFile(fillSQLReport(mainReport, params), outFile);
        return outFile;
    }

    public OutputStream exportPdfWithSQL(String mainReport, Map<String, Object> model, OutputStream outputStream) throws Exception {
        JasperExportManager.exportReportToPdfStream(fillSQLReport(mainReport, model), outputStream);
        return outputStream;
    }

    public String exportXlsWithSQL(String mainReport, Map<String, Object> params, String outFile) throws Exception {
        JRXlsExporter exporter = new JRXlsExporter();
        JasperPrint jasperPrint = fillSQLReport(mainReport, params);
        /*Add remove empty space rows for style of report*/
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(outFile));
        exporter.exportReport();
        return outFile;
    }

    public String exportXlsxWithSQL(String mainReport, Map<String, Object> params, String outFile) throws Exception {
        JRXlsxExporter exporter = new JRXlsxExporter();
        JasperPrint jasperPrint = fillSQLReport(mainReport, params);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(outFile));
        exporter.exportReport();
        return outFile;
    }

    public OutputStream exportXlsxWithSQL(String mainReport, Map<String, Object> params, OutputStream outputStream) throws Exception {
        JRXlsxExporter exporter = new JRXlsxExporter();
        JasperPrint jasperPrint = fillSQLReport(mainReport, params);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, outputStream);
        exporter.exportReport();
        return outputStream;
    }

    public OutputStream exportCsvWithSQL(String mainReport, Map<String, Object> params, OutputStream outputStream) throws Exception {
        JRCsvExporter exporter = new JRCsvExporter();
        JasperPrint jasperPrint = fillSQLReport(mainReport, params);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, outputStream);
        exporter.exportReport();
        return outputStream;
    }

    public String exportCsvWithSQL(String mainReport, Map<String, Object> params, String outFile) throws Exception {
        JRCsvExporter exporter = new JRCsvExporter();
        JasperPrint jasperPrint = fillSQLReport(mainReport, params);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(outFile));
        exporter.exportReport();
        return outFile;
    }

    //
    public OutputStream exportTxtWithSQL(String mainReport, Map<String, Object> params, OutputStream outputStream) throws Exception {
        JRTextExporter exporter = new JRTextExporter();
        JasperPrint jasperPrint = fillSQLReport(mainReport, params);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, outputStream);
        exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 200);
        exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 300);
        exporter.exportReport();
        return outputStream;
    }

    public String exportTxtWithSQL(String mainReport, Map<String, Object> params, String outFile) throws Exception {
        JRTextExporter exporter = new JRTextExporter();
        JasperPrint jasperPrint = fillSQLReport(mainReport, params);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(outFile));
        exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 200);
        exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 300);
        exporter.exportReport();
        return outFile;
    }

    //
    public String exportXlsxWithJRDatasource(String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        JRXlsxExporter exporter = new JRXlsxExporter();
        return exportFileWithJRDatasource(exporter, mainReport, params, jrds, outFile);
    }

    public String exportXlsWithJRDatasource(String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        JRXlsExporter exporter = new JRXlsExporter();
        /*Add remove empty space rows for style of report*/
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
        return exportFileWithJRDatasource(exporter, mainReport, params, jrds, outFile);
    }

    public String exportTxtWithJRDatasource(String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        JRTextExporter exporter = new JRTextExporter();
        exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 200);
        exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 300);
        return exportFileWithJRDatasource(exporter, mainReport, params, jrds, outFile);
    }

    public String exportCsvWithJRDatasource(String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        JRCsvExporter exporter = new JRCsvExporter();
        return exportFileWithJRDatasource(exporter, mainReport, params, jrds, outFile);
    }

    @SuppressWarnings("rawtypes")
    private String exportFileWithJRDatasource(JRExporter exporter, String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        //JrReportCompiler.compileToFileWhenNeccessary(this.getReportPath(),this.jasperFiles, mainReport);
        //Assert.isTrue(jasperFiles.containsKey(mainReport), "report can not be able to locate. "+ mainReport);
        //File sourceFile = new File(jasperFiles.get(mainReport));
        String jasperFile = JrReportEngine.class.getClassLoader().getResource("reportsTemplates").getPath() + "//" + mainReport + ".jasper";
        File sourceFile = new File(jasperFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(sourceFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrds);
        return exportToFile(jasperPrint, exporter, mainReport, params, jrds, outFile);
    }

    @SuppressWarnings("rawtypes")
    private String exportToFile(JasperPrint jasperPrint, JRExporter exporter, String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(outFile));
        exporter.exportReport();
        return outFile;
    }

    public String exportPdfWithJRDatasource(String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        //JrReportCompiler.compileToFileWhenNeccessary(this.getReportPath(),this.jasperFiles, mainReport);
        //Assert.isTrue(jasperFiles.containsKey(mainReport), "report can not be able to locate. "+ mainReport);
        //File sourceFile = new File(jasperFiles.get(mainReport));
        String jasperFile = JrReportEngine.class.getClassLoader().getResource("reportsTemplates").getPath() + "//" + mainReport + ".jasper";
        File sourceFile = new File(jasperFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(sourceFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrds);
        JasperExportManager.exportReportToPdfFile(jasperPrint, outFile);
        return outFile;
    }

    public OutputStream exportPdfWithJRDatasource(String mainReport, Map<String, Object> params, JRDataSource jrds, OutputStream outputStream) throws Exception {
        //JrReportCompiler.compileToFileWhenNeccessary(this.getReportPath(),this.jasperFiles, mainReport);
        //Assert.isTrue(jasperFiles.containsKey(mainReport), "report can not be able to locate. "+ mainReport);
        //File sourceFile = new File(jasperFiles.get(mainReport));
        String jasperFile = JrReportEngine.class.getClassLoader().getResource("reportsTemplates").getPath() + "//" + mainReport + ".jasper";
        File sourceFile = new File(jasperFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(sourceFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrds);
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream;
    }

    public String exportHtmlWithJRDatasource(String mainReport, Map<String, Object> params, JRDataSource jrds, String outFile) throws Exception {
        //JrReportCompiler.compileToFileWhenNeccessary(this.getReportPath(),this.jasperFiles, mainReport);
        //Assert.isTrue(jasperFiles.containsKey(mainReport), "report can not be able to locate. "+ mainReport);
        //File sourceFile = new File(jasperFiles.get(mainReport));
        String jasperFile = JrReportEngine.class.getClassLoader().getResource("reportsTemplates").getPath() + "//" + mainReport + ".jasper";
        File sourceFile = new File(jasperFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(sourceFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrds);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, outFile);
        return outFile;
    }

    public String exportHtmlWithSQL(String mainReport, Map<String, Object> params, String outFile) throws Exception {
        JasperExportManager.exportReportToHtmlFile(fillSQLReport(mainReport, params), outFile);
        return outFile;
    }
}
