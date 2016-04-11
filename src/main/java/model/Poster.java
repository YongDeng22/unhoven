package model;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


public class Poster {
	private long userId;
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

//	@Path("{id}")
//	public Poster getUser(@PathParam("id") long id){
//		Poster user = new Poster();
//		user.setUserId(id);
//		return user;
//	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (int) userId;
		return hash;
	}
}
