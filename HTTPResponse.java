import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;


public class HTTPResponse {
	
	HTTPRequest req;
	private final static Logger logger = Logger.getLogger(Main.class.getName());		
	
	// this is the final response
	String response;
	
	// root path of the server
	String root = "./root";
	
	public HTTPResponse(HTTPRequest request) {
		req = request;
		// now we have to open the file mentioned in request

		try {

			File f = new File(root + req.filename);
			response = "HTTP/1.1 200 \r\n"; // version of http + status code 200 means it's all good
			response += "Server: Emir & Peng's Java Server/1.0 \r\n"; // identity of server
			response += "Content-Type: " + contentType(req.filename) + "\r\n"; // response is in html format
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
			logger.info("Successfully created response for user.");
		} catch (FileNotFoundException e) {
			// if we don't get file then error 404
			response = response.replace("200", "404");
			response += "<!DOCTYPE html><html><head><title>File Not Found</title></head>";
			response += "<body><h1>File Not Found</h1></body></html>";
			logger.warning("Error 404: Can't find the requested file.");
		} catch (Exception e) {
			// if other error the 500 internal server error
			response = response.replace("200", "500");
			logger.warning("Server error 500.");
		}
	}
	
	private static String contentType (String filename) {
		if (filename.endsWith(".htm") || filename.endsWith(".html")) { return "text/html";}
		if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) { return "image/jpeg";}
		if (filename.endsWith(".gif")) { return "image/gif";}
		if (filename.endsWith(".png")) { return "image/png";}
		return "application/octet-stream";
	}
}
