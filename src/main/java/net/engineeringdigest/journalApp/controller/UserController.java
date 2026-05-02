package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.dto.ProductDTO;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.entity.Product;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> getAll() { return service.getAllUsers(); }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) { return service.getUserById(id);}

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO dto) {
        User user = service.createUser(dto);

        return ResponseEntity.ok(Map.of(
                "message", "User Created Sucessfully",
                "product" , user
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        User user = service.updateUser(id, dto);

        return ResponseEntity.ok(Map.of(
                "message", "Product Updated Sucessfully",
                "product" , user
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteUser(id);

        return ResponseEntity.ok(Map.of(
                "message" , "Product Deleted Sucessfully!"
        ));
    }
}
