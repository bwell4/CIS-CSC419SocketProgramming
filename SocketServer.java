package pairproject;
//system imports
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Implements java Socket server and determines if a given String from 
 * the Socket Client is a palindrome.
 * 
 * @author Todd Povinelli
 * @version	4/20/19
 */
public class SocketServer {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 1221;
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        //creating socket and waiting for client connection
        Socket socket = server.accept();
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);
            
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            boolean p = palindromeChecker(message);
            if (p)
            	oos.writeObject(message + " is a palindrome.");
            else
            	oos.writeObject(message + " is not a palindrome.");
            
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) {
            	//close resources
            	ois.close();
            	oos.close();
            	socket.close();
            	break;
            }
        }

        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
    
    //palindrome method
    private static boolean palindromeChecker(String text) {
    	//declare array lists
    	ArrayList<Character> forward = new ArrayList<Character>();
    	ArrayList<Character> backwards = new ArrayList<Character>();
    	
    	//place only letters and characters into array lists
    	for (int i = 0; i < text.length(); i++) {
    		if (Character.isLetterOrDigit(text.charAt(i))) {
    			forward.add(Character.toUpperCase(text.charAt(i)));
    			backwards.add(0, Character.toUpperCase(text.charAt(i)));
    		}
    	}
    	
    	return forward.equals(backwards);
    }
}