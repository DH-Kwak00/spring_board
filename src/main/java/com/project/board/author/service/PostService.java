package com.project.board.author.service;

import com.project.board.author.domain.Author;
import com.project.board.author.domain.Post;
import com.project.board.author.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(Post post){
        postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();

    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void delete(Long id){
        postRepository.delete(findById(id));
    }

    public List<Post> findByAuthor_id(Long id){
        return postRepository.findByAuthor_id(id);
    }

    public List<Post> findByScheduled(String scheduled){
        return postRepository.findByScheduled(scheduled);
    }
}
