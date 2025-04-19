package ge.tsu.faceburger.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostForm {
    @NotBlank(message = "Author must not be blank")
    @Size(max = 25, message = "Author size must not exceed {max}")
    private String author;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 50, message = "Title size must not exceed {max}")
    private String title;

    @NotBlank(message = "Text must not be blank")
    private String text;

    private List<MultipartFile> images;
}
