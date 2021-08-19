package service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL; 


public class HttpHandler {
    String URI;
    
    public HttpHandler(String input){ 
        URI = "https://financialmodelingprep.com/api/v3/quote/" + input.toUpperCase() + "?apikey=9956bbf6c1fa4fed909282e105b26e92";

    }  

    public String sendHttp( String input) throws MalformedURLException, IOException
    { 
        String result;
        URL conUrl = new URL(URI); //Creates the HTTP requests using the food input
        HttpURLConnection con = (HttpURLConnection) conUrl.openConnection(); //Opens the connection using the generated URL
        con.setRequestMethod("GET"); //Sets the request method for the connection
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //Prepares the input stream reader for the HTTP response 
        StringBuffer content = getResponse(in);
        con.disconnect();    
        return result = toSubString(content.toString());
    } 

    public static StringBuffer getResponse(BufferedReader in) throws IOException{     //Gets an HTTP response and puts it in a StringBuffer object
        String inputLine;        
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) { //loops through the response input stream and adds them to the StringBuffer
            content.append(inputLine);
        }
        in.close(); 
         
        return content;
    } 

    public String toSubString(String input){ 
       String result = input.substring(2, (input.length() - 2 )); 
       return result;
        
    }

}
