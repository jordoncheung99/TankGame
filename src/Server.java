/*
 * A simple TCP select server that accepts multiple connections and echo message back to the clients
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 */


//change the accept connection thing from this side
//create a gameroom
//List: currentplayers, gamerooms,


import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class Server {
    String line;
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
        // Make the tcp channel non-blocking
        //changed the original variable to tcp_channel from just channel
        ServerSocketChannel tcp_channel = ServerSocketChannel.open();
        tcp_channel.configureBlocking(false);

        // Get the port number and bind the socket
        InetSocketAddress isa = new InetSocketAddress(Integer.parseInt(args[0]));
        tcp_channel.socket().bind(isa);

        // Register that the server selector is interested in connection requests
        tcp_channel.register(selector, SelectionKey.OP_ACCEPT);

        //UDP channel initialization - do it the same as above
        // Declare a UDP server socket and a datagram packet
        DatagramChannel udp_channel = DatagramChannel.open();
        udp_channel.configureBlocking(false);
        udp_channel.socket().bind(isa);
        udp_channel.register(selector, SelectionKey.OP_READ);	//accept wont work..read only

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
                        //check if UDP first - initialize select channel
                        SelectableChannel schannel = key.channel();
                        //if this channel belongs to UDP channel
                        if (schannel instanceof DatagramChannel)
                        {
                            //do it same way as TCP
                            DatagramChannel dchannel = (DatagramChannel)schannel;
                            if (key.isReadable())
                            {

                                // Open input and output streams
                                inBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
                                cBuffer = CharBuffer.allocate(BUFFERSIZE);

                                // Read from socket
                                //take address of UDP socket and check if its null
                                SocketAddress data_addr = dchannel.receive(inBuffer);
                                if (data_addr == null)
                                {
                                    System.out.println("read() error, or connection closed");
                                    key.cancel();  // deregister the socket
                                    continue;
                                }

                                inBuffer.flip();      // make buffer available
                                decoder.decode(inBuffer, cBuffer, false);
                                cBuffer.flip();
                                line = cBuffer.toString();
                                System.out.print("UDP Client: " + line + "\n");

                                // Echo the message back
                                inBuffer.flip();
                                bytesRecv = line.length();
                                bytesSent = dchannel.send(inBuffer,data_addr); //send the message - not write
                                if (bytesSent != bytesRecv)
                                {
                                    System.out.println("write() error, or connection closed");
                                    key.cancel();  // deregister the socket
                                    continue;
                                }

                                if (line.equals("terminate") || line.equals("logout")){
                                    terminated = true;
                                }
                            }

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
                                System.out.print("TCP Client: " + line);

                                // Echo the message back
                                inBuffer.flip();
                                bytesSent = cchannel.write(inBuffer);
                                if (bytesSent != bytesRecv)
                                {
                                    System.out.println("write() error, or connection closed");
                                    key.cancel();  // deregister the socket
                                    continue;
                                }

                                if (line.equals("terminate") || line.equals("logout"))
                                {
                                    terminated = true;
                                }
                            }
                        }
                    }
                } // end of while (readyItor.hasNext())
            } // end of while (!terminated)
        }
        catch (IOException e) {
            System.out.println(e);
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
