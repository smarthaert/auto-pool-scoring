import java.io.*;
import java.net.*;

public class RemoteCommandInterceptor implements Runnable {
	
	private Thread t = null;
	private ScoringWindow window;
		
	public RemoteCommandInterceptor(ScoringWindow handle) {
		t = new Thread(this);
		t.start();
		window = handle;
		System.out.println("instantiated");
	}
	
	public void run() {
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		System.out.println("waiting for remote commands");
		try {
			DatagramSocket serverSocket = new DatagramSocket(9876);
			
			while(true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String sentence = new String( receivePacket.getData());
				for (int i = 0; i < receiveData.length; i++) {
					receiveData[i] = '\0';
				}
				int cmd = Integer.parseInt(sentence.trim());
				System.out.println("RECEIVED: " + cmd);
				
				//while (!window.allowRemoteCommands) {} // spin until remote commands are allowed again
				
				if (cmd >= 1 && cmd <= 15) {
					window.ballSunk(cmd);
				} else if (cmd == 16 || cmd == 17) { // miss
					window.animateTurnSelector();
				}				
			}
		} catch (SocketException e) {
			System.out.println("Could not bind to port 9876");
		} catch (IOException e ) {
			System.out.println("IOException");
		}
    }
}

