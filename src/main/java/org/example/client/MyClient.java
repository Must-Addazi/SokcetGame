package org.example.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) throws IOException {
        System.out.println("je me connecte au serveur");
        Socket s = new Socket("localhost",1234);
        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        System.out.println("donner un nombre");
        int nb= scanner.nextInt();
        System.out.println("J'envoie le nombre "+nb+" au serveur");
        os.write(nb);
        System.out.println("j'attends la réponse du serveur ..");
         int rep=  is.read();
        System.out.println(" la réponse du serveur est "+rep);
    }
}
