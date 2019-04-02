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

    @Override
    public void destroy() {
        threadPool.shutdown();
    }

    @Override
    public void afterPropertiesSet() {
        threadPool = Executors.newCachedThreadPool();
        MonitorThread monitorThread = new MonitorThread();
        threadPool.execute(monitorThread);
    }

    private class WorkThread extends Thread {

    }

    private class MonitorThread extends Thread {
        int port;
        ServerSocket serverSocket;
        @Override
        public void run() {
            try {
                port = 8888;
                serverSocket = new ServerSocket(port);
                while(true) {
                    Socket socket = serverSocket.accept();
                    if(socket != null) {
                        System.out.println("链接的用户ip = "+socket.getInetAddress());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
