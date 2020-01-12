package sop.filegen.jasperreport;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.fonts.FontFace;
import net.sf.jasperreports.engine.fonts.FontUtil;
import net.sf.jasperreports.engine.util.JRStyledText;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:32
 * @Package: sop.filegen.jasperreport
 */

public class AbsolutePathSimpleFontFace implements FontFace {

    /**
     *
     */
    private String file;
    private Font font;


    /**
     *
     */
    public AbsolutePathSimpleFontFace(JasperReportsContext jasperReportsContext, String file) {
        this.file = file;

//		InputStream is = null;
//		try
//		{
//			is = RepositoryUtil.getInstance(jasperReportsContext).getInputStreamFromLocation(file);
//		}
//		catch(JRException e)
//		{
//			throw new JRRuntimeException(e);
//		}
//
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(file));
        } catch (FontFormatException e) {
            throw new JRRuntimeException(e);
        } catch (IOException e) {
            throw new JRRuntimeException(e);
        } finally {
//			try
//			{
//				is.close();
//			}
//			catch (IOException e)
//			{
//			}
        }
    }


    /**
     * @see #SimpleFontFace(JasperReportsContext, String)
     */
    public AbsolutePathSimpleFontFace(String file) {
        this(DefaultJasperReportsContext.getInstance(), file);
    }


    /**
     *
     */
    public AbsolutePathSimpleFontFace(Font font) {
        this.font = font;
    }

    /**
     *
     */
    public static AbsolutePathSimpleFontFace getInstance(JasperReportsContext jasperReportsContext, String fontName) {
        AbsolutePathSimpleFontFace fontFace = null;

        if (fontName != null) {
            if (fontName.trim().toUpperCase().endsWith(".TTF")) {
                fontFace = new AbsolutePathSimpleFontFace(fontName);
            } else {
                FontUtil.getInstance(jasperReportsContext).checkAwtFont(fontName, JRPropertiesUtil.getInstance(jasperReportsContext).getBooleanProperty(JRStyledText.PROPERTY_AWT_IGNORE_MISSING_FONT));

                fontFace = new AbsolutePathSimpleFontFace(new Font(fontName, Font.PLAIN, JRPropertiesUtil.getInstance(jasperReportsContext).getIntegerProperty(JRFont.DEFAULT_FONT_SIZE)));
            }
        }

        return fontFace;
    }

    /**
     *
     */
    public String getName() {
        //(String)font.getAttributes().get(TextAttribute.FAMILY);
        return font.getName();
    }

    /**
     *
     */
    public String getFile() {
        return file;
    }

    /**
     *
     */
    public Font getFont() {
        return font;
    }


    @Override
    public String getEot() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getPdf() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getSvg() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getTtf() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getWoff() {
        // TODO Auto-generated method stub
        return null;
    }

}
