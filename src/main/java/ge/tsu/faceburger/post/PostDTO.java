package ge.tsu.faceburger.post;

import ge.tsu.faceburger.comment.CommentDTO;
import ge.tsu.faceburger.image.ImageDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ge.tsu.faceburger.util.TimeFormatter.prettyFormat;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String author;
    private String text;
    private String excerpt;
    private LocalDateTime createTime;
    private String prettyCreateTime;
    private List<ImageDTO> images;
    private List<CommentDTO> comments;

    public static PostDTO fromPost(Post post) {
        var postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setImages(post.getImages().stream().map(ImageDTO::fromImage).collect(Collectors.toList()));
        postDTO.setComments(post.getComments().stream().map(CommentDTO::fromComment).collect(Collectors.toList()));
        postDTO.setPrettyCreateTime(prettyFormat(post.getCreateTime()));
        return postDTO;
    }
}
