
public class HTTPRequest {
	
	//first line contain 3  parts
	// 1-request type 2-file name 3-http version
	// now we just make use of the file name
	String filename;

	//we have to create a constructor that accepts a string
	public HTTPRequest( String request ){
		//now we have the request only 1at line is important to us
		String lines[] = request.split("\n"); // get all the lines of request separatly
		filename = lines[0].split(" ")[1]; //get the filename
	}
	
	
}
