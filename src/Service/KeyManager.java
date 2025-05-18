package Service;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class KeyManager {
    public static void generateKeyPair(String name) throws Exception {
        if (new File(name + "_public.key").exists() && new File(name + "_private.key").exists()) return;
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        try (FileOutputStream out = new FileOutputStream(name + "_private.key")) {
            out.write(pair.getPrivate().getEncoded());
        }
        try (FileOutputStream out = new FileOutputStream(name + "_public.key")) {
            out.write(pair.getPublic().getEncoded());
        }
    }

    public static PrivateKey loadPrivateKey(String name) throws Exception {
        byte[] key = new FileInputStream(name + "_private.key").readAllBytes();
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(key));
    }

    public static PublicKey loadPublicKey(String name) throws Exception {
        byte[] key = new FileInputStream(name + "_public.key").readAllBytes();
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key));
    }
    public static void printKey(Key key, String label) {
        System.out.println("[INFO] " + label + ":");
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println();
    }
}

