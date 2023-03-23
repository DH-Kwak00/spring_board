package com.project.board.author.service;

import com.project.board.author.domain.Author;
import com.project.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService implements UserDetailsService {

//    securityconfig에서 만들어놓은 스프링 빈을 여기에 주입 시키는 것.
    private final PasswordEncoder passwordEncoder;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(PasswordEncoder passwordEncoder, AuthorRepository authorRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authorRepository = authorRepository;
    }

    public List<Author>findAll(){
        return authorRepository.findAll();
    }

    public void save(Author author){
        try {
            authorRepository.save(author);
//        save하고 밑에 encode하면 뭐하나? : 이 시점에서 save를 해도 완료되는게 아닌, 모든 메서드가 완료되야 commit된다.
//        author.password = encode(author.password)
            author.encodePassword(passwordEncoder);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Optional<Author> findById(Long id){
//        orElse null 로 하게 되면 controller >> 화면까지 null 리턴되어 에러 발생
//        orElseTHrow를 하면 service단에서 exception이 발생하여 여기서 stop

//        1단계 개선 ; spring Error 로그를 제대로 찍어보자
//        try {
//            return authorRepository.findById(id).orElseThrow(Exception::new);
//
//        }catch (Exception e){
//            throw new Exception("not found Exception");
//        }
        return authorRepository.findById(id);
    }

    public Author findByEmail(String email) {
        return authorRepository.findByEmail(email).orElse(null);
//        옵셔널로 묶어 주면 .orElse(null) 써야함
    }
    public List<Author>findAllFetchJoin(){
        return authorRepository.findAllFetchJoin();
    }


    //    doLogin이 loadByUsername 이라는 메서드를 찾는 걸로 되어있다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("가입되지 않은 Email입니다."));
//        doLogin을 할때 스프링이 사용자가 입력한 email을 DB에서 조회해 가져온 author와
//        사용자가 입력한 password를 비교할 수 있도록 author.getEmail(), author.getPassword()를 return한 것.
        return new User(author.getEmail(), author.getPassword(), Collections.emptyList());
    }



}
