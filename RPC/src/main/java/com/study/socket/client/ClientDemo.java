package com.study.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 包名: com.study.socket.client
 * 类名: ClientDemo
 * 创建用户: 25789
 * 创建日期: 2022年04月28日 23:53
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class ClientDemo {
    public static void main(String[] args) {
        while (true){
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(8888));
                OutputStream outputStream = socket.getOutputStream();
                Scanner sc = new Scanner(System.in);
                outputStream.write(sc.nextLine().getBytes());
                InputStream inputStream = socket.getInputStream();
                byte[] b = new byte[1024];
                int len = inputStream.read(b);
                System.out.println(new String(b,0,len));
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}