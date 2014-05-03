import java.io.BufferedReader;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;



//this class basically handles all the connection which contains the requests
	public class ConnectionHandler extends Thread {
		private final static Logger logger = Logger.getLogger(Main.class.getName());		
		Socket s;
		
		//for sending the output to client
		DataOutputStream pw;
		
		//for getting the input from client
		BufferedReader br;
		
		//constructor
		//which accepts a socket
		public ConnectionHandler(Socket s) throws Exception{
		
			this.s = s;
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new DataOutputStream(s.getOutputStream());
		}
		
		//thread class contains a method run which is called automatically when we start the 
		//thread
		//in this method we have to read the request and give the response
		
		@Override
		public void run() {
			try {
			//here we get the request string and give this string to
			//HttpRequest class
			
			String reqS = "";
			
			//from br we have to read our request
				while(br.ready() || reqS.length() == 0){
					 
					reqS += (char) br.read();
				} //check this afterwards
			
			System.out.println(reqS); //for display
			
			HTTPRequest req = new HTTPRequest(reqS);
			
			//now we pass the httpReq object to httpresponse class for getting the response
			
			HTTPResponse res = new HTTPResponse(req);
			
			//write the final output to pw
			pw.writeBytes(res.response);
			logger.info("Response sent to user.");
			
			pw.close();
			br.close();
			s.close();
			logger.info("Socketed is being closed.");
			} catch (Exception e) {
				logger.warning("Error during handling request from user.");
				e.printStackTrace();
			}
			
		
		}

}
