package com.study.chat;

import com.study.chat.client.NettyChatClient;
import com.study.chat.server.NettyChatServer;
import org.omg.IOP.TAG_JAVA_CODEBASE;

/**
 * 包名: com.study.chat
 * 类名: Main
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 13:14
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        NettyChatServer server = new NettyChatServer(9999);

        new Thread(() ->{
            server.run();
        }).start();
        Thread.sleep(2000);
        new Thread(() ->{
            new NettyChatClient("127.0.0.1",9999).run();
        }).start();
        Thread.sleep(2000);
        new Thread(() ->{
            new NettyChatClient("127.0.0.1",9999).run();
        }).start();
    }
}