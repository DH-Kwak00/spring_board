package com.project.board.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice // 컨트롤러 단위로 특정한 event가 발생했을때 catch를 해주는 역할
public class ExceptionHandlerAdvice {

//    AOP (Ascpect Oriented Programming) 어떤 관점을 기준으로 로직을 모듈화한다는 것
//    예외처리 또는 로그 등 공통화가 필요한 상황을 모듈화 시킨 AOP 프로그램

//    Exception 이라는 이름을 가진 예외가 발생했을떄 catch 해 내는 역할
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> notFound(Exception e){
//        <header> <h1><span>존재하지 않는 화면입니다.</span></h1> </header>
        String context = "<header> <h1><span>존재하지 않는 화면입니다.</span></h1> </header>";
        return new ResponseEntity<String>(context, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> unAuthorized(Exception e){
        String context = "<header> <h1><span>로그인 되지 않았습니다.</span></h1> </header>";
        return new ResponseEntity<String>(context, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> execption(Exception e){
        String context = "<header> <h1><span>에러가 발생 했습니다.</span></h1> </header>";
        return new ResponseEntity<String>(context, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
