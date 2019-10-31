package com.anton.testTask.controller;

import com.anton.testTask.Service.PostService;
import com.anton.testTask.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> list() {
        return postService.getAllPosts();
    }

    @GetMapping("{id}")
    public Post getOne(@PathVariable("id") Post post) {
        return post;
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("{id}")
    public Post update(
            @PathVariable("id") Post postFromDb,
            @RequestBody Post post
    ) {
        return postService.updatePost(postFromDb, post);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Post post) {
        postService.deletePost(post);
    }
}
