package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		
		Product p2 = new Product("2", "iPhone 6s", new BigDecimal(500));
		p2.setDescription("Apple iPhone 6s smartphone with 5.50-inch...");
		p2.setCategory("Smarth Phone");
		p2.setManufacturer("Apple");
		p2.setUnitsInStock(900);
		
		Product p3 = new Product("3", "Dell Inspirion", new BigDecimal(700));
		p3.setDescription("Dell Inspiron 14-inch laptop (Black)...");
		p3.setCategory("Laptop");
		p3.setManufacturer("Dell");
		p3.setUnitsInStock(950);
		
		Product p4 = new Product("4", "Dell Inspirion Ultra", new BigDecimal(700));
		p4.setDescription("Dell Inspiron 15-inch laptop (Black)...");
		p4.setCategory("Laptop");
		p4.setManufacturer("Dell");
		p4.setUnitsInStock(650);
		
		Product p5 = new Product("5", "Nexus 7", new BigDecimal(300));
		p5.setDescription("Google Nexus 7 is the lightest 7 inch...");
		p5.setCategory("Tablet");
		p5.setManufacturer("Google");
		p5.setUnitsInStock(800);
		
		listOfProducts.add(p1);
		listOfProducts.add(p2);
		listOfProducts.add(p3);
		listOfProducts.add(p4);
		listOfProducts.add(p5);
	}
	
	public List<Product> getAllProducts() {
		return listOfProducts;
	}

	public Product getProductById(String productId) {
		Product productById = null;
		
		for (Product p : listOfProducts) {
			if (p != null && p.getProductId() != null && p.getProductId().equals(productId)) {
				productById = p;
				break;
			}
		}
		
		if (productById == null) {
			throw new IllegalArgumentException("No products found with the product id: "+ productId);
		}
		
		return productById;
	}

	public List<Product> getProductByCategory(String category) {
		List<Product> productsByCategory = new ArrayList<Product>();
		
		for(Product p : listOfProducts) {
			if (category.equalsIgnoreCase(p.getCategory())) {
				productsByCategory.add(p);
			}
		}
		
		return productsByCategory;
	}

	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		Set<Product> produtctsByBrand = new HashSet<Product>();
		Set<Product> productsByCategory = new HashSet<Product>();
		
		Set<String> criterias = filterParams.keySet();
		
		if (criterias.contains("brand")) {
			for (String b: filterParams.get("brand")) {
				for (Product p : listOfProducts) {
					if (b.equalsIgnoreCase(p.getManufacturer())) {
						produtctsByBrand.add(p);
					}
				}
			}
		}
		
		if (criterias.contains("category")) {
			for (String category : filterParams.get("category")) {
				productsByCategory.addAll(this.getProductByCategory(category));
			}
		}
		
		productsByCategory.retainAll(produtctsByBrand);
		
		return productsByCategory;
	}

}
