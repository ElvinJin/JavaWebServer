import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class HTTPResponse {
	
	HTTPRequest req;
	
	// this is the final response
	String response;
	
	// root path of the server
	String root = "/Users/pjin/Dropbox/Course Files/Sem6/CSE489/Final Project/root";
	
	public HTTPResponse(HTTPRequest request) {
		req = request;
		// now we have to open the file mentioned in request
		File f = new File(root + req.filename);
		
		try {
			
			response = "HTTP/1.1 200 \r\n"; // version of http + status code 200 means it's all good
			
			response += "Server: Emir & Peng's Java Server/1.0 \r\n"; // identity of server
			response += "Content-Type: text/html \r\n"; // response is in html format
			response += "Connection: close \r\n"; // 
			response += "Content-Length: " + f.length() + " \r\n"; // length of response file
			response += "\r\n"; // after blank line we have to append file data
			
			//to read this file
			FileInputStream fis = new FileInputStream(f);
			int s;
			while ((s = fis.read()) != -1) { // -1 means end of file
				response += (char) s;
			}
			fis.close();
		} catch (FileNotFoundException e) {
			// if we don't get file then error 404
			response = response.replace("200", "404");
		} catch (Exception e) {
			// if other error the 500 internal server error
			response = response.replace("200", "500");
		}
	}
	
}
