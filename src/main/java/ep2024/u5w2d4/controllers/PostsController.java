package ep2024.u5w2d4.controllers;

import ep2024.u5w2d4.entities.BlogPayload;
import ep2024.u5w2d4.entities.BlogPost;
import ep2024.u5w2d4.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    //GET all http://localhost:3001/posts
    @GetMapping
    private Page<BlogPost> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.postsService.getPosts(page, size, sortBy);
    }

    //POST http://localhost:3001/posts +body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private BlogPost save(@RequestBody BlogPayload body) {
        return this.postsService.save(body);
    }

    //GET one http://localhost:3001/posts/:id
    @GetMapping("/{id}")
    private BlogPost findPostById(@PathVariable UUID id) {
        return this.postsService.findById(id);
    }

    //PUT http://localhost:3001/posts/:id + body
    @PutMapping("/{id}")
    private BlogPost findPostByIdAndUpdate(@PathVariable UUID id, @RequestBody BlogPayload body) {
        return this.postsService.findByIdAndUpdate(id, body);
    }

    //DELETE http://localhost:3001/posts/:id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findPostByIdAndDelete(@PathVariable UUID id) {
        this.postsService.findByIdAndDelete(id);
    }
}
