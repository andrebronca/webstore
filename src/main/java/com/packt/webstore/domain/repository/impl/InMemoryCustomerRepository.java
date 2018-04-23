package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

	private List<Customer> listOfCustomers = new ArrayList<Customer>();
	
	public InMemoryCustomerRepository() {
		Customer c1 = new Customer("1","Seu Madruga");
		c1.setAddress("Vila do chaves");
		
		Customer c2 = new Customer("2", "Buzz Lightyear");
		c2.setAddress("toy Andy's");
		
		Customer c3 = new Customer("3", "Andre");
		c3.setAddress("Brasil");
		
		listOfCustomers.add(c1);
		listOfCustomers.add(c2);
		listOfCustomers.add(c3);
	}
	
	public List<Customer> getAllCustomers() {
		return listOfCustomers;
	}

}
