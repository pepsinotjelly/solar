package com.bubble.utils.Transfer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class FileTransferClient extends Socket {
    private static String SERVER_IP = "127.0.0.1";
    private static String SERVER_PORT = "7758";
    private Socket client;
    private FileInputStream fis;
    private DataOutputStream dos;
    private FileOutputStream fileOutputStream;

    public FileTransferClient() throws Exception {
        super(SERVER_IP, Integer.parseInt(SERVER_PORT));
        this.client = this;
        System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
    }

    public void sendFile() throws Exception {
        try {
            File file = new File("E:\\JDK1.6中文参考手册(JDK_API_1_6_zh_CN).CHM");
            if (file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());

                // 文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                // 开始传输文件
                System.out.println("======== 开始传输文件 ========");
                byte[] bytes = new byte[1024];
                int length = 0;
                long progress = 0;
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    progress += length;
                    System.out.print("| " + (100 * progress / file.length()) + "% |");
                }
                System.out.println();
                System.out.println("======== 文件传输成功 ========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
            if (dos != null)
                dos.close();
            client.close();
        }
    }
}
