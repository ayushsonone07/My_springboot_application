package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.dto.ProductDTO;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RestController
@RequestMapping("/users")
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<UserDTO> getAllUsers() {
        try{
            return repo.findAll().stream()
                    .map(u -> {
                        UserDTO dto = new UserDTO();
                        dto.setName(u.getName());
                        dto.setEmail(u.getEmail());
                        return dto;
                    }).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public User getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not Found!"));
    }

    public User createUser(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return repo.save(user);
    }

    public User updateUser(Long id,UserDTO dto) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found!"));

        if(dto.getName() != null){
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
        }

        return repo.save(user);
    }

    public void deleteUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Foumd!"));

        repo.delete(user);
    }
}
