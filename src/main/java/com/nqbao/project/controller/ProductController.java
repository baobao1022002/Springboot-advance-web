package com.nqbao.project.controller;

import com.nqbao.project.model.Product;
import com.nqbao.project.model.ProductDTO;
import com.nqbao.project.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController extends GenericController<Product, Integer, ProductDTO> {
    public ProductController(ProductService service) {
        super(service);

    }

}
