package org.bzhy.console.comm;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author: 白振宇
 * @Date： 2019/4/1 20:18
 */
@Component
public class GuardSocket implements InitializingBean, DisposableBean {

    private ExecutorService threadPool;
    private ServerSocket serverSocket;

    @Override
    public void destroy() throws IOException {
        serverSocket.close();
        threadPool.shutdownNow();
    }

    @Override
    public void afterPropertiesSet() {
        threadPool = Executors.newCachedThreadPool();
        MonitorThread monitorThread = new MonitorThread();
        threadPool.execute(monitorThread);
    }

    public void joinPool(String command) {
        WorkThread workThread = new WorkThread();
        workThread.setCommand(command);
        threadPool.execute(workThread);
    }

    private class WorkThread extends Thread {
        private String command;
        void setCommand(String command) {
            this.command = command;
        }
        //流传递command
    }

    private class MonitorThread extends Thread {
        private int port;
        @Override
        public void run() {
            try {
                port = 8888;
                serverSocket = new ServerSocket(port);
                while(true) {
                    Socket socket = serverSocket.accept();
                    if(socket != null) {
                        //向数据库中更新用户信息，并将状态设置未活跃状态
                        System.out.println("链接的用户ip = "+socket.getInetAddress());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}