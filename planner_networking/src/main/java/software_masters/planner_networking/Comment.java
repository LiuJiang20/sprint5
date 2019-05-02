package software_masters.planner_networking;

import java.io.Serializable;

public class Comment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1818731548914457115L;
	private String user;
	private String comment;

	public Comment() {

	}
	public Comment(String user, String comment) {
		this.user = user;
		this.comment = comment;
	}

	/**
	 * @return the user
	 */
	public String getUser() { return user; }

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) { this.user = user; }

	/**
	 * @return the comment
	 */
	public String getComment() { return comment; }

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) { this.comment = comment; }
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() { return serialVersionUID; }
	@Override
	public String toString() {
		
	return this.user+":\n"+comment; }

}
