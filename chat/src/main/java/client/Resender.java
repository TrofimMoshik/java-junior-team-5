package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Trofim Moshik on 15.03.2018.
 */
public class Resender implements Runnable{
    private BufferedReader in;

    public Resender(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {

            while (!Thread.interrupted()) {
                String s = in.readLine();
                System.out.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
