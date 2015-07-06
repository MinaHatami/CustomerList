package com.minahatami.customerlogin;

public class Customer {

	private int id;
	private String name, dateOfBirth, address, image;

	public Customer(int id, String name, String dateOfBirth, String address,
			String image) {
		super();

		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public String getImage() {
		return image;
	}

}
