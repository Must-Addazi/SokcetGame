package org.example.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket ss= new ServerSocket(port);
        System.out.println("j'attends la connexion ... en port" +port);
        Socket s= ss.accept();
        System.out.println("Connexion acceptée de " + s.getRemoteSocketAddress());
        InputStream is= s.getInputStream();
        OutputStream os = s.getOutputStream();
        System.out.println("j'attends que le client envoi un octet");
        int a= is.read();
        System.out.println("j'ai reçu un nombre "+a);
        int res = a*5;
        System.out.println("j'envoie la réponse "+res);
        PrintWriter pwd=new PrintWriter(os,true);
        pwd.println("reponse : "+res);
        os.write(res);
    }
}
