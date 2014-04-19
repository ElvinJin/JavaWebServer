import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	
	ServerSocket serverSocket;
	
	//entry point
	public static void main(String[] args) throws Exception {
		
		new Main().runServer(); //to avoid any problem with static fields
		
	}
	
	public void runServer() throws Exception{
		System.out.println("Server is started...");
		serverSocket = new ServerSocket(9876);
		
		//for accepting requests
		acceptRequests();
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
