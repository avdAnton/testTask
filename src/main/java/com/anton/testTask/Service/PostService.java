package com.anton.testTask.Service;

import com.anton.testTask.domain.Post;
import com.anton.testTask.repository.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PostService {
    private PostRepo postRepo;

    @Autowired
    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getAllPosts(){
        log.info("IN PostService getAllPosts");
        return postRepo.findAll();
    }

    public Post createPost(Post post){
        post.setCreationDate(LocalDateTime.now());
        log.info("IN PostService createPost {}", post);
        return postRepo.insert(post);
    }

    public Post updatePost(Post postFromDb, Post post){
        BeanUtils.copyProperties(post, postFromDb, "id");
        log.info("IN PostService updatePost from {} to {}", postFromDb, post);
        return postRepo.save(postFromDb);
    }

    public void deletePost(Post post){
        log.info("IN PostService deletePost {}", post);
        postRepo.delete(post);
    }
}
