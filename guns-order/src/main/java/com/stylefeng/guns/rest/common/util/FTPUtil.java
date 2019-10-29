package com.stylefeng.guns.rest.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-29 17:44
 */
@Slf4j
public class FTPUtil {

    // 地址 端口 用户名 密码
    private String hostName = "192.168.1.158";
    private Integer port = 21;
    private String userName = "ftp";
    private String password = "ftp";

    private FTPClient ftpClient = null;

    private void initFTPClient() {
        try {
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding("utf-8");
            ftpClient.connect(hostName, port);
            ftpClient.login(userName, password);
        } catch (Exception e) {
            log.error("初始化FTP失败", e);
        }
    }

    // 输入一个路径，然后将路径里的文件转换成字符串返回
    public String getFileStrByAddress(String fileAddress) {
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            initFTPClient();
            bufferedReader = new BufferedReader(new InputStreamReader(ftpClient.retrieveFileStream(fileAddress)));
            while(true) {
                String lineStr = bufferedReader.readLine();
                if (lineStr == null) {
                    break;
                }
                stringBuffer.append(lineStr);
            }

            ftpClient.logout();
        } catch (Exception e) {
            log.error("获取文件信息失败", e);
        } finally {

        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        FTPUtil ftpUtil = new FTPUtil();
        String fileStrByAddress = ftpUtil.getFileStrByAddress("seats/cgs.json");
        System.out.println(fileStrByAddress);
    }
}
