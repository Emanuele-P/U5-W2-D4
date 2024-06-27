package ep2024.u5w2d4.payloads;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public record NewPostDTO(
    @NotBlank(message = "Genre must not be empty!")
    @Size(min = 3, max = 50, message = "Genre must be between 3 and 50 characters.")
    String genre,
    @NotBlank(message = "Title must not be empty!")
    @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters.")
    String title,
    @URL(message = "Cover must be a valid URL address!")
    String cover,
    @NotBlank(message = "Content must not be empty!")
    @Size(min = 50, message = "Content must be at least 50 characters long.")
    String content,
    @NotNull(message = "Reading time must not be empty!")
    @Min(value = 1, message = "Reading time must be at least 1 minute.")
    int readingTime,

    @NotNull(message = "Author ID must not be empty!")
    UUID authorId
) {}
