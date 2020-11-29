package socket;

import java.net.InetAddress;

public class User {
	private String pseudo;
	private InetAddress adress;
	private int port;
	private String status;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String pseudo, InetAddress adress, int port, String status) {
		super();
		this.pseudo = pseudo;
		this.adress = adress;
		this.port = port;
		this.status = status;
	}
	@Override
	public String toString() {
		return "User [pseudo=" + pseudo + ", adress=" + adress + ", port=" + port + ", status=" + status + "]";
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public InetAddress getAdress() {
		return adress;
	}
	public void setAdress(InetAddress adress) {
		this.adress = adress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	

}
