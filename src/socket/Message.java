package socket;

public class Message {
	private User Ufrom;
	private User Uto;
	private String body;
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(User ufrom, User uto, String body) {
		super();
		Ufrom = ufrom;
		Uto = uto;
		this.body = body;
	}
	@Override
	public String toString() {
		return "Message [Ufrom=" + Ufrom + ", Uto=" + Uto + ", body=" + body + "]";
	}
	public User getUfrom() {
		return Ufrom;
	}
	public void setUfrom(User ufrom) {
		Ufrom = ufrom;
	}
	public User getUto() {
		return Uto;
	}
	public void setUto(User uto) {
		Uto = uto;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	

}
