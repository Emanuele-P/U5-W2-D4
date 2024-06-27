package ep2024.u5w2d4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record NewAuthorDTO(
        @NotBlank(message = "Name must not be empty!")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
        String name,
        @NotBlank(message = "Surname must not be empty!")
        @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters.")
        String surname,
        @NotBlank(message = "Email must not be empty!")
        @Email(message = "Email must be a valid email address.")
        String email,
        @NotNull(message = "Date of birth must not be empty!")
        LocalDate dayOfBirth,
        @URL(message = "Avatar URL must be a valid URL address!")
        String avatarUrl
        ) {}
