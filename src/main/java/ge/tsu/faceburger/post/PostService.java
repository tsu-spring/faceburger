package ge.tsu.faceburger.post;

import ge.tsu.faceburger.image.Image;
import ge.tsu.faceburger.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageService imageService;

    public List<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).stream().map(PostDTO::fromPost).toList();
    }

    public PostDTO findPostById(Long postId) {
        Post post = findById(postId);
        return PostDTO.fromPost(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Transactional
    public PostDTO savePost(String author, String title, String text, List<MultipartFile> imageFiles) {
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setText(text);
        post = postRepository.save(post);

        // Physically save image files
        // TODO maybe extract somewhere better
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> images = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                if (imageFile.isEmpty()) {
                    continue;
                }
                String imagePath = imageService.saveImageFile(imageFile);
                Image image = new Image();
                image.setPost(post);
                image.setPath(imagePath);
                images.add(image);
            }
            post.setImages(images);
        }
        post = postRepository.save(post);

        return PostDTO.fromPost(post);
    }

    public long totalNumberOfPosts() {
        return postRepository.count();
    }
}
