package com.demo.netty.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/***
 * jdk socket 服务端
 */
public class JDKSocketServer {


    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("启动jdk socket 服务端");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerSend(socket)).start();
                new Thread(new ServerMonitor(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void restart() {

    }
}

// 发送消息
class ServerSend implements Runnable {

    private Socket socket;

    public ServerSend(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入发送内容：");
            String msg = scanner.nextLine();
            try {
                // 输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                JDKSocketMsg jdkSocketMsg=new JDKSocketMsg();
                jdkSocketMsg.setMsg(msg);
                oos.writeObject(jdkSocketMsg);
                oos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

// 监听消息
class ServerMonitor implements Runnable {

    private Socket socket;


    public ServerMonitor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                System.out.println(ois.readObject());
            } catch (Exception e) {
                try {
                    socket.close();
                    JDKSocketServer.start();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}

