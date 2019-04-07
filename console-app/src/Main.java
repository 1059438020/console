import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static Socket socket = null;
    static ExecutorService threadPool = Executors.newCachedThreadPool();
    static class ListenThread extends Thread {
        @Override
        public void run() {

            try {
                socket = new Socket("127.0.0.1", 8888);
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream inFromServer = null;
            DataInputStream in = null;

            while(true) {
                try {
                    inFromServer = socket.getInputStream();
                    in = new DataInputStream(inFromServer);
                    String str = in.readUTF();
                    if(!str.isEmpty()) {
                        WorkThread workThread = new WorkThread(str);
                        threadPool.execute(workThread);
                    }
                } catch (IOException e) {
                    try {
                        if (inFromServer != null) {
                            inFromServer.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                        socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
    static class WorkThread extends Thread {
        String str;
        WorkThread(String str) {
            this.str = str;
        }
        @Override
        public void run() {
            try {
                Runtime.getRuntime().exec(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ListenThread listenThread = new ListenThread();
        threadPool.execute(listenThread);
    }
}
