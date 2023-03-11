package com.project.board.author.controller;

import com.project.board.author.domain.Author;
import com.project.board.author.domain.AuthorDto;
import com.project.board.author.domain.Post;
import com.project.board.author.service.AuthorService;
import com.project.board.author.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final PostService postService;

    public AuthorController(AuthorService authorService, PostService postService) {
        this.authorService = authorService;
        this.postService = postService;
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
//        화면을 렌더링하기 전에 Data model에 넣어서
//        model.addAttribute("authors", authorService.findAll());
        List<Author> authors = authorService.findAllFetchJoin();
        model.addAttribute("authors", authorService.findAll());
        return "/author/authorList";
    }

    @GetMapping("/author/createform")
    public String createform(){
        return "/author/authorCreate";
    }

    @PostMapping("/author/create")
    public String create(AuthorDto authorDto){
//       방법1. setter 방식 : 단점은 세터를 사용 여기저기 객체에 변경을 할 수 있어 유지/보수가 힘들다.
//        Author author = new Author();
//        author.setName(authorDto.getName());
//        author.setEmail(authorDto.getEmail());
//        author.setPassword(authorDto.getPassword());
//        author.setRole(authorDto.getRole());
//        author.setCreateDate(LocalDateTime.now());

//        방법2. 생성자를 통한 객체 생성 방식
//        문제점: 가독성이 떨어짐 --> 실수할 여지가 생김, 각 변수 자리에 맞게 세팅해줘야 정확히 세팅 되는 문제
//        Author author = new Author(authorDto.getName(), authorDto.getEmail(), authorDto.getPassword(), authorDto.getRole());

//        방법3. 빌더를 통한 객체 생성 방식 ( 객체들의 순서 상관 X )
        Author author =  Author.builder()
                .name(authorDto.getName())
                .email(authorDto.getEmail())
                .password(authorDto.getPassword())
                .role(authorDto.getRole())
                .build();
        authorService.save(author);
        return "redirect:/";
    }

    @GetMapping("/author/findById")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String authorDetail(@RequestParam(value="id")Long id, Model model) throws Exception {

////        방법1
////        author 객체+ setcount (post에서 찾아온 List의 size)
//        Author author = authorService.findById(id);
//        List<Post> posts = postService.findByAuthor_id(id);
//        author.setCount(posts.size());

//        방법2
//        author객체를 최초 초회 할때부터 post와 join을 걸어서 가져온다.

//        -------------------------------------------------------------------------------------


//        orElse null 로 하게 되면 controller >> 화면까지 null 리턴되어 에러 발생
//        orElseTHrow를 하면 service단에서 exception이 발생하여 여기서 stop

//        1단계 개선 ; spring Error 로그를 제대로 찍어보자

//        2단계 개선 ; AOP를 활용해 공통 모듈에서 예외를 일괄 처리


        try {
            Author author = authorService.findById(id).orElseThrow(EntityNotFoundException::new);
            model.addAttribute("author", author);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("not found Exception");
        }
//        Author author = authorService.findById(id);
////        Author author = authorService.findAllFetchJoin
//        model.addAttribute("author", authorService.findById(id));
        return "/author/authorDetail";
    }
//          레프트조인 이너조인 개념 다시 공부

}
