package ep2024.u5w2d4.services;

import ep2024.u5w2d4.entities.Author;
import ep2024.u5w2d4.entities.BlogPayload;
import ep2024.u5w2d4.entities.BlogPost;
import ep2024.u5w2d4.exceptions.BadRequestException;
import ep2024.u5w2d4.exceptions.NotFoundException;
import ep2024.u5w2d4.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostsService {
    @Autowired
    PostsRepository postsRepository;
    @Autowired
    AuthorService authorService;

    public Page<BlogPost> getPosts(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return postsRepository.findAll(pageable);
    }

    public BlogPost save(BlogPayload payload) {
        Author author = authorService.findById(payload.getAuthorId());

        this.postsRepository.findByTitle(payload.getTitle()).ifPresent(
                post -> {
                    throw new BadRequestException("A post entitled " + payload.getTitle() + " exists already!");
                }
        );

        BlogPost newPost = new BlogPost(payload.getGenre(), payload.getTitle(), payload.getCover(), payload.getContent(), payload.getReadingTime());
        newPost.setAuthor(author);
        return postsRepository.save(newPost);
    }

    public BlogPost findById(UUID postId) {
        return this.postsRepository.findById(postId).orElseThrow(() -> new NotFoundException(postId));
    }

    public void findByIdAndDelete(UUID postId) {
        BlogPost found = this.findById(postId);
        this.postsRepository.delete(found);
    }

    public BlogPost findByIdAndUpdate(UUID postId, BlogPayload updatedPost) {
        BlogPost found = this.findById(postId);
        found.setGenre(updatedPost.getGenre());
        found.setTitle(updatedPost.getTitle());
        found.setCover(updatedPost.getCover());
        found.setContent(updatedPost.getContent());
        found.setReadingTime(updatedPost.getReadingTime());

        if(found.getAuthor().getId() != updatedPost.getAuthorId()){
            Author newAuthor = authorService.findById(updatedPost.getAuthorId());
            found.setAuthor(newAuthor);
        }
        return this.postsRepository.save(found);
    }
}
