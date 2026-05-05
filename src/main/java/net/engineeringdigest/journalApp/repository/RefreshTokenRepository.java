package net.engineeringdigest.journalApp.repository;

import jakarta.transaction.Transactional;
import net.engineeringdigest.journalApp.entity.RefreshToken;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying //requierd for delete
    @Transactional //requierd for delete
    void deleteByUser(User user);
}
