package redislettuceclient;

import java.io.Serializable;

public class Employee extends Customer{
	private String name;
	private String surname;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	

}
