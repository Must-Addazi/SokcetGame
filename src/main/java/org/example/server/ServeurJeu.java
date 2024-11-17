package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServeurJeu extends Thread {
    private int nbClient;
    private int nbSecret;
    private boolean fin=false;
    private String gagnant;
    public static void main(String[] args) {
        new ServeurJeu().start();
        System.out.println("la suite de l'application");
    }

    @Override
    public void run() {
        try {
            int port = 1234;
            ServerSocket ss= new ServerSocket(port);
            nbSecret= new Random().nextInt(0,1000);
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
                String IPClient=socket.getRemoteSocketAddress().toString();
                System.out.println("connexion du client n: "+numeroClient+" IP:"+IPClient);
                pwd.println("Bienvenue,vous ete le client n :"+numeroClient);
                pwd.println("Devinez le nombre secret");

                while (true) {
                    String req = br.readLine();
                    System.out.println("le client " + IPClient + " a envoyé une requte " + req);
                    int nombre=0;
                    boolean correctFormat = false;
                    try {
                        nombre = Integer.parseInt(req);
                        correctFormat = true;
                    } catch (NumberFormatException e) {
                        correctFormat = false;
                    }

                    System.out.println("nombre secret " + nbSecret);
                    if (correctFormat == true) {
                        if (fin == false) {
                            if (nombre > nbSecret) {
                                pwd.println("votre nombre est supérieur au nombre secret");
                            } else if (nombre < nbSecret) {
                                pwd.println("votre nombre est inférieur au nombre secret");
                            } else {
                                pwd.println("Bravo vous avez gagné");
                                gagnant = IPClient;
                                System.out.println("Bravo au gagnant IP client " + IPClient);
                                fin = true;
                            }
                        } else {
                            pwd.println("Le jeu est terminé,le gagnat est" + gagnant);
                        }
                    }else {
                        pwd.println("format de nombre incorrecte");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
