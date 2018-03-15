
/**
 * Created by Trofim Moshik on 15.03.2018.
 */
public class MultiClientServer extends Thread {
    public static void main(String[] args) {

        Thread connectionLoop = new Thread(new ServerAcceptor());

        connectionLoop.start();

    }
}
