import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Trofim Moshik on 15.03.2018.
 */
public class ServerAcceptor implements Runnable {
Socket socket;



    @Override
    public void run() {
        try (ServerSocket portListener = new ServerSocket(6000)) {

            while (true) {

                try (Socket clientSession = portListener.accept();
                     InputStream inputStream = clientSession.getInputStream();
                     DataInputStream dataInputStream = new DataInputStream(inputStream)) {
                    //Thread thred = new Thred (new Client());

                    String s = dataInputStream.readUTF();

                    System.out.println(s);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

