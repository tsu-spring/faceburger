package ge.tsu.faceburger.image;

import ge.tsu.faceburger.comment.Comment;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class ImageDTO {
    private Long id;
    private String path;
    private LocalDateTime createTime;

    public static ImageDTO fromImage(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        BeanUtils.copyProperties(image, imageDTO);
        return imageDTO;
    }
}
