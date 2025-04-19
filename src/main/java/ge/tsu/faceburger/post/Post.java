package ge.tsu.faceburger.post;

import ge.tsu.faceburger.comment.Comment;
import ge.tsu.faceburger.image.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "POSTS")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AUTHOR", nullable = false, length = 25)
    private String author;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "TEXT", nullable = false)
    private String text;

    @Column(name = "EXCERPT", nullable = false)
    private String excerpt;

    @Column(name = "CREATE_TIME", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        excerpt = StringUtils.abbreviate(text, 100);
    }
}
