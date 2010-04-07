package wizard;

import java.io.*;
import java.net.*;

class Send
{
   public static void main(String args[]) throws Exception
   {
       BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

       while(true) {
	   DatagramSocket clientSocket = new DatagramSocket();
	   //InetAddress IPAddress = InetAddress.getByName("localhost");

	   //byte[] inetAddr = { 128, 61, 24, 154 };
	   //InetAddress IPAddress = InetAddress.getByName(args[0]);
	   InetAddress IPAddress = InetAddress.getByName("127.0.0.1");

	   byte[] sendData = new byte[1024];
	   byte[] receiveData = new byte[1024];
	   System.out.print("Enter Command: ");
	   String sentence = inFromUser.readLine();
	   if (sentence.equalsIgnoreCase("miss")) {
	      sentence = "16";
	   } else if (sentence.equalsIgnoreCase("fault")) {
	       sentence = "17";
	   }
	   sendData = sentence.getBytes();
	   DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
	   clientSocket.send(sendPacket);
	   clientSocket.close();
       }
   }
}
