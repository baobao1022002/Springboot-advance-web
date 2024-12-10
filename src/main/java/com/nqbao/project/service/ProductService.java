package com.nqbao.project.service;


import com.nqbao.project.model.Product;
import com.nqbao.project.model.ProductDTO;
import com.nqbao.project.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends GenericService<Product, Integer, ProductDTO> {

    public ProductService(ProductRepository productRepository) {

        super(productRepository, "product",
                ProductDTO::new,
                Product::new);
    }

}
