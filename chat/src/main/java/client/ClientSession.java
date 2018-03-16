package client;

import server.Connection;
import server.ServerAcceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by Trofim Moshik on 15.03.2018.
 * <p>
 * Обеспечение работы клиента
 */
public class ClientSession implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String nickname;

    /**
     * Закрытие выходного, входного потоков и сокета
     */
    private void close() {
        try {

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.err.println("Потоки не были закрыты!");
        }
    }

    /**
     * Прием сообщений от сервера и вывод их в консоль
     */
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                String s = in.readLine();
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("Соединение закончено!!!");
        }
    }

    /**
     * Организация обмена сообщениями с сервером
     */
    void execute() {
        try {

            //Подключаемся к серверу и получаем потоки in и out для передачи сообщений
            socket = new Socket("localhost", 6000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Запускаем вывод всех входящих сообщений в консоль
            Thread thread = new Thread(this);
            thread.start();

            //Пока пользователь не введет "exit" отправляем на сервер все, что введено из консоли
            Scanner scanner = new Scanner(System.in);
            System.out.println("You are connect to server!");

            System.out.println("Enter your name, please!");
            nickname = scanner.nextLine().toUpperCase();

            System.out.printf("Welcome to our chat, %s!\r\n", nickname);

            String s = "";
            while (true) {
                s = scanner.nextLine();

                if (s.contains("exit")) {
                    break;
                }

                if (s.length() > 150) {
                    s = "";
                    System.out.println("This is too big string! Try again");
                }

                if (s.contains("/snd")) {
                    out.println(nickname + ":" + s.substring(4, s.length()));
                } else if (s.contains("/hist")) {
                    String[] parts = s.split(" */ *");
                    if (parts.length <= 2) {
                        Files.lines(Paths.get("C:\\Users\\pp183\\Desktop\\temp\\java-junior-team-5\\chat\\src\\main\\resources\\history.txt"),
                                StandardCharsets.UTF_8).forEach(System.out::println);
                    } else {
                        int last = Integer.parseInt(parts[parts.length - 1]);
                        int first = Integer.parseInt(parts[parts.length - 2]);
                        if ((last - first) < 101) {
                            try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\pp183\\Desktop\\temp\\java-junior-team-5\\chat\\src\\main\\resources\\history.txt"),
                                    StandardCharsets.UTF_8)) {
                                lines.skip(first - 1).limit(last - first).forEach(System.out::println);
                            }
                        }
                    }
                } else {
                    System.out.println("You entered wrong command! Use \"/snd\"!");
                }
            }

            //Прерываем вывод входящих сообщений в консоль
            thread.interrupt();
        } catch (Exception e) {
            System.out.println("Сервер упал!");

        } finally {
            close();
        }
    }
}