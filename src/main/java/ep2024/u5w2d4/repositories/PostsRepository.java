package ep2024.u5w2d4.repositories;

import ep2024.u5w2d4.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostsRepository extends JpaRepository<BlogPost, UUID> {
    Optional<BlogPost> findByTitle(String title);
}
