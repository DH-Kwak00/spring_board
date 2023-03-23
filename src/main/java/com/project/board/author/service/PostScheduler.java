//package com.project.board.author.service;
//
//import com.project.board.author.domain.Post;
//import com.project.board.author.repository.PostRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//@Component /*스프링빈(상시적으로 실행되기 위한)으로 등록하는 어노테이션*/
//@Slf4j
//public class PostScheduler {
//    private final PostRepository postRepository;
//
//    public PostScheduler(PostRepository postRepository){
//        this.postRepository = postRepository;
//    }
//
//    @Scheduled(cron = "0 0/1 * * * *") // 초 분 시 일 월 요일
//    public void postSchedule(){
//
//        //  1. 현재 예약되있는 건들은 스케줄드에 checked이므로 이건들만 조회
//        //  2. 현재 시간보다 시간상 뒤처진 건들은 null로 바꾼다.
//        //     특정건들에 대해서 repository.save for 문을 돌려가면서
//        // 현재시간보다 시이 뒤쳐진 post 건들은 모두 scheduled를 null로 세팅한다.
//        // repository.save for 문을 돌려가면서
//        //  3. repository.save에서 기존에 데이터가 있으면 update, 없으면 알이서 insert가 된다.
//
////        Post post = postRepository.findById(1L).orElse(null);
////        post.setScheduled(null);
////        postRepository.save(post);
////        log.info("hello");
//
//        // *** 다시 풀어보기 ***
//        // 1. 현재 예약 되있는 건들은 scheduled에 checked이므로 이건들만 조회
//        // 2. for문을 돌려가면서
//        // 3. 현재시간보다 schduled_time이 시간상 뒤처진 건들은 null로 바꾼다.
//        // 4. repository.save를 통해 update 한다.
//
//        List<Post> posts = postRepository.findByScheduled("checked");
//        for(Post p : posts){
//            if (p.getScheduledTime().isBefore(LocalDateTime.now())){
//                p.setScheduled(null);
//                postRepository.save(p);
//            }
//        }
//
//
//    }
//}
