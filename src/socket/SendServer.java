package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;

public class SendServer extends Thread{

	private DatagramSocket dc;
	
	public SendServer(DatagramSocket dc) {
		super();
		this.dc=dc;
	}
	
	public void run() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		
	}
	
	

}
