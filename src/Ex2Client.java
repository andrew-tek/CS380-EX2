//Andrew Tek
//CS380 Excercise 2

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.CRC32;


public class Ex2Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		 try (Socket socket = new Socket("codebank.xyz", 38102)) {
			 	System.out.println("Connecting to server...");
			 	int value1, value2;
			 	InputStream is = socket.getInputStream();
			 	String message = "";
			 	 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	        	BufferedReader br = new BufferedReader(isr);
			 	System.out.println("Bytes Received:");
	            for (int i = 0; i < 50; i++) {
		            value1 = isr.read();
		            value2 = isr.read();
		            String hex1 = Integer.toHexString(value1);
		            String hex2 = Integer.toHexString(value2);
		            message = hex1.concat(hex2.concat(message));
		            System.out.print(hex1 + hex2);
		            if (i % 10 == 0 & i != 0)
		            	System.out.println();
		          
	            }
	            System.out.println();
	            byte messageBytes[] = hexStringToByteArray (message);
	            CRC32 crcTest = new CRC32();
	            crcTest.update(messageBytes);
	            int data = (int) crcTest.getValue();
	            message = Long.toHexString(crcTest.getValue());
	            message = message.toUpperCase();
	            System.out.println("CRC Code:" + message);
	            byte test [] = hexStringToByteArray(message);
	            int value = Integer.parseInt(message.substring(0, 1), 16); 
	            for (int i = 0; i < 8; i+=2) {
	            	value = Integer.parseInt(message.substring(i, i + 1), 16); 
	            	value2 = Integer.parseInt(message.substring(i+1, i + 2), 16);
	            	out.print(value);
	            	out.println(value2);
	            }
	            String messages = br.readLine();
	            value1 = socket.getInputStream().read();
	            if (value1 == 1) 
	            	System.out.println("Correct CRC Value. Disconnecting.");
	            
	            else
	            	System.out.println("Incorrect CRC Value. Disconnecting.");
        	}
		 }
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}


	}


