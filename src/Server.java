/*
 * A simple TCP select server that accepts multiple connections and echo message back to the clients
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 */

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class Server {
    public static int BUFFERSIZE = 32;
    public static void main(String args[]) throws Exception
    {
        if (args.length != 1)
        {
            System.out.println("Usage: UDPServer <Listening Port>");
            System.exit(1);
        }

        // Initialize buffers and coders for channel receive and send
        String line = "";
        Charset charset = Charset.forName( "us-ascii" );
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer inBuffer = null;
        CharBuffer cBuffer = null;
        int bytesSent, bytesRecv;     // number of bytes sent or received

        // Initialize the selector
        Selector selector = Selector.open();

        // Create a server channel and make it non-blocking
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);

        // Get the port number and bind the socket
        InetSocketAddress isa = new InetSocketAddress(Integer.parseInt(args[0]));
        channel.socket().bind(isa);

        // Register that the server selector is interested in connection requests
        channel.register(selector, SelectionKey.OP_ACCEPT);

        //-------stuff here--------------------------------------------------
        String userName = "";
        String logged_in_users = "";
        File log_file = new File("loggedIn.txt");
        ArrayList<String> loggedInUsersArray = new ArrayList<String>();
        ArrayList<String> bannedUsersArray = new ArrayList<String>();

        /*
         * For logged in users
         */
        FileWriter fw = new FileWriter(log_file,true);
        BufferedWriter bw = new BufferedWriter(fw);

        // Wait for something happen among all registered sockets
        try {
            boolean terminated = false;
            while (!terminated)
            {
                if (selector.select(500) < 0)
                {
                    System.out.println("select() failed");
                    System.exit(1);
                }

                // Get set of ready sockets
                Set readyKeys = selector.selectedKeys();
                Iterator readyItor = readyKeys.iterator();

                // Walk through the ready set
                while (readyItor.hasNext())
                {
                    // Get key from set
                    SelectionKey key = (SelectionKey)readyItor.next();

                    // Remove current entry
                    readyItor.remove();

                    // Accept new connections, if any
                    if (key.isAcceptable())
                    {

                        SocketChannel cchannel = ((ServerSocketChannel)key.channel()).accept();
                        cchannel.configureBlocking(false);
                        System.out.println("Accept conncection from " + cchannel.socket().toString());

                        // Register the new connection for read operation
                        cchannel.register(selector, SelectionKey.OP_READ);
                    }
                    else
                    {
                        SocketChannel cchannel = (SocketChannel)key.channel();
                        if (key.isReadable())
                        {
                            Socket socket = cchannel.socket();

                            // Open input and output streams
                            inBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
                            cBuffer = CharBuffer.allocate(BUFFERSIZE);

                            // Read from socket
                            bytesRecv = cchannel.read(inBuffer);
                            if (bytesRecv <= 0)
                            {
                                System.out.println("read() error, or connection closed");
                                key.cancel();  // deregister the socket
                                continue;
                            }

                            inBuffer.flip();      // make buffer available
                            decoder.decode(inBuffer, cBuffer, false);
                            cBuffer.flip();
                            line = cBuffer.toString();
                            //System.out.print(line);

                            // Echo the message back
                            inBuffer.flip();
                            bytesSent = cchannel.write(inBuffer);
                            if (bytesSent != bytesRecv)
                            {
                                System.out.println("write() error, or connection closed");
                                key.cancel();  // deregister the socket
                                continue;
                            }

                            if (line.equals("terminate\n"))
                                terminated = true;

                            /*command plus the username as one line then just take out the command resulting in
                              just the username
                            */
                            if(line.contains("login")){
                                userName = line.replace("login","");
                                loggedInUsersArray.add(userName + "\n");
                                System.out.println("Logged in: " + userName);
                                bw.write(userName);
                                bw.flush();
                                //bw.close();
                            }
                            /*
                            Get the command from the client then pull out the database of logged in users
                            for the client to see. Add it to the arraylist
                            */
                            if(line.contains("/users")){
                                for (int i = 0; i<loggedInUsersArray.size();i++){
                                    String online_users = loggedInUsersArray.get(i);
                                    System.out.println(online_users);
                                    //loggedUsersArray.remove(i);
                                    //send to client the online user list
                                }
                            }
                            if(line.contains("/ban")){
                                String bannedUser = line.replace("/ban","");
                                bannedUsersArray.add(bannedUser);
                                //send to clients the banned usernames
                            }
                        }
                        //bw.close();
                    }
                    //bw.close();
                } // end of while (readyItor.hasNext())
                //bw.close();
            } // end of while (!terminated)
            //bw.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }


        finally {
            bw.close();
        }

        // close all connections
        Set keys = selector.keys();
        Iterator itr = keys.iterator();
        while (itr.hasNext())
        {
            SelectionKey key = (SelectionKey)itr.next();
            //itr.remove();
            if (key.isAcceptable())
                ((ServerSocketChannel)key.channel()).socket().close();
            else if (key.isValid())
                ((SocketChannel)key.channel()).socket().close();
        }
    }
}
