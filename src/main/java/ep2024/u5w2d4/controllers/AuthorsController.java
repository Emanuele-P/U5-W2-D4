package ep2024.u5w2d4.controllers;

import ep2024.u5w2d4.entities.Author;
import ep2024.u5w2d4.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    //GET all http://localhost:3001/authors
    @GetMapping
    private Page<Author> getAllAuthors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.authorService.getAuthors(page, size, sortBy);
    }

    //POST http://localhost:3001/authors +body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Author save(@RequestBody Author body) {
        return this.authorService.save(body);
    }

    //GET one http://localhost:3001/authors/:id
    @GetMapping("/{id}")
    private Author findAuthorById(@PathVariable UUID id) {
        return this.authorService.findById(id);
    }

    //PUT http://localhost:3001/authors/:id + body
    @PutMapping("/{id}")
    private Author findAuthorByIdAndUpdate(@PathVariable UUID id, @RequestBody Author body) {
        return this.authorService.findByIdAndUpdate(id, body);
    }

    //DELETE http://localhost:3001/authors/:id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findAuthorByIdAndDelete(@PathVariable UUID id) {
        this.authorService.findByIdAndDelete(id);
    }
}
