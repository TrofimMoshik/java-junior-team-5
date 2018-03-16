package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Trofim Moshik on 15.03.2018.
 *
 * Обеспечение работы клиента
 */
public class ClientSession /*implements Runnable*/{
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    /**
     * Организация обмена сообщениями с сервером
     */
    ClientSession() {

        try {

            //Подключаемся к серверу и получаем потоки in и out для передачи сообщений
            socket = new Socket("localhost", 6000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            //Запускаем вывод всех входящих сообщений в консоль
            Resender resender = new Resender(in);
            Thread thread = new Thread(resender);
            thread.start();

            //Пока пользователь не введет "exit" отправляем на сервер все, что введено из консоли
            Scanner scanner = new Scanner(System.in);
            String s = "";
            while (!"exit".equals(s)) {
                s = scanner.nextLine();
                out.println(s);
            }

            //Прерываем вывод входящих сообщений в консоль
//            thread.interrupt();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * Закрытие выходного, входного потоков и сокета
     */
    private void close() {
        try {

            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            System.err.println("Потоки не были закрыты!");
        }
    }

    /**
     * Прием сообщений от сервера и вывод их в консоль
     */
//    @Override
//    public void run() {
//        try {
//
//            while (!Thread.interrupted()) {
//                String s = in.readLine();
//                System.out.println(s);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
