package com.packt.webstore.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.service.ProductService;

@RequestMapping("/products")
@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@RequestMapping
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}
	
	@RequestMapping("/all")
	public ModelAndView allProducts() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("products", productService.getAllProducts());
		modelAndView.setViewName("products");
		return modelAndView;
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		model.addAttribute("products", productService.getProductsByCategory(productCategory));
		return "products";
	}
	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(
			@MatrixVariable(pathVar="ByCriteria") Map<String, List<String>> filterParams, 
			Model model ) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}
	
	//para passar mais parametros para serem recuperados no GET
	//obs. quando o parametro na URI é o mesmo que o nome da variável, não é necessário especificar o nome
	//ex. nomes diferentes: @RequestParam("id") String productID	
	//ex. nomes iguais: @RequestParam String id					- significa que na URI terá o parametro: id
	// webStore/products/product?category=laptop&price=700
	//ex. public String getProducts(@RequestParam String category, @RequestParam String price){}
	
	@RequestMapping("/tablet/{price}")
	public String filterProducts(
			@MatrixVariable(pathVar="price") Map<String, List<String>> filterParams,
			Model model ) {
		model.addAttribute("products", productService.getProductsByPriceFilter(filterParams));
		return "products";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct, 
			BindingResult result) {
		String[] supressedFields = result.getSuppressedFields();	//fields in: initialiseBinder()
		if (supressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: "
					+ StringUtils.arrayToCommaDelimitedString(supressedFields));
		}
		productService.addProduct(newProduct);
		return "redirect:/products";
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
//		DateFormat dateFormat = new SimpleDateFormat("MMM d, YYYY");
//		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
//		binder.registerCustomEditor(Date.class, orderDateEditor);
		binder.setDisallowedFields("unitsInOrder", "discontinued");
	}
}
