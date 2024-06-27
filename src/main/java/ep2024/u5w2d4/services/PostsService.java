package ep2024.u5w2d4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ep2024.u5w2d4.entities.Author;
import ep2024.u5w2d4.payloads.NewPostDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<BlogPost> getPosts(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return postsRepository.findAll(pageable);
    }

    public BlogPost save(NewPostDTO body) {
        Author author = authorService.findById(body.authorId());

        this.postsRepository.findByTitle(body.title()).ifPresent(
                post -> {
                    throw new BadRequestException("A post entitled " + body.title() + " exists already!");
                }
        );

        BlogPost newPost = new BlogPost(body.genre(), body.title(), body.cover(), body.content(), body.readingTime());
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

    public BlogPost findByIdAndUpdate(UUID postId, NewPostDTO updatedPost) {
        BlogPost found = this.findById(postId);
        found.setGenre(updatedPost.genre());
        found.setTitle(updatedPost.title());
        found.setCover(updatedPost.cover());
        found.setContent(updatedPost.content());
        found.setReadingTime(updatedPost.readingTime());

        if(found.getAuthor().getId() != updatedPost.authorId()){
            Author newAuthor = authorService.findById(updatedPost.authorId());
            found.setAuthor(newAuthor);
        }
        return this.postsRepository.save(found);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    public BlogPost updateCoverURL(UUID postId, String url) {
        BlogPost post = this.findById(postId);
        post.setCover(url);
        return this.postsRepository.save(post);
    }
}
