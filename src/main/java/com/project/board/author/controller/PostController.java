package com.project.board.author.controller;

import com.project.board.author.domain.Post;
import com.project.board.author.domain.PostDto;
import com.project.board.author.service.AuthorService;
import com.project.board.author.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final AuthorService authorService;
    @Autowired
    public PostController(PostService postService, AuthorService authorService) {
        this.postService = postService;
        this.authorService = authorService;
    }

    @GetMapping("/post/list")
    public String postList(Model model){
        model.addAttribute("posts", postService.findByScheduled(null));
        return "/post/postList";

    }
    @GetMapping("/post/create")
    public  String postcreate(Model model){
        model.addAttribute("authors", postService.findAll());
        return "/post/postcreate";
    }

//    @GetMapping("/post/create")
//    public  String postcreate(Model model){
//        List<Post> new_list = new ArrayList<>();
//        for (Post : a)
//        model.addAttribute("authors", postService.findAll());
//        return "/post/postcreate";
//    }


    @PostMapping("/post/create")
    public String create(PostDto postDto){
        LocalDateTime dateTime = LocalDateTime.now();

        if (postDto.getScheduled() !=null){
            String str = postDto.getScheduledTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.parse(str, formatter);
            System.out.println(str);
            System.out.println(postDto.getScheduled());
            System.out.println(dateTime);
        }

        Post post =  Post.builder()
                .title(postDto.getTitle())
                .contents(postDto.getContents())
                .author(authorService.findByEmail(postDto.getEmail()))
                .scheduled(postDto.getScheduled())
                .scheduledTime(dateTime)
                .build();
        postService.save(post);
        return "redirect:/post/list";
    }

    @GetMapping("/post/findById")
    public String postDetail(@RequestParam(value="id")Long id, Model model){
        model.addAttribute("post", postService.findById(id));
        return "/post/postDetail";
    }

}
