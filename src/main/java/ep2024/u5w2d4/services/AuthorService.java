package ep2024.u5w2d4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ep2024.u5w2d4.entities.Author;
import ep2024.u5w2d4.entities.BlogPost;
import ep2024.u5w2d4.exceptions.BadRequestException;
import ep2024.u5w2d4.exceptions.NotFoundException;
import ep2024.u5w2d4.payloads.NewAuthorDTO;
import ep2024.u5w2d4.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AuthorService {
   @Autowired
   private AuthorsRepository authorsRepository;
   @Autowired
   private Cloudinary cloudinaryUploader;

    public Page<Author> getAuthors(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return authorsRepository.findAll(pageable);
    }

    public Author save(NewAuthorDTO body) {
       this.authorsRepository.findByEmail(body.email()).ifPresent(
               author -> {
                   throw new BadRequestException("The email: " + body.email() + " is already in use!");
               }
       );

       Author newAuthor = new Author(body.name(), body.surname(), body.email(), body.dayOfBirth(), body.avatarUrl());
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

    public Author findByIdAndUpdate(UUID authorId, NewAuthorDTO updatedAuthor) {
        Author found = this.findById(authorId);
        found.setName(updatedAuthor.name());
        found.setSurname(updatedAuthor.surname());
        found.setEmail(updatedAuthor.email());
        found.setDayOfBirth(updatedAuthor.dayOfBirth());
        found.setAvatarUrl(updatedAuthor.avatarUrl());
        return this.authorsRepository.save(found);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    public Author updateAvatarURL(UUID authorId, String url) {
        Author author = this.findById(authorId);
        author.setAvatarUrl(url);
        return authorsRepository.save(author);
    }
}
