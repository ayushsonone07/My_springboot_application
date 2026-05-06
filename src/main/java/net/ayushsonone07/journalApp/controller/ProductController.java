package net.ayushsonone07.journalApp.controller;

import net.ayushsonone07.journalApp.dto.ApiResponse;
import net.ayushsonone07.journalApp.dto.ProductDTO;
import net.ayushsonone07.journalApp.entity.Product;
import net.ayushsonone07.journalApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<ProductDTO> getAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {

        return service.getProductById(id);

    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody ProductDTO dto) {

        return ApiResponse.created("Product Created Sucessfully", service.createProduct(dto));

    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody ProductDTO dto) {

        return ApiResponse.success("Product Updated Sucessfully",service.updateProduct(id, dto));

    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {

        service.deleteProduct(id);
        return ApiResponse.success("Product Deleted Sucessfully!");

    }
}
