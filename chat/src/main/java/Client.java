import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6666);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())
        ) {
            out.writeUTF("str1");
            out.writeUTF("str2");
            out.writeUTF("str3");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}