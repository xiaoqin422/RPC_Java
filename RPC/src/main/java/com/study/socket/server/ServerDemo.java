package com.study.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 包名: com.study.socket.server
 * 类名: ServerDemo
 * 创建用户: 25789
 * 创建日期: 2022年04月28日 23:54
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class ServerDemo {
    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(10);
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务器启动。。。");
            while (true){
                final Socket client = serverSocket.accept();
                System.out.println("有客户端连接");
                executors.execute(new Runnable() {
                    public void run() {
                        handle(client);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void handle(Socket client){
        try {
            System.out.println("客户端建立连接：" + client.getInetAddress() + ".线程：" + Thread.currentThread().getName());
            //客户端建立连接
            InputStream inputStream = client.getInputStream();
            byte[] b = new byte[1024];
            int len = inputStream.read(b);
            System.out.println("客户端发送消息：" + new String(b,0,len));
            OutputStream outputStream = client.getOutputStream();
            outputStream.write("服务器应答".getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (client != null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}