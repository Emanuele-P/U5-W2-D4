package ep2024.u5w2d4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "posts")
public class Author {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;
    private String surname;
    private String email;
    @Column(name = "day_of_birth")
    private LocalDate dayOfBirth;
    @Column(name = "avatar_url")
    private String avatarUrl;

    public Author(String name, String surname, String email, LocalDate dayOfBirth, String avatarUrl) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
        this.avatarUrl = avatarUrl;
    }
}
