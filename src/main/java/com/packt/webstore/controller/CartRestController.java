package com.packt.webstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.CartItem;
import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.CartService;
import com.packt.webstore.service.ProductService;

@Controller
@RequestMapping(value="rest/cart")
public class CartRestController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody 
	public Cart create(@RequestBody Cart cart, @RequestHeader HttpHeaders headers, HttpServletRequest httpRequest) {
		return  cartService.create(cart);
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	@ResponseBody 
	public Cart read(@PathVariable(value = "cartId") String cartId) {
		return cartService.read(cartId);
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable(value = "cartId") String cartId,	@RequestBody Cart cart) {
		cartService.update(cartId, cart);
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "cartId") String cartId) {
		cartService.delete(cartId);
	}
	
	@RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addItem(@PathVariable String productId, HttpServletRequest request) {
		
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId);
		if(cart== null) {
			cart = cartService.create(new Cart(sessionId));
		}
		
		Product product = productService.getProductById(productId);
		if(product == null) {
			throw new IllegalArgumentException(new ProductNotFoundException(productId));
		}
		
		cart.addCartItem(new CartItem(product));
		
		cartService.update(sessionId, cart);
	}
	
	@RequestMapping(value = "/remove/{productId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeItem(@PathVariable String productId, HttpServletRequest request) {
		
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId);
		if(cart== null) {
			cart = cartService.create(new Cart(sessionId));
		}
		
		Product product = productService.getProductById(productId);
		if(product == null) {
			throw new IllegalArgumentException(new ProductNotFoundException(productId));
		}
		
		cart.removeCartItem(new CartItem(product));
		
		cartService.update(sessionId, cart);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST,  reason="Illegal request, please verify your payload")
	public void handleClientErrors(Exception ex) { }

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal server error")
	public void handleServerErrors(Exception ex) {
		
		System.out.println("\nError: \ngetMessage: "+ ex.getMessage() +"\ngetStackTrace: "+ ex.getStackTrace() 
			+"\ngetClass: "+ ex.getClass() 
			+"\ngetLocalizeMessage: "+ ex.getLocalizedMessage() 
			+"\ngetSupressed: "+ ex.getSuppressed().toString());
		
	}	
	/*
	 *	localhost:8888/webstsore/rest/cart				POST	create new cart
	 *	localhost:8888/webstsore/rest/cart/1234			GET		retrieves cart with the ID 1234
	 *	localhost:8888/webstsore/rest/cart/1234			PUT		updates cart with the ID 1234
	 *	localhost:8888/webstsore/rest/cart/1234			DELETE	deletes cart with the id 1234
	 *	localhost:8888/webstsore/rest/cart/add/1234		PUT		adiciona ao carrinho ou incrementa a quantidade
	 *	localhost:8888/webstsore/rest/cart/remove/1234	PUT		remove do carrinho
	 */
	
	/* json test com postman : POST 
	 * 
{
	"cartId" : "1234",
	"cartItems" : {
		"P1234" : {
			"product" : {
				"productId" : "P1234",
				"name" : "iPhone 5s",
				"unitPrice" : 500,
				"description" : "Apple iPhone 5s ...",
				"manufacturer" : "Apple",
				"category" : "Smart Phone",
				"unitsInStock" : 1000,
				"unitsInOrder" : 0,
				"discontinued" : false,
				"condition" : "NEW"
			},
			"quantity" : 1,
			"totalPrice" : 500
		}
	},
	"grandTotal" : 500
}
	 * 
	 */
	
	
}


