package client;

import Service.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.Scanner;

public class PGPClient {
    public void start() {
        try {
            System.out.println("Welcome to the PGP Email Client!");
            System.out.print("Enter your email address: ");
            Scanner scanner = new Scanner(System.in);
            String from = scanner.nextLine();

            System.out.print("Enter recipient email address: ");
            String to = scanner.nextLine();

            System.out.println("Generating PGP key pair...");
            KeyManager.generateKeyPair("sender");
            KeyManager.generateKeyPair("receiver");
            System.out.println("Done.");

            PrivateKey senderPrivate = KeyManager.loadPrivateKey("sender");
            PublicKey senderPublic = KeyManager.loadPublicKey("sender");
            PublicKey receiverPublic = KeyManager.loadPublicKey("receiver");
            PrivateKey receiverPrivate = KeyManager.loadPrivateKey("receiver");
            //ndryshimi
            KeyManager.printKey(senderPrivate, "Sender Private Key");
            KeyManager.printKey(senderPublic, "Sender Public Key");
            KeyManager.printKey(receiverPublic, "Receiver Public Key");


            System.out.println("Please enter your email content below:");
            System.out.print("> ");
            String message = scanner.nextLine();

            System.out.println("Encrypting and signing the email...");
            String encrypted = Services.CryptoService.encrypt(message, receiverPublic);
            String signature = SignatureService.sign(message, senderPrivate);

            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(from);
            out.writeObject(to);
            out.writeObject(encrypted);
            out.writeObject(signature);
            out.close();
            socket.close();
            System.out.println("Email successfully sent to " + to);

            System.out.println("Awaiting encrypted messages...");
            Thread.sleep(1000);

            Socket response = new Socket("localhost", 4445);
            ObjectInputStream in = new ObjectInputStream(response.getInputStream());
            String encResponse = (String) in.readObject();
            String sigResponse = (String) in.readObject();
            in.close();
            response.close();

            String decrypted = Services.CryptoService.decrypt(encResponse, receiverPrivate);
            boolean verified = SignatureService.verify(decrypted, sigResponse, senderPublic);
            //ndryshimi
            System.out.println("[INFO] Encrypted Message (Base64):");
            System.out.println(encrypted);
            System.out.println("[INFO] Digital Signature (Base64):");
            System.out.println(signature);

            System.out.println("New encrypted email received. Decrypting...");
            System.out.println("The email from " + from + " has been successfully decrypted and verified.");

        } catch (Exception e) {
            System.err.println("[ERROR] Client: " + e.getMessage());
        }
    }
}
