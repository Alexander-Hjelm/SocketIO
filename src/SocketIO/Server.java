package SocketIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {
	
	private static final boolean DEBUG = true;
	private static final int LISTEN_PORT = 8888;
	
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        if(DEBUG) System.out.println("Server started");
        
        clientSocket = serverSocket.accept();
        if(DEBUG) System.out.println("Server: client connected");
        
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        if(DEBUG) System.out.println("Created reader, got Input Stream");
      
		
        // Main listen loop
        while(!clientSocket.isClosed()) {
        	Message msg = null;
        	StringBuilder msgSB = new StringBuilder();
        	
        	// Read message from JSON
	        boolean msgSet = false;
	        while(!msgSet) {
	        	msgSet = true;
	        	try {
	        		String input = in.readLine();
	        		if(input == null || input.equals("")) {
	        			msgSet = false;
	        			continue;
	        		}
	        		msgSB.append(input);
	        		if(DEBUG) System.out.println("SB contents so far: " + msgSB.toString());
	        		msg = Message.FromJson(new JSONObject(msgSB.toString()));
	        	} catch (JSONException e) {
	        		System.out.println("Error: Failed while converting message from json.");
	        		e.printStackTrace();
	        		msgSet = false;
	        	}
	        }
	        
	        if(DEBUG) System.out.println("Read message: type = " + msg.type.name() + ", id = " + msg.id + ", data = " + msg.data);
	        
	        if(msg != null) {
	        	// Control switch
	        	switch(msg.type) {
	        		case TYPE_A:
	    	        	// TYPE_A, message callback
	        			break;
	        			
	        		case TYPE_B:
	    	        	// TYPE_B, message callback
	        			break;
	        			
	        		case TYPE_C:
	    	        	// TYPE_C, message callback
	        			break;
	        	}
	        }
        }
 
    }
 
    public void stop() throws IOException {
        in.close();
        //out.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(LISTEN_PORT);
    }
	
}
