package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

public class Server {

	static Vector<User> list;
	static Vector<Message> history;
	static Vector<Sallon> sallon;

	public static void main(String[] args) throws Exception {
		list = new Vector<User>();

		history = new Vector<Message>();

		sallon = new Vector<Sallon>();

		System.out.println("Server is ON");

		DatagramSocket sc = new DatagramSocket(5000);

		while (true) {

			try {
				DatagramPacket pk = new DatagramPacket(new byte[1024], 1024);

				sc.receive(pk);
				InetAddress adr = pk.getAddress();
				int port = pk.getPort();
				String msg = new String(pk.getData(), 0, pk.getLength());

				if (msg.startsWith("##")) {
					String pseudo = msg.substring(2, msg.length());

					boolean test = false;
					for (User u : Server.list) {
						if (u.getPseudo().equals(pseudo)) {
							test = true;

						}
					}
					if (test == true) {
						String msg2 = "User Exist";
						DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
						sc.send(pk2);
					} else {
						User u = new User(pseudo, adr, port, "Online");
						String cc = "welcome " + pseudo;
						DatagramPacket pk2 = new DatagramPacket(cc.getBytes(), cc.getBytes().length, adr, port);
						sc.send(pk2);
						Server.list.add(u);
					}

				} else if (msg.equals("#LIST")) {
					Vector<User> lu = Server.list;
					String msg2 = "";
					for (User u : lu) {
						msg2 = u.getPseudo() + " is " + u.getStatus() + " ";
					}
					DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
					sc.send(pk2);

				} else if (msg.startsWith("#STATUS#")) {

					String status = msg.substring(8, msg.length());
					for (User u : Server.list) {
						if (u.getPort() == port) {
							u.setStatus(status);
							System.out.println(u);
						}
					}

				}

				else if (msg.startsWith("#*")) {

					String newPseudo = msg.substring(2, msg.length());
					for (User u : Server.list) {
						if (u.getPort() == port) {
							u.setPseudo(newPseudo);
							System.out.println(u);
						}
					}
				} else if (msg.startsWith("@#")) {
					User send = new User();
					User recive = new User();
					String[] tab = msg.split("@#");
					Message m = new Message();
					for (User u : Server.list) {
						if (u.getPort() == port) {
							send.setAdress(u.getAdress());
							send.setPort(u.getPort());
							send.setPseudo(u.getPseudo());
							send.setStatus(u.getStatus());
							m.setUfrom(send);

						}

					}
					for (User u : Server.list) {
						if (u.getPseudo().equals(tab[1])) {
							recive.setAdress(u.getAdress());
							recive.setPort(u.getPort());
							recive.setPseudo(u.getPseudo());
							recive.setStatus(u.getStatus());
							m.setUto(recive);

						}

					}

					m.setBody(tab[2]);
					String mesg = "new message from " + m.getUfrom().getPseudo() + " : " + m.getBody();
					DatagramPacket pk2 = new DatagramPacket(mesg.getBytes(), mesg.getBytes().length, recive.getAdress(),
							recive.getPort());
					sc.send(pk2);

					Server.history.add(m);

				} else if (msg.equals("#HISTO")) {
					String msg2="HISTORY: ";
					for (Message m:Server.history)
					{
						msg2=msg2+" "+m;
					}
					DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
					sc.send(pk2);
					
				} else if (msg.equals("#SALONS")) {
					String msg2="ALL SALONS : ";
					for (Sallon s:Server.sallon)
					{
						msg2=msg2+" "+s;
					}
					DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
					sc.send(pk2);
				} else if (msg.startsWith("#SALON#")) {
					String name = msg.substring(7, msg.length());
					Sallon sallon = new Sallon(name);
					Server.sallon.add(sallon);
					String msg2="SALLON: "+name+" IS ADDED";
					DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
					sc.send(pk2);
				} else if (msg.startsWith("#>")) {
					String namesal = msg.substring(2, msg.length());
					for (Sallon s : Server.sallon) {
						if (s.getName().equals(namesal)) {
							for (User u : Server.list) {
								if (u.getPort() == port) {
									s.addUser(u);
								}
							}
						}
					}
					String msg2="YOU HAVE JOINED: "+namesal;

					DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
					sc.send(pk2);
				} else if (msg.startsWith("#USERS#")) {
					String namesal = msg.substring(7, msg.length());
					for (Sallon s : Server.sallon) {
						if (s.getName().equals(namesal)) {
							String msg2 = "";
							for (User u : s.getUsers()) {
								msg2 = u.getPseudo() + " is " + u.getStatus() + " ";
							}
							DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
							sc.send(pk2);

						}
					}
				} else if (msg.startsWith("@>")) {
					String[] tab = msg.split("@>");
					for (Sallon s : Server.sallon) {
						if (s.getName().equals(tab[1])) {
							String msg2 = tab[2];
							Message m = new Message();
							for (User u : s.getUsers()) {
								if (u.getPort() == port) {
									m.setUfrom(u);
								}
							}
							m.setBody(msg2);
							for (User us : s.getUsers()) {
								m.setUto(us);
								String msgg = m.getUfrom() + " " + m.getBody();
								DatagramPacket pk2 = new DatagramPacket(msgg.getBytes(), msgg.getBytes().length, us.getAdress(),
								us.getPort());
								sc.send(pk2);
							}
							s.addMessage(m);
						}
					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
