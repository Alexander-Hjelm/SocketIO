package SocketIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;

public class Client {

	private static final String IP_ADDR = "127.0.0.1";
	private static final int SERVER_PORT = 8888;
	private static final boolean DEBUG = true;
    
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public boolean startConnection(String ip, int port) {
        try {
			clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
		} catch (Exception e) {
			// UnknownHostException, IOException
			System.out.println("ERROR: Failed to start client socket connection.");
			e.printStackTrace();
			return false;
		}
        return true;
    }
 
    public void sendMessage(Message msg) {
    	String msgStr = "";
    	
    	try {
			msgStr = msg.ToJson().toString();
		} catch (JSONException e) {
			System.out.println("Error: Failed to write json object!");
			e.printStackTrace();
			return;
		}
    			
        if(DEBUG) System.out.println("Printing message to server: " + msgStr);
        out.println(msgStr);
    }
 
    public void stopConnection() {
        try {
			in.close();
		} catch (IOException e) {
			System.out.println("ERROR: Failed to close input stream while terminating client connection.");
			e.printStackTrace();
		}
        out.close();
        try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("ERROR: Failed to close client connection.");
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {

        // Start client
        Client client = new Client();
		if( !client.startConnection(IP_ADDR, SERVER_PORT)) return;
		
	    // Send a message
		client.sendMessage(new Message(Message.Type.TYPE_A, "id", "payload"));
	   
		
    	// Stop client
    	client.stopConnection();
    }
    
}
