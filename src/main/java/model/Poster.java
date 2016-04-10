package model;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("user")
public class Poster {
	private long user_id;
	private String userName;
	
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Path("{id}")
	public Poster getUser(@PathParam("id") long id){
		Poster user = new Poster();
		user.setUser_id(id);
		return user;
	}
}
