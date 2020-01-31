package sop.filegen.generator.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * @Author: LCF
 * @Date: 2020/1/8 18:00
 * @Package: sop.filegen.generator.impl
 */

public class OmrBarcodeGenerator {

    private static final String TEMP_PATH = System.getProperty("java.io.tmpdir");
    private static final String PAGE_STORE_FILE = TEMP_PATH + "/nFormStart.txt";
    // declare private static final Stringants
    private static final int nMAXOMRMARK = 18 + 1;
    private static final int nOMRPARITY = 2;
    // Set Single sided (1) or Double sided (2);
    private static final int nSingleDoubleSide = 2;
    // OMR Space of between the OMRs
    private static final float fSPACE = 9.21F;
    // OMR Maximum Form number
    private static final int nMaxForm = 31;
    private static int width = 60;
    private static int height = 500;
    // OMR Left position of the OMR
    private static float fLEFT = 550;
    // OMR Top position of the last OMR i.e. position 0
    private static float fTOP = 317;
    // OMR Width of the OMR
    private static float fWIDTH = 500;
    // OMR Height
    private static float fHEIGHT = 5.0F;
    // OMR Form Registry location
    // OMR Current Print Queue Registry location
    //private static final String sPQLoc = "CurrentPQ";
    // Macro Complete Indicator for VolumePrint Registry location
    // Macro Complete Indicator for AutoPrint Registry location
    // Macro Complete
    //private static final String sComplete = "Complete";
    // Macro Complete Indicator
    //private static final String sComInd = "Y";
    private static com.itextpdf.text.Image lineImage;

    public static void printBarcode(String inputFile, String outputFile, float left, float top, float width, float height) throws Exception {
        fLEFT = left;
        fTOP = top;
        fWIDTH = width;
        fHEIGHT = height;
        PdfReader reader = new PdfReader(inputFile);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
        int total = reader.getNumberOfPages();
        ormPrintAll(total, 0, stamper);
        stamper.close();
    }

    private static void omrGenMarking(List<Integer> anOmrFlag, int nCurrentPage, int nActualPage, int nNumberOfPage, int nForm) {
        //' declare variable
        String sBinCode = "";
        int nParity;

        //' Clear Omr Array
        omrInit(anOmrFlag);

        //' Set page number to Binary 把页数转成二进制， 长度4.
        sBinCode = omrDec2Bin(nCurrentPage, 4, sBinCode);

        //' Set OMR array//
        for (int i = 0; i < 4; i++) {
            if (Integer.valueOf(sBinCode.substring(3 - i, 4 - i)) == 1) {
                anOmrFlag.set(i + 14, 1);
            }
        }

        //' Set form number to Binary
        sBinCode = omrDec2Bin(nForm, 5, sBinCode);

        //' Set OMR array
        for (int i = 0; i < 5; i++) {
            if (Integer.valueOf(sBinCode.substring(4 - i, 5 - i)) == 1) {
                anOmrFlag.set(i + 6, 1);
            }
        }

        //' Set End of Group Mark
        if ((nActualPage + 1 + nSingleDoubleSide) > nNumberOfPage) {
            anOmrFlag.set(1, 1);
        }

        //' Parity Check
        nParity = 0;
        for (int i = 0; i < (nMAXOMRMARK); i++) {
            if (anOmrFlag.get(i) == 1) {
                nParity = nParity + 1;
            }
        }

        if ((nParity % 2) == 1) {
            anOmrFlag.set(nOMRPARITY, 1);
        }
    }

    private static void omrPrintMarking(List<Integer> anOmrFlag, int nNumberOfOmr, PdfStamper stamper, int pageNum) throws Exception {
        for (int i = 0; i < nNumberOfOmr; i++) {
            if (anOmrFlag.get(i) == 1) {
                omrPrintSingleMarking(fLEFT, (fTOP - (i * (fSPACE + fHEIGHT))), fWIDTH, fHEIGHT, stamper, pageNum);
            }
        }
    }

    private static void omrPrintSingleMarking(float dLeft, float dTop, float dWidth, float dHeight, PdfStamper stamper, int pageNum) throws Exception {
        PdfContentByte under = stamper.getUnderContent(pageNum + 1);
        com.itextpdf.text.Image image = OmrBarcodeGenerator.createOrGetImage();
        //System.out.println("top:"+(under.getPdfDocument().top()+under.getPdfDocument().topMargin()-dTop)+ "<-> top:"+(dTop));
        image.setBorder(0);
        image.scaleAbsoluteWidth(dWidth);
        image.scaleAbsoluteHeight(dHeight);
        //System.out.println("height:"+(image.getHeight())+ "<-> width:"+(image.getWidth()));
        image.setAbsolutePosition(dLeft, under.getPdfDocument().top() + under.getPdfDocument().topMargin() - dTop);
        under.addImage(image);
    }

    //' Initialise OMR marks (set to off)
    private static void omrInit(List<Integer> anOmrFlag) {
        //' Initialise Gate Mark (must be on)
        anOmrFlag.set(0, 1);
        //' Initialise the rest (set to off)
        for (int i = 1; i < nMAXOMRMARK; i++) {
            anOmrFlag.set(i, 0);
        }
    }

    //' Convert Dec number to Bin number
    private static String omrDec2Bin(int nDec, int nUnitLen, String sOutput) {

        //' Initialise variable
        sOutput = "";

        while (nDec > 1) {
            sOutput = String.valueOf(nDec % 2) + sOutput;
            nDec = nDec / 2;
        }

        if (nDec == 1) {
            sOutput = "1" + sOutput;
        }

        //' Add extra zero
        if (sOutput.length() < nUnitLen) {
            int gap = nUnitLen - sOutput.length();
            for (int i = 0; i < gap; i++) {
                sOutput = "0" + sOutput;
            }
        }
        return sOutput;
    }

