package com.project.board;

import com.project.board.author.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity 어노테이션을 통해 Security customizing 기능 활성화
// WebSecurityConfigurerAdapter 라는 클래스를 상속했었으나 스프링 2.7.0 이상에서는 deoprecated 됨
@Configuration
@EnableWebSecurity
public class SecurutyConfig {

//    만드는 법 2가지
//    @Component는 setter나 builder 등을 통해 사용자가 특정값을 변경해서 생성한 인스턴스를 스프링에게 관리 맡기는 것
//    1) 개발자가 직접 컨트롤이 가능한 내부 클래스에서 사용
//    2) class에서만 선언 가능한 어노테이션

//    @Bean은 클래스를 스프링한테 알아서 인스턴스를 생성 후 등록하라고 맡기는 것
//    1) 개발자가 컨트롤이 불가능한 외부 라이브러리 사용시 사용
//    2) 메서드단에도 붙일 수 있고, 이때에 클래스에는 @Configuration을 붙여줘야 한다.

    @Bean
    public PasswordEncoder passwordEncoder(){
//        암호화모듈을 우리 프로젝트의 스프링에 빈으로 주입하는 것
//        스프링 빈 : 싱글톤, 상시적으로 떠있는 객체
//        싱글톤 : 객체를 new하지 않고 모듈로 만들어 사용자가 몇 명이든 상시로 떠 있는 객체. 매번 new 안하는
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity

//                csrf라는 공격에 대한 설정을 현재는 필요 없으니 일단 주석처리
                .csrf().disable()

                .authorizeRequests()
//                특정 url에는 접속 권한을 모두 허용을 한것
                /*예외 url*/
                .antMatchers("/author/login", "/", "/author/createform", "/author/create")
                .permitAll()
//                그외에 나머지 request에 대해서는 인증을 요구하도록 한것
                .anyRequest().authenticated() /*인증*/
                .and()
                .formLogin()
//                login에 대한 page url 지정
                    .loginPage("/author/login")
//                login 화면에서 어떤 post요청을 통해 로그인을 시도 할 것인지 지정
                    .loginProcessingUrl("/doLogin")
//                findByEmail >> password != 사용자가 입력한 password
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                    .logoutUrl("/doLogout")
                .and().build();

    }
}
