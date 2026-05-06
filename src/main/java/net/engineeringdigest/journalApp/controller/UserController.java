package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.dto.ApiResponse;
import net.engineeringdigest.journalApp.dto.AuthResponse;
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
    public ApiResponse<?> create(@RequestBody UserDTO dto) {

        return ApiResponse.created("User Created Sucessfully", service.createUser(dto));

    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody UserDTO dto) {

        return ApiResponse.success("User Updated Sucessfully", service.updateUser(id, dto));

    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {

        service.deleteUser(id);
        return ApiResponse.success("User Deleted Sucessfully!");

    }
}