    private static int getNFormStart() {
        try {
            File f = new File(PAGE_STORE_FILE);
            if (!f.exists()) {
                return 0;
            }
            Properties prop = new Properties();
            prop.load(new FileInputStream(f));
            return Integer.valueOf((String) prop.get("nFromStart"));
        } catch (Exception e) {
            return 0;
        }
    }

    private static void saveNFormStart(int nFormStart) throws Exception {
        File f = new File(PAGE_STORE_FILE);
        if (!f.exists()) {
            f.createNewFile();
        }
        Properties prop = new Properties();
        prop.load(new FileInputStream(f));
        prop.setProperty("nFromStart", "" + nFormStart);
		/*		FileWriter fw = new FileWriter(f, false);
		fw.write("nFromStart = "+ nFormStart+ "\r\n");
		fw.close();*/
    }

    private static void complete(boolean sComInd) throws Exception {
        File f = new File(PAGE_STORE_FILE);
        if (!f.exists()) {
            f.createNewFile();
        }

        Properties prop = new Properties();
        prop.load(new FileInputStream(f));
        prop.setProperty("complete", "" + sComInd);
    }

    private static void ormPrintAll(int nNumberOfPage, int nPageCounter, PdfStamper stamper) throws Exception {
        List<String> paths = new ArrayList<String>();
        //' Minimise windows
        //    Application.WindowState = wdWindowStateMinimize

        //' declare variables
        //int nNumberOfPage = 10;
        //int nCurrentPage = 0;
        //int nCurrentFile = 0;
        //int nPageCounter = 0;
        int nFormStart = getNFormStart();
        List<Integer> anOmrFlag = new ArrayList<Integer>(nMAXOMRMARK);
        for (int i = 0; i < nMAXOMRMARK; i++) {
            anOmrFlag.add(0);
        }

        //' Get Statisitcs
        //   ActiveDocument.ComputeStatistics (wdStatisticPages)

        //' Initialise variables
        //nPageCounter = 0;

        //' Get Form Group
        //   sCurrentPQ = System.PrivateProfileString(FileName:="", Section:=sFormLoc, Key:=sPQLoc)
        //  sFormStart = System.PrivateProfileString(FileName:="", Section:=sFormLoc, Key:=sCurrentPQ)
        nFormStart = 0;

        //' Get number of page
        //    nNumberOfPage = ActiveDocument.BuiltInDocumentProperties(wdPropertyPages)

        //' Goto the top of document
        //   Selection.HomeKey Unit:=wdStory

        //' Loop for each page
        for (int i = 0; i < nNumberOfPage; i = i + 2) {

            if (i % nSingleDoubleSide == 0) {
                //' Determine OMR marks
                omrGenMarking(anOmrFlag, nPageCounter, i, nNumberOfPage, nFormStart);

                printAnOmrFlag(anOmrFlag);
                //' Draw OMR marks
                omrPrintMarking(anOmrFlag, nMAXOMRMARK, stamper, i);
            }

            //' Jump to Next Page
            //for(int j = 1;j<nSingleDoubleSide;j++){
            //Application.Browser.Next
            //}
            //' Next page
            nPageCounter = nPageCounter + 1;
            if (nFormStart < nMaxForm)
                nFormStart = nFormStart + 1;
            else
                nFormStart = 0;
        }

        //' Update Form in registry
        //   System.PrivateProfileString(FileName:="", Section:=sFormLoc, Key:=sCurrentPQ) = nFormStart
        saveNFormStart(nFormStart);
        //' Update Complete Indicator
        //    complete (sVPLoc);
        complete(true);
    }

    private static synchronized Image createOrGetImage() throws Exception {
        if (lineImage == null) {
            BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = image.createGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 10, 10);
            File f = new File(TEMP_PATH + "/line.jpg");
            ImageIO.write(image, "jpeg", f);
            com.itextpdf.text.Image itextImage = com.itextpdf.text.Image.getInstance(f.getAbsolutePath());
            lineImage = itextImage;
            System.out.println(f.getAbsolutePath());
        }
        return lineImage;
    }


    public static void printBarcode(String inputFile, String outputFile) throws Exception {

        PdfReader reader = new PdfReader(inputFile);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
        int total = reader.getNumberOfPages();
        ormPrintAll(total, 0, stamper);
        stamper.close();
    }

    public static void main(String[] args) throws Exception {
//		printBarcode("c:/cssa-english.pdf", "c:/cssa-english111.pdf", 550, 20, 500, 5.0f);
//		printBarcode("c:/cssa-english.pdf", "c:/cssa-english222.pdf", 550, 20, 500, 10.0f);
//		printBarcode("c:/cssa-english.pdf", "c:/cssa-english333.pdf", 550, 20, 500, 15.0f);
//		printBarcode("c:/cssa-english.pdf", "c:/cssa-english444.pdf", 550, 20, 500, 20.0f);
//		printBarcode("c:/cssa-english.pdf", "c:/cssa-english555.pdf", 550, 20, 500, 40.0f);
//		printBarcode("c:/cssa-english.pdf", "c:/cssa-english666.pdf", 550, 20, 500, 8.0f);
//		printBarcode("c:/cssa-english.pdf", "c:/cssa-english777.pdf", 550, 20, 500, 13.0f);
        printBarcode("c:/cssa-english.pdf", "c:/cssa-english2.pdf");
//		printBarcode("c:/cssa-chinese.pdf", "c:/cssa-chinese2.pdf");
    }

    private static void printAnOmrFlag(List<Integer> anOmrFlag) {
        System.err.print("\n============================\n");
        for (Integer i : anOmrFlag)
            System.err.print(i + ",");
        System.err.print("\n============================\n");
    }
}
