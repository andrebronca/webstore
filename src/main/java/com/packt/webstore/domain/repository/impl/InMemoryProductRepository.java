package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;

@Repository
public class InMemoryProductRepository implements ProductRepository {

	private List<Product> listOfProducts = new ArrayList<Product>();
	
	public InMemoryProductRepository() {
		Product p1 = new Product("1", "iPhone 5s", new BigDecimal(500));
		p1.setDescription("Apple iPhone 5s smartphone with 4.00-inch...");
		p1.setCategory("Smarth Phone");
		p1.setManufacturer("Apple");
		p1.setUnitsInStock(1000);
		
		Product p2 = new Product("2", "Dell Inspirion", new BigDecimal(700));
		p2.setDescription("Dell Inspiron 14-inch laptop (Black)...");
		p2.setCategory("Laptop");
		p2.setManufacturer("Dell");
		p2.setUnitsInStock(950);
		
		Product p3 = new Product("3", "Nexus 7", new BigDecimal(300));
		p3.setDescription("Google Nexus 7 is the lightest 7 inch...");
		p3.setCategory("Tablet");
		p3.setManufacturer("Google");
		p3.setUnitsInStock(800);
		
		listOfProducts.add(p1);
		listOfProducts.add(p2);
		listOfProducts.add(p3);
	}
	
	public List<Product> getAllProducts() {
		return listOfProducts;
	}

}
