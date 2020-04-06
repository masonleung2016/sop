package sop.util.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.medsea.mimeutil.MimeUtil;
import sop.util.BaseSys;
import sop.util.Sys;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:17
 * @Package: sop.util.io
 */

public class FileUtil {
    public static final String MIME_TXT = "text/plain";
    
    public static final String MIME_RTF = "application/rtf";
    
    public static final String MIME_HTM = "text/html";
    
    public static final String MIME_PDF = "application/pdf";
    
    public static final String MIME_PS = "application/postscript";
    
    public static final String MIME_TIF = "image/tiff";
    
    public static final String MIME_PNG = "image/png";
    
    public static final String MIME_GIF = "image/gif";
    
    public static final String MIME_JPG = "image/jpeg";
    
    protected static final int BUFFER_SIZE = 4096;
    
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    
    private static final Hashtable<String, String> mimeSet;

    static {
        mimeSet = new Hashtable<String, String>();
        mimeSet.put("txt", MIME_TXT);
        mimeSet.put("rtf", MIME_RTF);
        mimeSet.put("htm", MIME_HTM);
        mimeSet.put("html", MIME_HTM);
        mimeSet.put("pdf", MIME_PDF);
        mimeSet.put("eps", MIME_PS);
        mimeSet.put("tif", MIME_TIF);
        mimeSet.put("png", MIME_PNG);
        mimeSet.put("gif", MIME_GIF);
        mimeSet.put("jpg", MIME_JPG);
    }

    static {
        eu.medsea.mimeutil.MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        eu.medsea.mimeutil.MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
    }

    /**
     * Append input to the output file
     *
     * @param input  File input
     * @param output Output file
     * @throws IOException
     */
    
    public static void appendFile(File input, File output) throws IOException {
        copyFile(input, output, true);
    }

    /**
     * Append content to the inFile
     *
     * @param inFile  File to be append on
     * @param content String content to append
     * @throws IOException
     */
    public static void appendFile(File inFile, String content) throws IOException {
        appendFile(inFile, content, true);
    }

    /**
     * Append content to the inFile
     *
     * @param inFile  File to be append on
     * @param content String content to append
     * @param append  whether append content to file or not
     * @throws IOException
     */
    public static void appendFile(File inFile, String content, boolean append) throws IOException {
        FileWriter oFW = null;
        BufferedWriter oBW = null;
        PrintWriter out = null;
        File parentD = inFile.getParentFile();
        if (!parentD.exists()) {
            boolean mkD = parentD.mkdirs();
            if (!mkD) {
                throw new IOException("Unable to make parent diretory," + parentD);
            }
        }
        try {
            oFW = new FileWriter(inFile, append);
            oBW = new BufferedWriter(oFW);
            out = new PrintWriter(oBW, true);
            out.println(content);
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (out != null)
                out.close();
            if (oBW != null)
                oBW.close();
            if (oFW != null)
                oFW.close();
        }
    }

    /**
     * Build file's parent dir if not exists
     *
     * @param inFile File to be checked.
     * @throws IOException
     */
    public static void buildDir(File inFile) throws IOException {
        File parentD = inFile.getParentFile();
        if (!parentD.exists()) {
            boolean mkD = parentD.mkdirs();
            if (!mkD) {
                throw new IOException("Unable to make parent diretory," + parentD);
            }
        }
    }

    /**
     * Build file's parent dir if not exists
     *
     * @param inFile File to be checked.
     * @throws IOException
     */
    public static void buildDir(String inFile) throws IOException {
        File parentD = new File(inFile).getParentFile();
        if (!parentD.exists()) {
            boolean mkD = parentD.mkdirs();
            if (!mkD) {
                throw new IOException("Unable to make parent diretory," + parentD);
            }
        }
    }

    /**
     * Concat input1 & input2 and the generate the output file
     *
     * @param input1 First file
     * @param input2 Second file
     * @param output Write the output to this file
     * @throws IOException
     */
    public static void concatFile(File input1, File input2, File output) throws IOException {
        copyFile(input1, output);
        appendFile(input2, output);
    }

    /**
     * Copy only the content of a directory into another directory.
     *
     * @param srcPath         the source directory
     * @param destinationPath the destination directory
     */
    public static void copyDir(File srcDir, File destDir) throws IOException {
        if (srcDir.isDirectory()) {
            if (destDir.exists() != true) {
                boolean mkD = destDir.mkdirs();
                if (!mkD) {
                    throw new IOException("Unable to make destDir," + destDir);
                }
            }
            String list[] = srcDir.list();
            for (int i = 0; i < list.length; i++) {
                String src = srcDir.getAbsolutePath() + System.getProperty("file.separator") + list[i];
                String dest = destDir.getAbsolutePath() + System.getProperty("file.separator") + list[i];
                copyDir(new File(src), new File(dest));
            }
        }
    }

