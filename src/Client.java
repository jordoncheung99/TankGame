
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

        //Player aPlayer = new Player();

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
        File file = new File("Usernames.txt");
        Scanner s = new Scanner(file);
        ArrayList<String> userArray = new ArrayList<String>();
        while (s.hasNext()){
            userName = s.next();
            userArray.add(userName);
        }
        s.close();
        //System.out.println(userArray.get(2));
        /*
        for (int i = 0; i<userArray.size();i++){
            System.out.println(userArray.get(i));
        }
        */

        //*------logged-in users----------------------------------
        String logged_in_users = "";
        File log_file = new File("loggedIn.txt");
        ArrayList<String> loggedUsersArray = new ArrayList<String>();

        /*
         * Initialize user input stream
         * For usernames
         */
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        //bw.write("fuk this");
        //bw.close();

        /*
         * For logged in users
         */
        FileWriter fw2 = new FileWriter(log_file,true);
        BufferedWriter bw2 = new BufferedWriter(fw2);


        String line,line2,line3;
        boolean logged_in;
        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
        BufferedReader nameInput =
                new BufferedReader(new InputStreamReader(System.in));
        BufferedReader gameInput =
                new BufferedReader(new InputStreamReader(System.in));

        // Get user input and send to the server
        // Display the echo meesage from the server
        System.out.print("login/register/exit: ");
        line = inFromUser.readLine();
        while (!line.equals("exit"))
        {
            // Send to the server
            //outBuffer.writeBytes(line + '\n');
            //if the input is list then list all the files in current directory
            if (line.equals("login")){
                System.out.println("Enter login Username: ");
                while (!line.equals("logout")){
                    line = nameInput.readLine();
                    boolean isUser = userArray.contains(line);
                    if(isUser){
                        logged_in = true;
                        System.out.println("Welcome:" + line);
                        loggedUsersArray.add(line);
                        bw2.write(line + "\n");
                        bw2.close();
                        while(logged_in){
                            System.out.println("type /gameroom join OR /users Or logout");
                            line3 = gameInput.readLine();
                            if (line3.contains("/gameroom join")){
                                //launch the game here
                                System.out.println("Starting game now");
                            }
                            if (line3.equals("logout")){
                                logged_in = false;
                                line = "logout";
                            }
                            if(line3.equals("/users")){
                                Scanner s2 = new Scanner(log_file);
                                while (s2.hasNext()){
                                    logged_in_users = s2.next();
                                    loggedUsersArray.add(logged_in_users);
                                }
                                s2.close();
                                for (int i = 0; i<loggedUsersArray.size()-1;i++){
                                    String online_users = loggedUsersArray.get(i);
                                    outBuffer.writeBytes(online_users + '\n');
                                    System.out.println(online_users);
                                    //loggedUsersArray.remove(i);
                                }
                            }
                            else{
                                //System.out.println("Unknown command");
                            }
                        }

                        //launch the game here
                    }
                    else{
                        System.out.println("Can't find username: " + line);
                        break;
                    }
                    //take out logged in name here
                }
                //bw2.close();
            }
            else if (line.equals("register")){
                // BufferedWriter bw = new BufferedWriter(fw);
                System.out.println("Enter Username to register: ");
                line = nameInput.readLine();
                System.out.println("New User:" + line);
                userArray.add(line);
                bw.write(line + "\n");
                bw.close();

            }

            else{
                System.out.println("Only type login or register");
            }


            System.out.print("login/register/exit: ");
            line = inFromUser.readLine();

        }
        //bw.close();
        //bw2.close();
        // Close the socket
        clientSocket.close();
    }
}

