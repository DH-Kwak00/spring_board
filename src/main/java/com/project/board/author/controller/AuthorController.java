package com.project.board.author.controller;

import com.project.board.author.domain.Author;
import com.project.board.author.domain.AuthorDto;
import com.project.board.author.domain.Post;
import com.project.board.author.service.AuthorService;
import com.project.board.author.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Slf4j /* 로그 찍는 어노테이션*/
@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final PostService postService;

////    logger 생성 방법1
//    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AuthorController(AuthorService authorService, PostService postService) {
        this.authorService = authorService;
        this.postService = postService;
    }

    @GetMapping("/test")
    public void test(){
        System.out.println("test log");
        //        slf4j 라이브러리를 활용한 현업에서 사용하는 logging 복사
//        logger.trace("test trace log");
//        logger.debug("test debug log");
//        logger.info("test info log");
//        logger.error("test error log");

//        어노테이션을 활용하여 사용
        log.trace("test trace log");
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
            log.error("findById error : "+e.getMessage());
            throw new EntityNotFoundException("not found Exception");
        }
//        Author author = authorService.findById(id);
////        Author author = authorService.findAllFetchJoin
//        model.addAttribute("author", authorService.findById(id));
        return "/author/authorDetail";
    }
//          레프트조인 이너조인 개념 다시 공부


    @GetMapping("/author/login")
    public String login(){
        return "/author/loginPage";
    }
}
