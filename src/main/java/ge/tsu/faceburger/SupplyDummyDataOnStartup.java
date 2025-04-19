package ge.tsu.faceburger;

import com.github.javafaker.Faker;
import ge.tsu.faceburger.comment.CommentService;
import ge.tsu.faceburger.post.PostDTO;
import ge.tsu.faceburger.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class SupplyDummyDataOnStartup {

    private final PostService postService;
    private final CommentService commentService;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        Faker faker = new Faker();
        SecureRandom random = new SecureRandom();

        if (postService.totalNumberOfPosts() > 0) {
            return;
        }

        for (int i = 0; i < random.nextInt(50, 100); i++) {
            PostDTO postDTO = postService.savePost(
                    faker.name().fullName(),
                    String.join(" ", faker.lorem().words(random.nextInt(3, 7))),
                    String.join(" ", faker.lorem().words(random.nextInt(50, 100))),
                    null
            );

            for (int j = 0; j < random.nextInt(10); j++) {
                commentService.saveComment(
                        faker.name().fullName(),
                        String.join(" ", faker.lorem().words(random.nextInt(5, 15))),
                        postDTO.getId()
                );
            }
        }
    }
}
