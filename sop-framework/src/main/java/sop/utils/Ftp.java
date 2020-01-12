package sop.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

/**
 * ftp实现文件的上传下载
 *
 * @Author: LCF
 * @Date: 2020/1/9 11:29
 * @Package: sop.utils
 */

public class Ftp {


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url      FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param ftpPath  FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     */
    public static boolean upload(String url, int port, String username, String password, String dir, String fileName, InputStream fis) {
        FTPClient ftpClient = new FTPClient();
        boolean flag = false;
        try {
            ftpClient.connect(url, port);

            boolean loginFlag = ftpClient.login(username, password);
            if (loginFlag) {
                ftpClient.enterLocalPassiveMode();

                //设置文件类型（二进制）
                boolean setFileTypeFlag = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

                //如果目录不存在先建立该目录
                ftpClient.makeDirectory(dir);

                String path = dir + "/" + fileName;

                flag = ftpClient.storeFile(path, fis);
            }
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
        return flag;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param url       FTP服务器hostname
     * @param port      FTP服务器端口
     * @param username  FTP登录账号
     * @param password  FTP登录密码
     * @param ftpFile   FTP服务器上的要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
     */
    public static boolean download(String url, int port, String username, String password, String ftpFile, String localPath) {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        boolean flag = false;
        try {
            ftpClient.connect(url, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();

            fos = new FileOutputStream(localPath);

            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(ftpFile, fos);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
        return flag;
    }
}
