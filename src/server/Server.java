package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final int PORT = 8080;
    private ArrayList<ClientHandler> clients =new ArrayList<ClientHandler>();

    public Server(){
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            while (true){
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен!");
                serverSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void sendMessageToAllClients(String msg){
        for (ClientHandler o : clients){
            o.sendMsg(msg);
        }
    }
    public void removeClient(ClientHandler client){
        clients.remove(client);
    }
}
