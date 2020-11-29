package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class UserUDP {

	public static void main(String[] args) throws Exception {
		
		DatagramSocket sc = new DatagramSocket();
		InetAddress adr = InetAddress.getLocalHost();
	//	InetAddress adr = InetAddress.getByName("192.168.137.57");

		System.out.println("Login with ##");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String pseudo="";
		
		do {
			pseudo=in.readLine();
		}while(!pseudo.startsWith("##"));
		
		
		DatagramPacket pk = new DatagramPacket(pseudo.getBytes(),pseudo.getBytes().length,adr,5000);
		sc.send(pk);
		
		pseudo = pseudo.substring(2,pseudo.length());
		
		SendUser send = new SendUser(sc, pseudo);
		send.start();
		
		RecUser rec = new RecUser(sc, pseudo);
		rec.start();
	
	}
}
