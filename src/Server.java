import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server implements Runnable{
    protected ServerSocket serverSocket;
    protected Socket aSocket;
    protected ExecutorService pool;

    public Server(int portNum) {
        try {
            serverSocket = new ServerSocket(portNum);
            pool = Executors.newCachedThreadPool();
        }catch(IOException e) {
            System.err.println(e.getMessage());
            System.err.println("That port number is already in use");
            System.exit(1);
        }
    }

    public void commuicateWtihClient() throws IOException{
        try {
            while(true) {
                pool.execute(new GameLobby(serverSocket.accept()));
                System.out.println("Connected to ShopOwner");
            }
        }catch(Exception e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }

    public void run(){
        try {
            commuicateWtihClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException{
        //MultiTreaded server
        Server server = new Server(8099);
        Thread t = new Thread(server);
        t.start();
        System.out.println("Servers are now running");
    }

}
