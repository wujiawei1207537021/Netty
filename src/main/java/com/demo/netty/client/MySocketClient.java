package com.demo.netty.client;


import java.io.*;
import java.net.Socket;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-28 10:29 上午
 */
public class MySocketClient {
    private Socket soc = null;
    private String server = "";
    private int port = 0;

    public MySocketClient(String server, int port) {
        super();
        this.server = server;
        this.port = port;
        try {
            soc = new Socket(server, port);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    //给服务器发送消息
    public void sendMsgToServer(String msg) {
        try {
            OutputStream out = soc.getOutputStream();
            out.write(msg.getBytes());
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    //从服务器接收消息
    public void recMsgFromServer() {
        byte[] b = null;
        StringBuilder text = null;
        while (true) {
            try {
                InputStream in = soc.getInputStream();
                b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) != -1) {
                    String strText = new String(b, 0, len);
                    System.out.println(strText);
                    writeDataToTxt(strText);
                }
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

    public void writeDataToTxt(String strText) {
        String fileName = "E:\\Desktop\\Temp\\01.txt";
        File f = new File(fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f, f.exists());
            strText = "\n" + strText;
            out.write(strText.getBytes());
            out.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
            try {
                out.close();
            } catch (IOException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MySocketClient mySocketClient=new MySocketClient("127.0.0.1",8888);
        mySocketClient.sendMsgToServer("1111");
    }


}
