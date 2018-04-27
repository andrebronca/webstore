package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Address;
import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

	private List<Customer> listOfCustomers = new ArrayList<Customer>();
	
	public InMemoryCustomerRepository() {
		Customer c1 = new Customer("1","Seu Madruga");
		Address a1 = new Address("10", "vila do chaves", "vila", null, "Chile", null);
		c1.setBillingAddress( a1 );
		
		
		Customer c2 = new Customer("2", "Buzz Lightyear");
		Address a2 = new Address("113", "casa do Andy", null, null, "EUA", null);
		c2.setBillingAddress(a2);
		
		Customer c3 = new Customer("3", "Andre");
		Address a3 = new Address("666", "Av. Inglaterra", "Europa", null, "Brasil", null);
		c3.setBillingAddress(a3);
		
		listOfCustomers.add(c1);
		listOfCustomers.add(c2);
		listOfCustomers.add(c3);
	}
	
	public List<Customer> getAllCustomers() {
		return listOfCustomers;
	}

}
