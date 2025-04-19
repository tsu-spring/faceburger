package ge.tsu.faceburger.comment;

import ge.tsu.faceburger.post.Post;
import ge.tsu.faceburger.post.PostDTO;
import ge.tsu.faceburger.post.PostRepository;
import ge.tsu.faceburger.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    public CommentDTO saveComment(String author, String text, Long postId) {
        final Post post = postService.findById(postId);

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setText(text);
        comment.setPost(post);
        comment = commentRepository.save(comment);
        return CommentDTO.fromComment(comment);
    }
}
