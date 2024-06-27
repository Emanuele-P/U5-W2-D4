package ep2024.u5w2d4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "blog_posts")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BlogPost {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String genre;
    private String title;
    private String cover;
    private String content;
    private int readingTime;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public BlogPost(String genre, String title, String cover, String content, int readingTime) {
        this.genre = genre;
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.readingTime = readingTime;
    }
}
