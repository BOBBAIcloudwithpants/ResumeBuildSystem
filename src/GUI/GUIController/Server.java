package GUI.GUIController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(1056);
            while (true){
                Socket socket = serverSocket.accept();
                socketList.add(socket);
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
                System.out.println("Connect to Client!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
