package sop.util.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:16
 * @Package: sop.util.io
 */

public class FilesRemover {
    public static final String TYPE_RECURSIVE = "R";
    public static final String TYPE_NON_RECURSIVE = "N";
    private static Logger LOG = LoggerFactory.getLogger(FilesRemover.class);

    /**
     * Remove file based upon retention period
     *
     * @param purgeDir        Purge directory
     * @param currDate        Calculated date from
     * @param retentionPeriod Retention period
     * @param dirPurgeType    Directory Purge type
     * @throws IOException
     */
    public static void purgeFiles(File purgeDir, Date currDate, int retentionPeriod,
                                  String dirPurgeType) throws IOException {
        File[] fileList = purgeDir.listFiles();
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(currDate);
        currCal.add(Calendar.DATE, -retentionPeriod);
        for (int i = 0; i < fileList.length; i++) {
            Date lastModifiedDate = new Date(fileList[i].lastModified());
            Calendar lastModifiedCal = Calendar.getInstance();
            lastModifiedCal.setTime(lastModifiedDate);
            if (fileList[i].isFile()) {
                if (currCal.after(lastModifiedCal)) {
                    boolean delF = FileUtils.deleteQuietly(fileList[i]);
                    if (!delF) {
                        FileUtils.forceDeleteOnExit(fileList[i]);
                    }
                }
            } else if (fileList[i].isDirectory()
                    && TYPE_RECURSIVE.equals(dirPurgeType)) {
                File subFolder = new File(fileList[i].toString());
                purgeFiles(subFolder, currDate, retentionPeriod, dirPurgeType);
            }
        }
    }

    /**
     * @param file Remove the file from file system
     */
    public static void removeFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("Passed-in file argument is null");
        } else {
            List<File> fileL = new ArrayList<File>();
            fileL.add(file);
            removeFiles(fileL, file);
        }
    }

    /**
     * Remove the list of files
     */
    public static void removeFiles(List<File> fileL, File houseKeepDir) {
        long start = System.currentTimeMillis();
        int count = 0;
        try {

            if (fileL == null || fileL.isEmpty()) {
                return;
            } else {
                count = fileL.size();
            }


            int size = fileL.size();
            File file;
            StringBuffer buf;
            String filePath;
            for (int i = 0; i < size; i++) {
                file = fileL.get(i);
                boolean isDirectory = file.isDirectory();
                filePath = file.getAbsolutePath();
                boolean hasDeleted = FileUtils.deleteQuietly(file);
                if (hasDeleted) {
                    buf = new StringBuffer();
                    if (isDirectory) {
                        buf.append("Directory housekeep including its sub-contents under dir,");
                    } else {
                        buf.append("File housekeep, ");
                    }
                    buf.append(filePath);
                    //               LogUtil.printInfo(buf.toString());
                } else {
                    try {
                        FileUtils.forceDeleteOnExit(file);
                        buf = new StringBuffer();
                        if (isDirectory) {
                            buf.append("Directory housekeep including its sub-contents under dir,");
                        } else {
                            buf.append("File housekeep, ");
                        }
                        buf.append(filePath);
                        //                  LogUtil.printInfo(buf.toString());
                    } catch (Exception ex) {
                        LOG.error("Exception caused while housekeeping file," + file, ex);
                    }
                }
            }
        } finally {
            if (count > 0) {
                long etime = System.currentTimeMillis() - start;
                StringBuffer buf = new StringBuffer();
                buf.append("Time used ");

                if (houseKeepDir != null) {
                    buf.append("in housekeep directory, ");
                    buf.append(houseKeepDir);
                    buf.append(", ");
                }
                buf.append("housekeep file object=");
                buf.append(count);
                buf.append(", etime-in-ms=");
                buf.append(etime);

                LOG.info(buf.toString());
            }
        }
    }

    public static boolean removeFolder(File folder) {
        if (folder.isDirectory()) {
            return FileUtils.deleteQuietly(folder);
        } else {
            LOG.warn("folder is not a directory");
        }

        return false;
    }
}