    public static void purgeFiles(File purgeDir, Date currDate, int retentionPeriod,
                                  String dirPurgeType) throws IOException {
        FilesRemover.purgeFiles(purgeDir, currDate, retentionPeriod, dirPurgeType);
    }

    public static void removeFiles(List<File> fileL, File houseKeepDir) {
        FilesRemover.removeFiles(fileL, houseKeepDir);
    }

    public static void removeFolder(File folder) {
        FilesRemover.removeFolder(folder);
    }


    public static void removeFile(File file) {
        FilesRemover.removeFile(file);
    }

    /**
     * Copy input file to the output
     *
     * @param input  Input
     * @param output Output
     * @throws IOException
     */
    public static void copyFile(File input, File output) throws IOException {
        copyFile(input, output, false);
    }

    /**
     * Read the file to string
     *
     * @param inFile input file
     * @return String content
     * @throws IOException
     */
    public static String readFile(File inFile) throws IOException {
        return FileUtils.readFileToString(inFile);
    }

    /**
     * Write content to the inFile
     *
     * @param inFile  File to write upon
     * @param content String content
     * @throws IOException
     */
    public static void writeFile(File inFile, String content) throws IOException {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(inFile)));
            dos.writeBytes(content);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        } finally {
            if (dos != null)
                dos.close();
        }
    }

    /**
     * Write map entries to the file
     *
     * @param <T>    Key
     * @param <E>    Value
     * @param map    Write the map entries to the file
     * @param inFile File to write upon
     * @throws IOException
     */
    public static <T, E> void writeFile(Map<T, E> map, File inFile) throws IOException {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        PrintWriter out = null;
        try {
            fos = new FileOutputStream(inFile);
            bos = new BufferedOutputStream(fos);
            out = new PrintWriter(bos);
            Iterator<Entry<T, E>> entryI = map.entrySet().iterator();
            Entry<T, E> entry;
            while (entryI.hasNext()) {
                entry = entryI.next();
                out.println(entry.getKey() + "=" + entry.getValue());
            }
            out.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (bos != null)
                    bos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ioe) {
            }
        }
    }

    /**
     * Copy input file to the output
     *
     * @param input  Input
     * @param output Output
     * @param append true if append
     * @throws IOException
     */
    private static void copyFile(File input, File output, boolean append) throws IOException {
        FileInputStream in = new FileInputStream(input);
        FileOutputStream out = new FileOutputStream(output.getPath(), append);
        byte[] buffer = new byte[BUFFER_SIZE];
        int numRead = in.read(buffer);
        while (numRead > 0) {
            out.write(buffer, 0, numRead);
            numRead = in.read(buffer);
        }
        out.close();
        in.close();
    }

    /*
     * Get the extension of a file.
     */
    public static String getExtension(String fileName) {
        String ext = "";
        int i = fileName.lastIndexOf('.');

        if (i > 0) {
            ext = fileName.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static String getMimeType(URL url) {

        try {
            return getMimeType(url.openStream());
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    public static String getMimeType(File file) {


        String typeStr = null;
        Collection types = MimeUtil.getMimeTypes(file);
        if (types != null && types.size() >= 1) {
            for (Object type : types) {
                logger.debug("" + type);
                typeStr = String.valueOf(type);
                break;
            }
        }
        return typeStr;

    }

    public static String getMimeType(InputStream ins) {
        String typeStr = null;
        Collection types = MimeUtil.getMimeTypes(ins);
        if (types != null && types.size() >= 1) {
            for (Object type : types) {
                logger.debug("" + type);
                typeStr = String.valueOf(type);
                break;
            }
        }
        return typeStr;

    }

    public static String getMimeType(byte[] b) {
        String typeStr = null;
        Collection types = MimeUtil.getMimeTypes(b);
        if (types != null && types.size() >= 1) {
            for (Object type : types) {
                logger.debug("" + type);
                typeStr = String.valueOf(type);
            }
        }
        return typeStr;

    }

    /***********************************
     *
     * @param InputStream
     * @param String
     * @return ture = no virus false = has virus and delete file
     * @throws Exception
     */
    public static boolean checkVirus(InputStream fileinputstream, String filepath) throws IOException {
        boolean result = false;

        String filename = UUID.randomUUID().toString() + ".tmp";

        // String filepath = System.props.getProperty("antivirus.file.path");
        // InputStream in = new BufferedInputStream(new
        // FileInputStream("avparam.properties"));

        // InputStream in = (new
        // FileUtils()).getClass().getResourceAsStream("/"+"avparam.properties");

        // System.out.println("avparam.properties " + in);

        // PropertyResourceBundle properties = new PropertyResourceBundle(in);
        // String filepath = "d:\\testing\\";

        // String filepath = properties.getString("antivirus.file.path");

        createFile(fileinputstream, filename, filepath);
        File file = new File(filepath, filename);
        result = file.exists();
        file.delete();

        return result;
    }

    /**
     * create file
     *
     * @param fileinputstream
     * @param filename
     * @param filepath
     */
    public static void createFile(InputStream fileinputstream, String filename, String filepath) {
        try {
            // FileInputStream fIn = new FileInputStream("e:/in.txt");
            System.out.println("File Name: " + filename);
            File dir = new File(filepath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(file);

            while (fileinputstream.available() > 0) {
                byte[] b = new byte[10];
                int nResult = fileinputstream.read(b);
                if (nResult == -1)
                    break;

                fOut.write(b, 0, nResult);
            }
            fileinputstream.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a unique file name
     *
     * @return string
     */
    public static String getUniqueFileName(String originalName) {
        originalName = StringUtils.trimToEmpty(originalName);
        String hash = StringUtils.leftPad(String.valueOf(Math.abs(originalName.hashCode() + BaseSys.getTimestamp().hashCode())), 10, "0");
        originalName = StringUtils.deleteWhitespace(originalName);
        String[] tokens = StringUtils.split(originalName, ".");
        String ext = null;
        String name = "";
        if (tokens != null && tokens.length > 0) {
            name = tokens[0];

            if (tokens.length > 1) {
                ext = tokens[tokens.length - 1];
            }
        }
        if (name != null && name.length() > 20) {
            name = StringUtils.left(name, 20);
        }

        name = StringUtils.rightPad(name, 20, "a");
        name = StringUtils.lowerCase(name);

        if (ext != null) {
            return StringUtils.join(new String[]{name, "_", hash, ".", ext});
        } else {
            return StringUtils.join(new String[]{name, "_", hash,});
        }
    }

    /**
     * Generate a unique file name
     *
     * @return string
     */
    public static String getUniqueFileName() {
        return getUniqueFileName(null);
    }

    /**
     * Format the status file name
     *
     * @param oriFileName Original file name
     * @return Formatted file name
     */
    public static String appendDateTimeToFileName(String oriFileName) {
        StringBuffer buf = new StringBuffer();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String dateS = sdf.format(new Date());

        buf.append(dateS);
        buf.append("-");
        buf.append(oriFileName);

        return buf.toString();
    }

    public static String mergeSubDirectory(String[] array) {
        if (array == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String p : array) {
            if (p != null) {
                p = p.replace('\\', '/');
                if (!p.startsWith("/")) {
                    p = "/" + p;
                }
                if (p.endsWith("/")) {
                    p = StringUtils.removeEnd(p, "/");
                }

            }
            sb.append(p);
        }
        sb.append("/");
        String path = sb.toString();
        if (Sys.isUnix()) {
            return path;
        } else {
            return StringUtils.removeStart(path, "/");
        }
    }

    /**
     * @param response - HTTP servlet response
     * @param io       - input Stream.
     * @param mimeType - context type
     * @param fileName - download save as file name.
     * @return
     */
    public static boolean downloadFile(HttpServletResponse response, InputStream inputStream, String mimeType, String fileName) {
        ServletOutputStream os = null;
        try {
            String contentDisposition = "attachment; filename=\"" + fileName + "\";";
            response.setHeader("Content-Disposition", new String(contentDisposition.getBytes("UTF-8"), "ISO-8859-1"));
            response.setContentType(mimeType);
            os = response.getOutputStream();
            writeToOutputStream(inputStream, os);
            return true;

        } catch (Exception e) {
            logger.error("error in downloading file.", e);
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private static void writeToOutputStream(InputStream fis, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int length = 0;
        while (true) {
            length = fis.read(buffer);
            if (length == -1) {
                break;
            }
            os.write(buffer, 0, length);
        }
    }

    public boolean isMimeMatchExt(String fileName, String mime) {
        return mime.equals(mimeSet.get(getExtension(fileName)));
    }
}
