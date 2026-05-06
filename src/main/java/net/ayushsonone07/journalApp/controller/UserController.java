package net.ayushsonone07.journalApp.controller;

import net.ayushsonone07.journalApp.dto.ApiResponse;
import net.ayushsonone07.journalApp.dto.UserDTO;
import net.ayushsonone07.journalApp.entity.User;
import net.ayushsonone07.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
