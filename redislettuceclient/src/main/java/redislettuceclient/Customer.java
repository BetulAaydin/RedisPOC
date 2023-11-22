package redislettuceclient;

import java.util.Vector;

public class Customer implements java.io.Serializable {
	private Vector products;
	private Department dept;
	public Vector getProducts() {
		return products;
	}
	public void setProducts(Vector products) {
		this.products = products;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	
}
