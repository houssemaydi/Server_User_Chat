package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class RecServer extends Thread {
	

	private DatagramSocket sc ;
	

	public RecServer(DatagramSocket sc) {
		super();
		this.sc = sc;
	}
	
	public void run() {
		while(true) {
			
			try {
				DatagramPacket pk = new DatagramPacket(new byte[1024],1024);
				
				sc.receive(pk);
				InetAddress adr = pk.getAddress();
				int port = pk.getPort();
				String msg = new String(pk.getData(),0,pk.getLength());
				
				if(msg.startsWith("##")) {
					String pseudo= msg.substring(2,msg.length());
					
					
					boolean test = false;
					for(User u:Server.list) {
						if(u.getPseudo().equals(pseudo)) {
							test=true;
							
						}
					}
					if(test==true) {
						String msg2 = "Ce nom Existe deja..tu dois le changer";
						DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(),msg2.getBytes().length,adr,port);
						sc.send(pk2);
					}else {
						User u = new User(pseudo,adr,port,"Online");
						Server.list.add(u);					}
						
				}
				else if (msg.equals("#LIST")) {
					System.out.println(Server.list);
					
				}
				else if (msg.startsWith("#STATUS#")) {
					
					String status= msg.substring(8,msg.length());
					for(User u:Server.list) {
						if(u.getPort()==port) {
							u.setStatus(status);
							
						}
					}

					
				}
				
				else if (msg.startsWith("#*")) {
					
					String newPseudo= msg.substring(2,msg.length());
					for(User u:Server.list) {
						if(u.getPort()==port) {
							u.setPseudo(newPseudo);
							
						}
					}
				}
				else if (msg.startsWith("@#")) {
					User send = new User();
					User recive = new User();
			        String[] tab = msg.split("@#");
			        Message m = new Message();
			        for(User u:Server.list) {
						if(u.getPort()==port) {
							send.setAdress(u.getAdress());
							send.setPort(u.getPort());
							send.setPseudo(u.getPseudo());
							send.setStatus(u.getStatus());
							m.setUfrom(send);

						}

					}
			        for(User u:Server.list) {
						if(u.getPseudo().equals(tab[0])) {
							recive.setAdress(u.getAdress());
							recive.setPort(u.getPort());
							recive.setPseudo(u.getPseudo());
							recive.setStatus(u.getStatus());
							m.setUto(recive);

						}
						

					}
			        
			        m.setBody(tab[1]);
			        
					DatagramPacket pk2 = new DatagramPacket(tab[1].getBytes(),tab[1].getBytes().length,recive.getAdress(),recive.getPort());
					
					Server.history.add(m);

			        


					
				}
				else if (msg.equals("#HISTO")) {
					System.out.println("ALL HISTORY :"+Server.history);
				}
				else if (msg.equals("#SALONS")) {
					System.out.println("ALL HISTORY :"+Server.sallon);
				}
				else if (msg.startsWith("#SALON#")) {
					String status= msg.substring(7,msg.length());
					Sallon sallon = new Sallon();
					Server.sallon.add(sallon);
				}

				
				
				
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

}
