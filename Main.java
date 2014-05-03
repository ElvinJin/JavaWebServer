import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
	
	private final static Logger logger = Logger.getLogger(Main.class.getName());
	private static FileHandler fh = null;
	
	ServerSocket serverSocket;
	
	public static void init(){
		 try {
			 fh = new FileHandler("logger.log", false);
		 } catch (SecurityException | IOException e) {
			 e.printStackTrace();
		 }
		 Logger l = Logger.getLogger("");
		 fh.setFormatter(new SimpleFormatter());
		 l.addHandler(fh);
		 l.setLevel(Level.CONFIG);
	}
		 
	//entry point
	public static void main(String[] args) throws Exception {
		Main.init();
		new Main().runServer(); //to avoid any problem with static fields
	}
	
	public void runServer() throws Exception{
		logger.info("Server is started...");
		serverSocket = new ServerSocket(8989);
		
		//for accepting requests
		acceptRequests();
	}
	
	private void acceptRequests() throws Exception{
		
		logger.info("Server is ready to accept request.");
		
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
