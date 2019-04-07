import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ExecutorService threadPool = Executors.newCachedThreadPool();
    static class ListenThread extends Thread {
        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket("127.0.0.1", 8888);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(true) {
                try {
                    InputStream inFromServer = socket.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
                    String str = in.readUTF();
                    if(!str.isEmpty()) {
                        WorkThread workThread = new WorkThread(str);
                        threadPool.execute(workThread);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
