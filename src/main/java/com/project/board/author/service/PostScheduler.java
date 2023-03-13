package com.project.board.author.service;

import com.project.board.author.repository.PostRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component /*스프링빈(상시적으로 실행되기 위한)으로 등록하는 어노테이션*/
public class PostScheduler {

//    public PostScheduler(PostRepository postRepository){
//        this.postRepository = postRepository;
//    }

    @Scheduled(cron = "0 0/1 * * * *") // 초 분 시 일 월 요일
    public void postSchedule(){
        // 현재시간보다 시이 뒤쳐진 post 건들은 모두 scheduled를 null로 세팅한다.
        // repository.save for 문을 돌려가면서

        // repository.save에서 기존에 데이터가 있으면 update, 없으면 알이서 insert가 된다.

    }
}
