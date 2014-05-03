import java.nio.charset.Charset;
import java.util.logging.Logger;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class HTTPRequest {
	
	//first line contain 3  parts
	// 1-request type 2-file name 3-http version
	// now we just make use of the file name
	String filename;
	boolean close;
	private final static Logger logger = Logger.getLogger(Main.class.getName());
	
	String username = null;
	String password = null;
	String authorization = null;
	
	//we have to create a constructor that accepts a string
	public HTTPRequest( String request ){
		//now we have the request only 1at line is important to us
		String lines[] = request.split("\n"); // get all the lines of request separately
		
		for (int i = 0; i < lines.length; i++ ) {
			String token[] = lines[i].split(" ");
			if (token[0].equals("GET") || token[0].equals("POST")) {
				filename = token[1];
			} else if (token[0].equals("Connection:")) {
				if (token[1].startsWith("keep")){
					close = false;
				} else {
					close = true;
				}
			} else if (token[0].equals("Authorization:")) {
				authorization = lines[i];
			}
		}
		
		// decode the credential pair
		// Authorization: Basic base64credentials
		if (authorization != null && authorization.startsWith("Authorization")) {
			String base64Credentials = authorization.split(" ")[2];
			String credentials = new String(Base64.decode(base64Credentials), Charset.forName("UTF-8"));
			// credentials = username:password
			final String[] values = credentials.split(":",2);
			username = values[0];
			password = values[1];
			
			System.out.println(username+":"+password);
		}
		logger.info("Successfully parsed the request from user.");
	}
	
}
