
//create new isntance for each player
//change the wording
//if certain keywords are entered then do specific things like login and register
//login


import java.io.*;
import java.net.*;
import java.util.*;

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

        //----------Usernames--------------------------------
        String userName = "";
        File file = new File("usernames.txt");
        Scanner s = new Scanner(file);
        ArrayList<String> userArray = new ArrayList<String>();
        while (s.hasNext()){
            userName = s.next();
            userArray.add(userName);
        }
        s.close();
        //System.out.println(userArray.get(2));

        //-----------------------------------------------
        // Initialize user input stream
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        //bw.write("fuk this");
        //bw.close();
        String line;
        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
        BufferedReader nameInput =
                new BufferedReader(new InputStreamReader(System.in));

        // Get user input and send to the server
        // Display the echo meesage from the server
        System.out.print("login/register/exit: ");
        line = inFromUser.readLine();
        while (!line.equals("exit"))
        {
            // Send to the server
            outBuffer.writeBytes(line + '\n');
            //if the input is list then list all the files in current directory
            if (line.equals("login")){
                System.out.println("Enter login Username: ");
                line = nameInput.readLine();
                boolean isUser = userArray.contains(line);
                if(isUser){
                    System.out.println("Welcome:" + line);
                }
                else{
                    System.out.println("Can't find username: " + line);
                }
            }
            else if (line.equals("register")){
                // BufferedWriter bw = new BufferedWriter(fw);
                System.out.println("Enter Username to register: ");
                line = nameInput.readLine();
                System.out.println("New User:" + line);
                userArray.add(line);
                bw.write(line + "\n");
                //bw.close();

            }

            else{
                System.out.println("Only type login or register");
            }


            System.out.print("login/register/exit: ");
            line = inFromUser.readLine();

        }
        bw.close();
        // Close the socket
        clientSocket.close();
    }
}

