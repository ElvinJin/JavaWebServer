import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
	
	ServerSocket serverSocket;
	
	public void runServer() throws Exception{
		
		serverSocket = new ServerSocket(6543);
		
		//for accepting requests
		acceptRequests();
	}
	
	
	//entry point
	public static void main(String[] args) throws Exception {
		
		new Main().runServer(); //to avoid any problem with static fields
		
	}
	
	private void acceptRequests() throws Exception{
		
		while(true){ //we have to accept all the request
			
			
			//connection to client is in the form of socket which contain the stream for input
			//and output
			Socket s = serverSocket.accept();
			ConnectionHandler ch = new ConnectionHandler(s);
			
			//ch is the thread, so
			ch.start(); // this will call the run method automatically
		}
		
	}

}
