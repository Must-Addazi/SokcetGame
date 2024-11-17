package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT extends Thread {
    private int nbClient;
    public static void main(String[] args) {
        new ServeurMT().start();
        System.out.println("la suite de l'application");
    }

    @Override
    public void run() {
        try {
            int port = 1234;
            ServerSocket ss= new ServerSocket(port);
            System.out.println("démarrage du serveur");
            while (true){
                Socket socket=ss.accept();
                ++nbClient;
                new Conversation(socket,nbClient).start();

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        }
    class Conversation extends Thread{
        private Socket socket;
        private int numeroClient;
        public Conversation(Socket socket,int numeroClient){
            this.socket =socket;
            this.numeroClient=numeroClient;
        }
        @Override
        public void run() {
            try {
                InputStream is= socket.getInputStream();
                InputStreamReader isr= new InputStreamReader(is);
                BufferedReader br= new BufferedReader(isr);
                OutputStream os= socket.getOutputStream();
                PrintWriter pwd= new PrintWriter(os,true);
                String IP=socket.getRemoteSocketAddress().toString();
                System.out.println("connexion du client n: "+numeroClient+" IP:"+IP);
                pwd.println("Bienvenue,vous ete le client n :"+numeroClient);
                while (true){
                    String req= br.readLine();
                    System.out.println("le client "+IP+" a envoyé une requte "+req);
                    pwd.println("length = "+req.length());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
