package com.demo.netty.client;

import com.demo.netty.client.packet.LinkRegistrationMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * jdk socket 客户端
 */
public class JDKSocketClient {

    private static Socket socket;
    public static boolean isSuccess = false;

    public static void main(String[] args) {
        System.out.println("客户端启动");
        connect();
    }

    public static void connect() {
        try {
            socket = new Socket("218.5.80.28", 7810);
            System.out.println("链接成功");
            isSuccess = true;
            // 输入流
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            // 输出流
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("测试消息");
            new Thread(new Test(oos)).start();
            new Thread(new ClientSend(socket, oos)).start();
            new Thread(new ClientMonitor(socket, ois)).start();
        } catch (Exception e) {
            isSuccess = false;
            reconnect();
            e.printStackTrace();
        }
    }

    public static void reconnect() {
        while (!isSuccess) {
            try {
                System.out.println("链接失败正在重新连接。。。。");
                Thread.sleep(5000);
                connect();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

class Test implements Runnable {
    private ObjectOutputStream oos;

    public Test(ObjectOutputStream oos) {
        this.oos = oos;
    }

    @Override
    public void run() {
        System.out.println("qidongceshi");
        LinkRegistrationMessage linkRegistrationMessage = new LinkRegistrationMessage();
        try {
            oos.write(linkRegistrationMessage.getBytes());
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 发送消息
class ClientSend implements Runnable {

    private ObjectOutputStream oos;
    private Socket socket;

    public ClientSend(Socket socket, ObjectOutputStream oos) {
        this.socket = socket;
        this.oos = oos;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入发送内容：");
            String msg = scanner.nextLine();
            try {
                JDKSocketMsg jdkSocketMsg = new JDKSocketMsg();
                jdkSocketMsg.setMsg(msg);
                oos.writeObject(jdkSocketMsg);
                oos.flush();
            } catch (Exception e) {
                try {
                    socket.close();
                    JDKSocketClient.isSuccess = false;
                    JDKSocketClient.reconnect();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}

// 监听消息
class ClientMonitor implements Runnable {

    private ObjectInputStream ois;
    private Socket socket;

    public ClientMonitor(Socket socket, ObjectInputStream ois) {
        this.socket = socket;
        this.ois = ois;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.print(ois.readObject());
            } catch (Exception e) {
                try {
                    socket.close();
                    JDKSocketClient.isSuccess = false;
                    JDKSocketClient.reconnect();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}


