package server;

import java.io.*;
import java.net.*;

public class PGPServer {
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(4444);
             ServerSocket replySocket = new ServerSocket(4445)) {

            System.out.println("Email server started, waiting for messages…");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                String from = (String) in.readObject();
                String to = (String) in.readObject();
                String encrypted = (String) in.readObject();
                String signature = (String) in.readObject();
                in.close();
                clientSocket.close();

                System.out.println("New encrypted email received from " + from);
                System.out.println("Email forwarded to " + to);

                Socket reply = replySocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(reply.getOutputStream());
                out.writeObject(encrypted);
                out.writeObject(signature);
                out.close();
                reply.close();
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Server: " + e.getMessage());
        }
    }
}
