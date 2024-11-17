package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServeurChat extends Thread {
    private int nbClient;
    private List<Conversation> clients = new ArrayList<Conversation>();
    public static void main(String[] args) {
        new ServeurChat().start();
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
             Conversation conversation=   new Conversation(socket,nbClient);
             clients.add(conversation);
              conversation.start();

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        }
    class Conversation extends Thread{
        protected Socket socket;
        protected int numeroClient;
        public Conversation(Socket socket,int numeroClient){
            this.socket =socket;
            this.numeroClient=numeroClient;
        }
        public void broadCastMessage(String message,Socket socketClient,int numClient) throws IOException {
            for(Conversation client:clients){
                if(client.socket != socketClient) {
                    if (numClient == client.numeroClient || numClient==-1) {
                        PrintWriter printWriter = new PrintWriter(client.socket.getOutputStream(), true);
                        printWriter.println(message);
                    }
                }
            }
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
                    if (req.contains("=>")) {
                        String[] requestParams = req.split("=>");
                        if(requestParams.length==2){
                            String message= requestParams[1];
                            int nmbClient= Integer.parseInt(requestParams[0]);
                            broadCastMessage(message,socket,nmbClient);
                        }
                    }else {
                        broadCastMessage(req,socket,-1);
                    }

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
