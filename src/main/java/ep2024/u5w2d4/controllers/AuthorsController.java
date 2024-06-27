package ep2024.u5w2d4.controllers;

import ep2024.u5w2d4.entities.Author;
import ep2024.u5w2d4.entities.BlogPost;
import ep2024.u5w2d4.exceptions.BadRequestException;
import ep2024.u5w2d4.payloads.NewAuthorDTO;
import ep2024.u5w2d4.services.AuthorService;
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
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    //GET all http://localhost:3001/authors
    @GetMapping
    public Page<Author> getAllAuthors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.authorService.getAuthors(page, size, sortBy);
    }

    //POST http://localhost:3001/authors +body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author save(@RequestBody @Validated NewAuthorDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return this.authorService.save(body);
    }

    //GET one http://localhost:3001/authors/:id
    @GetMapping("/{id}")
    public Author findAuthorById(@PathVariable UUID id) {
        return this.authorService.findById(id);
    }

    //PUT http://localhost:3001/authors/:id + body
    @PutMapping("/{id}")
    public Author findAuthorByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated NewAuthorDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return this.authorService.findByIdAndUpdate(id, body);
    }

    //DELETE http://localhost:3001/authors/:id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAuthorByIdAndDelete(@PathVariable UUID id) {
        this.authorService.findByIdAndDelete(id);
    }

    //UPLOAD FILE http://localhost:3001/posts/:id/avatar
    @PostMapping("/{id}/avatar")
    public Author uploadAvatar(@PathVariable UUID id, @RequestParam("avatar") MultipartFile image) throws IOException {
        String coverURL = this.authorService.uploadImage(image);
        return this.authorService.updateAvatarURL(id, coverURL);
    }
}
