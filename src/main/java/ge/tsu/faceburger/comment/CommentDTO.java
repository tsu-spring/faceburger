package ge.tsu.faceburger.comment;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

import static ge.tsu.faceburger.util.TimeFormatter.prettyFormat;

@Data
public class CommentDTO {
    private Long id;
    private String author;
    private String text;
    private LocalDateTime createTime;
    private String prettyCreateTime;

    public static CommentDTO fromComment(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        commentDTO.setPrettyCreateTime(prettyFormat(comment.getCreateTime()));
        return commentDTO;
    }
}
