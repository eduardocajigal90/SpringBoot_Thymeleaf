package com.quiox.test.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.quiox.test.service.ProductService;
import com.quiox.test.projection.ProductRequestDto;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;



@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/index", "/home", "/"}, method = RequestMethod.GET)
    public String report(Model model) {
        return "views/example";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		model.addAttribute("title", "List of products");
		model.addAttribute("products", productService.getAllProducts());
		return "views/list";
	}
	
	@RequestMapping(value = "/form")
	public String create(Map<String, Object> model) {
		ProductRequestDto product = new ProductRequestDto();
		model.put("product", product);
		model.put("title", "Form of Product");
		return "views/form";
	}

    @RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(ProductRequestDto product, BindingResult result, Model model, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Form of Product");
			return "views/form";
		}
		productService.saveProduct(product);
		status.setComplete();
		return "redirect:views/list";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable(value="id") Long id) {
		if(id > 0) {
			productService.deleteProduct(id);
		}
		return "redirect:views/list";
	}
}
