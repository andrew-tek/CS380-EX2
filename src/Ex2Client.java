//Andrew Tek
//CS 380

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
			 	int value1, value2;
			 	InputStream is = socket.getInputStream();
			 	String message = "";
			 	 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 	
	            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	            for (int i = 0; i < 50; i++) {
		            value1 = isr.read();
		            value2 = isr.read();
		            String hex1 = Integer.toHexString(value1);
		            String hex2 = Integer.toHexString(value2);
		            System.out.println(hex1);
		            System.out.println(hex2);
		            message = message.concat(hex1.concat(hex2));
	            }
	            System.out.println(message);
	            byte messageBytes[] = hexStringToByteArray (message);
	            for (int i = 0; i < messageBytes.length; i++) {
	            	System.out.print(messageBytes[i]);
	            }
	            CRC32 crcTest = new CRC32();
	            crcTest.update(messageBytes);
	            System.out.println("\n" + crcTest.getValue());
	            int data = (int) crcTest.getValue();
	            message = Long.toHexString(crcTest.getValue());
	            System.out.println("MESSAGE:" + message);
	            String tmp;
	            
	      
	            
	            for (int i = 0; i < message.length(); i +=2) {
	            	tmp = message.substring(i, i + 2);
	            	out.print(tmp);
	            	System.out.println(tmp);
	            }
	           
	            value1 = isr.read();
	            System.out.println(value1 + "HERE");
	            if (value1 == 1) {
	            	System.out.println("You did it");
	            }
	            else if (value1 == 0) {
	            	System.out.println("You kind of failed this city");
	            }
	            else
	            	System.out.println("You failed this city...");
	            
	            
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
	static List<Integer> digits(int i) {
	    List<Integer> digits = new ArrayList<Integer>();
	    while(i > 0) {
	        digits.add(i % 10);
	        i /= 10;
	    }
	    return digits;
	}

	}


