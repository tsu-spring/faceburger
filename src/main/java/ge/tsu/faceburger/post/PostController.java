package ge.tsu.faceburger.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findPostById(id));
        return "post";
    }

    @GetMapping("/post/new")
    public String createNewPost(Model model) {
        if (!model.containsAttribute("postForm")) {
            model.addAttribute("postForm", new PostForm());
        }
        return "post_new";
    }

    @PostMapping("/post/new")
    public String createNewPost(@Valid @ModelAttribute("postForm") PostForm form,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("uploadForm", form);
            return "post_new";
        }

        try {
            PostDTO post = postService.savePost(
                    form.getAuthor(),
                    form.getTitle(),
                    form.getText(),
                    form.getImages()
            );
            String redirectUrl = UriComponentsBuilder.fromPath("/post/{id}")
                    .buildAndExpand(post.getId())
                    .toUriString();
            return String.format("redirect:%s", redirectUrl);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    String.format("An error occurred: %s", e.getMessage())
            );
        }
        return "redirect:/post/new";
    }
}
