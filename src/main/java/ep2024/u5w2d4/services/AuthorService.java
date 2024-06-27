package ep2024.u5w2d4.services;

import ep2024.u5w2d4.entities.Author;
import ep2024.u5w2d4.exceptions.BadRequestException;
import ep2024.u5w2d4.exceptions.NotFoundException;
import ep2024.u5w2d4.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorService {
   @Autowired
   private AuthorsRepository authorsRepository;

    public Page<Author> getAuthors(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return authorsRepository.findAll(pageable);
    }

    public Author save(Author newAuthor) {
       this.authorsRepository.findByEmail(newAuthor.getEmail()).ifPresent(
               author -> {
                   throw new BadRequestException("The email: " + newAuthor.getEmail() + " is already in use!");
               }
       );

       newAuthor.setAvatarUrl("https://ui-avatars.com/api/?name=" + newAuthor.getName() + "+" + newAuthor.getSurname());
       return authorsRepository.save(newAuthor);
   }

    public Author findById(UUID authorId) {
        return this.authorsRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public void findByIdAndDelete(UUID authorId) {
        Author found = this.findById(authorId);
        this.authorsRepository.delete(found);
    }

    public Author findByIdAndUpdate(UUID authorId, Author updatedAuthor) {
        Author found = this.findById(authorId);
        found.setName(updatedAuthor.getName());
        found.setSurname(updatedAuthor.getSurname());
        found.setEmail(updatedAuthor.getEmail());
        found.setDayOfBirth(updatedAuthor.getDayOfBirth());
        found.setAvatarUrl("https://ui-avatars.com/api/?name=" + updatedAuthor.getName() + "+" + updatedAuthor.getSurname());
        return this.authorsRepository.save(found);
    }
}
