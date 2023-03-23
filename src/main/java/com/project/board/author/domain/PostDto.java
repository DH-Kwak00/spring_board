package com.project.board.author.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String contents;
    private String createDate;
    private String email;
    private String scheduled;
    private String scheduledTime;
}
