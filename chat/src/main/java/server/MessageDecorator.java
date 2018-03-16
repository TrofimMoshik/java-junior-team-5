package server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Trofim Moshik on 15.03.2018.
 */
class MessageDecorator {
    private ZonedDateTime zonedDateTime = ZonedDateTime.now();
    private String message;

    String decorate(String income) {
        return message = "Server Time: " + DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(zonedDateTime)+ " " + income;

    }
    void history(){
        if (!Files.exists(Paths.get("C:\\Users\\pp183\\Desktop\\temp\\java-junior-team-5\\chat\\src\\main\\resources\\history.txt"))){
            try {
                Files.createFile(Paths.get("C:\\Users\\pp183\\Desktop\\temp\\java-junior-team-5\\chat\\src\\main\\resources\\history.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path historyFile = Paths.get("C:\\Users\\pp183\\Desktop\\temp\\java-junior-team-5\\chat\\src\\main\\resources\\history.txt");
        try {
            Files.write(historyFile, (message + "\r\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

