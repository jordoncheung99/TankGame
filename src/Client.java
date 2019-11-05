/*
 * A simple TCP client that sends messages to a server and display the message
   from the server.
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 */

//create new isntance for each player
//change the wording
//if certain keywords are entered then do specific things like login and register
//

import java.io.*;
import java.net.*;

class Client {

    public static void main(String args[]) throws Exception
    {
        if (args.length != 2)
        {
            System.out.println("Usage: TCPClient <Server IP> <Server Port>");
            System.exit(1);
        }

        // Initialize a client socket connection to the server
        Socket clientSocket = new Socket(args[0], Integer.parseInt(args[1]));

        // Initialize input and an output stream for the connection(s)
        DataOutputStream outBuffer =
                new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inBuffer =
                new BufferedReader(new
                        InputStreamReader(clientSocket.getInputStream()));

        // Initialize user input stream
        String line;
        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        // Get user input and send to the server
        // Display the echo meesage from the server
        System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
        line = inFromUser.readLine();
        while (!line.equals("logout"))
        {
            // Send to the server
            outBuffer.writeBytes(line + '\n');

            //if the input is list then list all the files in current directory
            if (line.equals("list"))
            {
                //get the current directory
                File currentDir = new File(".");
                //list of the files in the current directory
                File[] filesList = currentDir.listFiles();
                //get all the files in the current directory and print it
                for(File file : filesList){
                    if(file.isDirectory())
                    {
                        System.out.println(file.getName());
                    }
                    if(file.isFile())
                    {
                        System.out.println(file.getName());
                    }
                }
            }

	   /*
	   if (line.equals("get"){

	   }
	   */

            //if its not list or get then print unknown command
            //currently have a bug where it prints the input before
            else{
                // Getting response from the server
                line = inBuffer.readLine();
                System.out.println("Server: Unknown Command: " + line);
            }


            System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
            line = inFromUser.readLine();
        }

        // Close the socket
        clientSocket.close();
    }
}

