package GUI.GUIController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket socket = null;
    BufferedReader input = null;
    PrintWriter output = null;

    public ServerThread(Socket socket){
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){

        try {
            if (socket.getPort() == Server.socketList.get(0).getPort()){
                /*
                while (true) {
                    if (Server.socketList.size()>1){
                        String message = input.readLine();
                        System.out.println(message);
                        for (Socket client : Server.socketList){
                            if(!(client.getPort() == socket.getPort())){
                                PrintWriter temp = new PrintWriter(client.getOutputStream());
                                temp.println(message);
                                temp.flush();
                            }
                        }
                        break;
                    }
                }

                 */
            }
            else {
                int size = Server.socketList.size();
                BufferedReader inputt = new BufferedReader(new InputStreamReader(Server.socketList.get(size-2).getInputStream()));
                String message = inputt.readLine();
                System.out.println(message);
                output.println(message);
                output.flush();
                socket.close();
                Server.socketList.get(size-2).close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
