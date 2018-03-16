package server;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

/**
 * Created by Trofim Moshik on 15.03.2018.
 *
 * Класс содержит информацию о клиентском подключениию потоки in, out и сокет.
 * Имплементирует интерфейс Runnable.
 */
public class Connection implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    Connection(Socket socket) {
        this.socket = socket;

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {

            e.printStackTrace();
            close();

        }

    }

    @Override
    public void run() {

        try {

            String income;
            String afterDecorating;
            while (true) {
                income = in.readLine();
                if ("exit".equals(income)) break;
                afterDecorating = new MessageDecorator(income).decorate();

                Iterator<Connection> iterator = ServerAcceptor.clients.iterator();
                while (iterator.hasNext()) {
                    iterator.next().out.println(afterDecorating);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            close();
        }
    }

    void close() {

        try {

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Потоки не были закрыты!");
        }
    }
}

