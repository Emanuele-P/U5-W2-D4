package ep2024.u5w2d4.repositories;

import ep2024.u5w2d4.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByEmail(String email);
}
