package com.project.board.author.service;

import com.project.board.author.domain.Author;
import com.project.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author>findAll(){
        return authorRepository.findAll();
    }

    public void save(Author author){
        authorRepository.save(author);
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
}
