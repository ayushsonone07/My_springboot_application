package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.dto.ProductDTO;
import net.engineeringdigest.journalApp.entity.Product;
import net.engineeringdigest.journalApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<ProductDTO> getAllProducts() {
        return repo.findAll().stream()
                .map(p -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setName(p.getName());
                    dto.setPrice(p.getPrice());
                    return dto;
                }).collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product Not Found!"
                ));
    }

    public Product createProduct(ProductDTO dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        return repo.save(p);
    }

    public Product updateProduct(Long id,ProductDTO dto) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        if(dto.getName() != null){
            product.setName(dto.getName());
            product.setPrice(dto.getPrice());
        }

        return repo.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Foumd!"));

        repo.delete(product);
    }

}
