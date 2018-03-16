package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Trofim Moshik on 15.03.2018.
 */
class ServerAcceptor {
    // Синхронизированное множество подключений клиентов. Одновременно имеет доступ только один тред.
    static volatile Queue<Connection> clients;

    void execute() {
        clients = new LinkedList<>();

        try (ServerSocket server = new ServerSocket(6000)) {
            //Ожидание сервером подключения

            while (true) {
                Socket socket = server.accept();

                //Выделение, добавление в сет и запуск нового треда для вновь подключенного клиента
                Connection connection = null;
                try {
                    connection = new Connection(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (connection != null) {
                    Thread clientThread = new Thread(connection);
                    clientThread.start();
                    synchronized (clients) {
                        clients.add(connection);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //Синхронизированное закрытие всех клиентских соединений
            //Доступ обеспечен только для одного треда одновременно
            while (!clients.isEmpty()) {
                try {
                    synchronized (clients) {
                        for (Connection client : clients) {
                            client.close();
                            clients.remove(client);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }
        }
    }
