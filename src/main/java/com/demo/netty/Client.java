package com.demo.netty;

import com.demo.netty.client.JDKSocketMsg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    public static boolean connection_state = false;

    public static void main(String[] args){
        while (!connection_state) {
            connect();
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void connect(){
        try {
            socket = new Socket("127.0.0.1", 9999);
            connection_state = true;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            new Thread(new Client_listen(socket,ois)).start();
            new Thread(new Client_send(socket,oos)).start();
            new Thread(new Client_heart(socket,oos)).start();
        }catch (Exception e){
            e.printStackTrace();
            connection_state = false;
        }
    }

    public static void reconnect(){
        while (!connection_state){
            System.out.println("正在尝试重新链接.....");
            connect();
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

class Client_listen implements Runnable{
    private Socket socket;
    private ObjectInputStream ois;

    Client_listen(Socket socket,ObjectInputStream ois){
        this.socket = socket;
        this.ois = ois;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(ois.readObject());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Client_send implements Runnable{
    private Socket socket;
    private ObjectOutputStream oos;

    Client_send(Socket socket, ObjectOutputStream oos){
        this.socket = socket;
        this.oos = oos;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("请输入你要发送的信息：");
                String msg = scanner.nextLine();
                JDKSocketMsg jdkSocketMsg=new JDKSocketMsg();
                jdkSocketMsg.setMsg(msg);
                oos.writeObject(jdkSocketMsg);
                oos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                socket.close();
                Client.connection_state = false;
                Client.reconnect();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
}

class Client_heart implements Runnable{
    private Socket socket;
    private ObjectOutputStream oos;

    Client_heart(Socket socket, ObjectOutputStream oos){
        this.socket = socket;
        this.oos = oos;
    }

    @Override
    public void run() {
        try {
            System.out.println("心跳包线程已启动...");
            while (true){
                Thread.sleep(5000);
                JDKSocketMsg jdkSocketMsg=new JDKSocketMsg();
                jdkSocketMsg.setMsg("心跳");
                oos.writeObject(jdkSocketMsg);
                oos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                socket.close();
                Client.connection_state = false;
                Client.reconnect();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
}