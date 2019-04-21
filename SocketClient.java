package pairproject;
//system imports
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Implements java socket client and sends a String to the Socket Server
 * @author Todd Povinelli
 * @version	4/20/19
 *
 */
public class SocketClient {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        //Scanner for reading input
        System.out.println("Submit a String to continue, enter a blank to quit:");
        Scanner input = new Scanner(System.in);
        String palindrome = input.nextLine();
        
        int port = 1221;
        if (args.length == 1) {
        	port = Integer.parseInt(args[0]);
        }
        //establish socket connection to server
        socket = new Socket(host.getHostName(), port);
        
        
        //check for message to exit program
        while (!palindrome.equals("quit")) {
        	
        	System.out.println("Sending request to Socket Server");
        	//write to socket using ObjectOutputStream
        	oos = new ObjectOutputStream(socket.getOutputStream());
        	oos.writeObject(palindrome);
        	
            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println(message);
            
            //read in next palindrome
            System.out.println("Submit a String to continue, enter quit to exit:");
            palindrome = input.nextLine();
            
           
        }
        //write exit message to shut down server
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("exit"); //shut down Socket Server
        ois = new ObjectInputStream(socket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println("Closing the program.");
        input.close(); //close Scanner
        socket.close(); //close Socket
        //close resources
        ois.close();
        oos.close();

        Thread.sleep(100);
    }
}