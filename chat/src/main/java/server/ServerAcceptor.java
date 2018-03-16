package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Trofim Moshik on 15.03.2018.
 */
class ServerAcceptor {
    // Синхронизированное множество подключений клиентов. Одновременно имеет доступ только один тред.
    static Set<Connection> clients;

    ServerAcceptor() {
        clients = Collections.synchronizedSet(new HashSet<>());

        try (ServerSocket server = new ServerSocket(6000)){

            while (true) {
                //Ожидание сервером подключения
                Socket socket = server.accept();

                //Выделение, добавление в сет и запуск нового треда для вновь подключенного клиента
                Connection acceptor = new Connection(socket);
                clients.add(acceptor);
                Thread clientThread = new Thread(acceptor);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //Синхронизированное закрытие всех клиентских соединений
            //Доступ обеспечен только для одного треда одновременно
            try {
                synchronized (clients) {
                    for (Connection client : clients) {
                        client.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
