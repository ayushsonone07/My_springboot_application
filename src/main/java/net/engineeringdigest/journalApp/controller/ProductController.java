package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.dto.ProductDTO;
import net.engineeringdigest.journalApp.entity.Product;
import net.engineeringdigest.journalApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> create(@RequestBody ProductDTO dto) {
        Product product = service.createProduct(dto);

        return ResponseEntity.ok(Map.of(
        "message", "Product Created Sucessfully",
        "product" , product
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product product = service.updateProduct(id, dto);

        return ResponseEntity.ok(Map.of(
           "message", "Product Updated Sucessfully",
           "product" , product
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteProduct(id);

        return ResponseEntity.ok(Map.of(
        "message" , "Product Deleted Sucessfully!"
        ));
    }
}
