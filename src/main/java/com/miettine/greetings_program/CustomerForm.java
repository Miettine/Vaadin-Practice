package com.miettine.greetings_program;

public class CustomerForm {
	private CustomerService service = CustomerService.getInstance();
	private Customer customer;
	private MyUI myUI;

	public CustomerForm(MyUI myUI) {
	    this.myUI = myUI;
	    //status.addItems(CustomerStatus.values());

	}
}
