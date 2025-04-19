package ge.tsu.faceburger.home;

import ge.tsu.faceburger.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String index(Pageable pageable, Model model) {
        // Override sorting logic!
        Sort sort = Sort.by(Sort.Order.by("createTime").with(Sort.Direction.DESC));
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        model.addAttribute("posts", postService.getAllPosts(pageable));
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("totalPages", postService.totalNumberOfPosts() / pageable.getPageSize());
        return "index";
    }
}
