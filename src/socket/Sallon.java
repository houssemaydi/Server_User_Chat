package socket;

import java.util.Vector;

public class Sallon {
	private String name;
	private Vector<User>  users;
	private Vector<Message> messages;
	public Sallon() {
		super();
		users= new Vector<User>();
		messages= new Vector<Message>();
		// TODO Auto-generated constructor stub
	}
	public Sallon(String name) {
		super();
		this.name = name;
		users= new Vector<User>();
		messages= new Vector<Message>();
	}
	@Override
	public String toString() {
		return "Sallon [name=" + name + ", users=" + users + ", messages=" + messages + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector<User> getUsers() {
		return users;
	}
	public void setUsers(Vector<User> users) {
		this.users = users;
	}
	public Vector<Message> getMessages() {
		return messages;
	}
	public void setMessages(Vector<Message> messages) {
		this.messages = messages;
	}
	 public void addUser(User u) {
		 this.users.add(u);
	 }
	 
	 public void addMessage(Message m) {
		 messages.add(m);
	 }
	
	
	

	

}
