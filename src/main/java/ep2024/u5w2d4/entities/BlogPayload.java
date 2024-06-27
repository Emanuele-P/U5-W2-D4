package ep2024.u5w2d4.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BlogPayload {
    private String genre;
    private String title;
    private String cover;
    private String content;
    private int readingTime;
    private UUID authorId;
}
