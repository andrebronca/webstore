package com.packt.webstore.controller;

import java.io.File;
//import java.sql.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;
import com.packt.webstore.validator.UnitsInStockValidator;

@RequestMapping("/products")
@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductValidator productValidator;

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
	public String getProductsByCategory(Model model, 
			@PathVariable("category") String productCategory) {
		List<Product> products = productService.getProductsByCategory(productCategory);
		
		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}
		
		model.addAttribute("products", products);
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
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct, 
			BindingResult result, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			return "addProduct";
		}
		
		String[] supressedFields = result.getSuppressedFields();	//fields in: initialiseBinder()
		if (supressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: "
					+ StringUtils.arrayToCommaDelimitedString(supressedFields));
		}
		
		MultipartFile productImage = newProduct.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		
		if (productImage != null && !productImage.isEmpty()) {
			/*
			 * pathImagem: /Users/andrerbronca/SpringToolSuite_Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps
			 * /webstore/resources\images\8.png
			 * 
			 * pathImagem: /Users/andrerbronca/SpringToolSuite_Workspace/.metadata/
			 * .plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps
			 * /webstore/resources/images/7.png

			 */
			
			String sep = File.separator;
			String name_image = Product.PREFIX_IMAGE_NAME + newProduct.getProductId() +".png";
			String pathOfImage = rootDirectory + sep +"resources"+ sep +"images"+ sep + name_image;
			System.out.println("pathImagem: "+ pathOfImage);
			try {
				productImage.transferTo(new File( pathOfImage ));
			} catch (Exception e) {
				throw new RuntimeException("Product Image saving failed: ", e);
			}
		}
		
		productService.addProduct(newProduct);
		return "redirect:/products";
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
//		DateFormat dateFormat = new SimpleDateFormat("MMM d, YYYY");
//		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
//		binder.registerCustomEditor(Date.class, orderDateEditor);
		//o que não é negado é permitido, deixar as duas como exemplo. Se não der problema.
		binder.setDisallowedFields("unitsInOrder", "discontinued");
		//obs.: language é o parametro da tradução
//		binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", 
//				"category", "unitsInStock", "condition", "productImage","language");
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() +"?"+ req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
	}
	
	@RequestMapping("/invalidPromoCode")
	public String invalidPromoCode() {
		return "invalidPromoCode";
	}
}
