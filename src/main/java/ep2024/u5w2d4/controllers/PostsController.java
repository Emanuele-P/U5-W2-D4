package ep2024.u5w2d4.controllers;

import ep2024.u5w2d4.exceptions.BadRequestException;
import ep2024.u5w2d4.payloads.NewPostDTO;
import ep2024.u5w2d4.entities.BlogPost;
import ep2024.u5w2d4.payloads.NewPostResponseDTO;
import ep2024.u5w2d4.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    //GET all http://localhost:3001/posts
    @GetMapping
    public Page<BlogPost> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.postsService.getPosts(page, size, sortBy);
    }

    //POST http://localhost:3001/posts +body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPostResponseDTO save(@RequestBody @Validated NewPostDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewPostResponseDTO(this.postsService.save(body).getId());
    }

    //GET one http://localhost:3001/posts/:id
    @GetMapping("/{id}")
    public BlogPost findPostById(@PathVariable UUID id) {
        return this.postsService.findById(id);
    }

    //PUT http://localhost:3001/posts/:id + body
    @PutMapping("/{id}")
    public BlogPost findPostByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated NewPostDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return this.postsService.findByIdAndUpdate(id, body);
    }

    //DELETE http://localhost:3001/posts/:id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findPostByIdAndDelete(@PathVariable UUID id) {
        this.postsService.findByIdAndDelete(id);
    }

    //UPLOAD FILE http://localhost:3001/posts/:id/cover
    @PostMapping("/{id}/cover")
    public BlogPost uploadCover(@PathVariable UUID id, @RequestParam("cover")MultipartFile image) throws IOException {
        String coverURL = this.postsService.uploadImage(image);
        return this.postsService.updateCoverURL(id, coverURL);
    }
}
