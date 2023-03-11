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
        model.addAttribute("posts", postService.findAll());
        return "/post/postList";

    }
    @GetMapping("/post/create")
    public  String postcreate(Model model){
        model.addAttribute("authors", postService.findAll());
        return "/post/postcreate";
    }
    @PostMapping("/post/create")
    public String create(PostDto postDto){
        Post post =  Post.builder()
                .title(postDto.getTitle())
                .contents(postDto.getContents())
                .author(authorService.findByEmail(postDto.getEmail()))
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
