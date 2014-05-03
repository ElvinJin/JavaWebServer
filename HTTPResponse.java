import java.io.*;
import java.util.logging.Logger;


public class HTTPResponse {
	
	HTTPRequest req;
	private final static Logger logger = Logger.getLogger(Main.class.getName());		
	
	// this is the final response
	String response;
	
	// root path of the server
	String root = "./root";
	
	public HTTPResponse(HTTPRequest request, DataOutputStream os) throws IOException {
		req = request;
		// now we have to open the file mentioned in request
		
		try {
			System.out.println(root + req.filename);
			
			File f = new File(root + req.filename);
			
			//to read this file
			FileInputStream fis = new FileInputStream(f);
			
			os.writeBytes("HTTP/1.1 200 \r\n"); // version of http + status code 200 means it's all good
			os.writeBytes("Server: Emir & Peng's Java Server/2.0 \r\n"); // identity of server
			os.writeBytes("Content-Type: " + contentType(req.filename) + "\r\n"); // response is in html format
			if (req.close) {
				os.writeBytes("Connection: close");
			} else {
				os.writeBytes("Connection: keep-alive \r\n");
			} 
			os.writeBytes("Content-Length: " + f.length() + " \r\n"); // length of response file
			os.writeBytes("\r\n"); // after blank line we have to append file data
			
			sendBytes(fis, os);

			fis.close();
			
			logger.info("Successfully created response for user.");
		} catch (FileNotFoundException e) {
			// if we don't get file then error 404
			String errHTML = "<!DOCTYPE html><html><head><title>File Not Found</title></head>";
			errHTML += "<body><h1>File Not Found</h1></body></html>";
			
			os.writeBytes("HTTP/1.1 404 \r\n"); // version of http + status code 200 means it's all good
			os.writeBytes("Server: Emir & Peng's Java Server/2.0 \r\n"); // identity of server
			os.writeBytes("Content-Type: text/html \r\n"); // response is in html format
			os.writeBytes("Connection: close");
			os.writeBytes("Content-Length: " + errHTML.length() + " \r\n"); // length of response file
			os.writeBytes("\r\n"); // after blank line we have to append file data
			os.writeBytes(errHTML);
			
			logger.warning("Error 404: Can't find the requested file.");
		} catch (Exception e) {
			// if other error the 500 internal server error
			String errHTML = "<!DOCTYPE html><html><head><title>Interner Server Error</title></head>";
			errHTML += "<body><h1>Interner Server Error</h1></body></html>";
			
			os.writeBytes("HTTP/1.1 500 \r\n"); // version of http + status code 200 means it's all good
			os.writeBytes("Server: Emir & Peng's Java Server/2.0 \r\n"); // identity of server
			os.writeBytes("Content-Type: text/html \r\n"); // response is in html format
			os.writeBytes("Connection: close");
			os.writeBytes("Content-Length: " + errHTML.length() + " \r\n"); // length of response file
			os.writeBytes("\r\n"); // after blank line we have to append file data
			os.writeBytes(errHTML);
			
			logger.warning("Error 500: Interner server error.");
		}
	}
	
	private static String contentType (String filename) {
		if (filename.endsWith(".htm") || filename.endsWith(".html")) { return "text/html";}
		if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) { return "image/jpeg";}
		if (filename.endsWith(".gif")) { return "image/gif";}
		if (filename.endsWith(".png")) { return "image/png";}
		return "application/octet-stream";
	}
	
	//set up input output streams
	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception
	{
	   // Construct a 1K buffer to hold bytes on their way to the socket.
	   byte[] buffer = new byte[1024];
	   int bytes = 0;

	   // Copy requested file into the socket's output stream.
	   while((bytes = fis.read(buffer)) != -1 )// read() returns minus one, indicating that the end of the file
	   {
	      os.write(buffer, 0, bytes);
	   }
	}
}
