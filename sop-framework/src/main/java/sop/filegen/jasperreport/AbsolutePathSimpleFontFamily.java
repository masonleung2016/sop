package sop.filegen.jasperreport;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.fonts.FontFace;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.util.JRDataUtils;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:33
 * @Package: sop.filegen.jasperreport
 */

public class AbsolutePathSimpleFontFamily implements FontFamily {

    /**
     *
     */
    private JasperReportsContext jasperReportsContext;
    private String name;
    private FontFace normalFace;
    private FontFace boldFace;
    private FontFace italicFace;
    private FontFace boldItalicFace;
    private String normalPdfFont;
    private String boldPdfFont;
    private String italicPdfFont;
    private String boldItalicPdfFont;
    private String pdfEncoding;
    private Boolean isPdfEmbedded;
    private String defaultExportFont;
    private Map<String, String> exportFonts;
    private Set<String> locales;

    public AbsolutePathSimpleFontFamily() {
        this(DefaultJasperReportsContext.getInstance());
    }

    public AbsolutePathSimpleFontFamily(JasperReportsContext jasperReportsContext) {
        this.jasperReportsContext = jasperReportsContext;
    }


    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public void setNormal(String normal) {
        normalFace = AbsolutePathSimpleFontFace.getInstance(jasperReportsContext, normal);
    }

    /**
     *
     */
    public void setBold(String bold) {
        boldFace = AbsolutePathSimpleFontFace.getInstance(jasperReportsContext, bold);
    }

    /**
     *
     */
    public void setItalic(String italic) {
        italicFace = AbsolutePathSimpleFontFace.getInstance(jasperReportsContext, italic);
    }

    /**
     *
     */
    public void setBoldItalic(String boldItalic) {
        boldItalicFace = AbsolutePathSimpleFontFace.getInstance(jasperReportsContext, boldItalic);
    }

    /**
     *
     */
    public FontFace getNormalFace() {
        return normalFace;
    }

    /**
     *
     */
    public FontFace getBoldFace() {
        return boldFace;
    }

    /**
     *
     */
    public FontFace getItalicFace() {
        return italicFace;
    }

    /**
     *
     */
    public FontFace getBoldItalicFace() {
        return boldItalicFace;
    }

    /**
     *
     */
    public String getNormalPdfFont() {
        return normalPdfFont;
    }

    /**
     *
     */
    public void setNormalPdfFont(String normalPdfFont) {
        this.normalPdfFont = normalPdfFont;
    }

    /**
     *
     */
    public String getBoldPdfFont() {
        return boldPdfFont;
    }

    /**
     *
     */
    public void setBoldPdfFont(String boldPdfFont) {
        this.boldPdfFont = boldPdfFont;
    }

    /**
     *
     */
    public String getItalicPdfFont() {
        return italicPdfFont;
    }

    /**
     *
     */
    public void setItalicPdfFont(String italicPdfFont) {
        this.italicPdfFont = italicPdfFont;
    }

    /**
     *
     */
    public String getBoldItalicPdfFont() {
        return boldItalicPdfFont;
    }

    /**
     *
     */
    public void setBoldItalicPdfFont(String boldItalicPdfFont) {
        this.boldItalicPdfFont = boldItalicPdfFont;
    }

    /**
     *
     */
    public String getPdfEncoding() {
        return pdfEncoding;
    }

    /**
     *
     */
    public void setPdfEncoding(String pdfEncoding) {
        this.pdfEncoding = pdfEncoding;
    }

    /**
     *
     */
    public Boolean isPdfEmbedded() {
        return isPdfEmbedded;
    }

    /**
     *
     */
    public void setPdfEmbedded(Boolean isPdfEmbedded) {
        this.isPdfEmbedded = isPdfEmbedded;
    }

    /**
     *
     */
    public String getDefaultExportFont() {
        return defaultExportFont;
    }

    /**
     *
     */
    public void setDefaultExportFont(String defaultExportFont) {
        this.defaultExportFont = defaultExportFont;
    }

    /**
     *
     */
    public Map<String, String> getExportFonts() {
        return exportFonts;
    }

    /**
     *
     */
    public void setExportFonts(Map<String, String> exportFonts) {
        this.exportFonts = exportFonts;
    }

    /**
     *
     */
    public String getExportFont(String key) {
        String exportFont = exportFonts == null ? null : (String) exportFonts.get(key);
        return exportFont == null ? defaultExportFont : exportFont;
    }

    /**
     *
     */
    public Set<String> getLocales() {
        return locales;
    }

    /**
     *
     */
    public void setLocales(Set<String> locales) {
        this.locales = locales;
    }

    /**
     *
     */
    public boolean supportsLocale(Locale locale) {
        return locales == null || locales.isEmpty() || locales.contains(JRDataUtils.getLocaleCode(locale));
    }

    @Override
    public boolean isVisible() {
        // TODO Auto-generated method stub
        return false;
    }


}
