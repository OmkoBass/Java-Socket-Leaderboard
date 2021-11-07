import Models.Contestant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Main {
    private static final int port = 6666;

    private static final String[] names = {
            "Jack",
            "Baron",
            "Aimee",
            "Duke",
            "Chad",
            "Tatiana",
            "Lesley",
            "Freedom",
            "Liberty",
            "Barney",
            "Deimos"
    };

    public static void main(String[] args) throws IOException {
        Socket socket = null;

        ObjectOutputStream objectOutputStream;

        ServerSocket serverSocket = new ServerSocket(port);

        try {
            socket = serverSocket.accept();

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // 100 contestants
            for(int i = 0; i < 100; i++) {
                Random random = new Random();
                int randInt = random.nextInt(names.length);
                int score = random.nextInt(100);

                Contestant contestant = new Contestant(names[randInt], score);

                // Write the random contestant in the object stream
                // and send him
                objectOutputStream.writeObject(contestant);
                objectOutputStream.flush();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            if(socket != null) {
                socket.close();
            }

            e.printStackTrace();
        } finally {
            serverSocket.close();

            if(socket != null) {
                socket.close();
            }
        }
    }
}
