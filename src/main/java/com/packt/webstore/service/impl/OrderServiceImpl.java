package com.packt.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Order;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.OrderRepository;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.CartService;
import com.packt.webstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartService cartService;

//	public void processOrder(String productId, long quantity) {
//		Product productById = productRepository.getProductById(productId);
//		long qtdInStock = productById.getUnitsInStock();
//		
//		if (qtdInStock < quantity) {
//			throw new IllegalArgumentException("Out of Stock. Available units in stock: "+ qtdInStock);
//		}
//		
//		productById.setUnitsInStock(qtdInStock - quantity); 
//	}

	public Long saveOrder(Order order) {
		Long orderId = orderRepository.saveOrder(order);
		cartService.delete(order.getCart().getCartId());
		return orderId;
	}

}
