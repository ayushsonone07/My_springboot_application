package net.ayushsonone07.journalApp.repository;

import net.ayushsonone07.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
