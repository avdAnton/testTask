package com.anton.testTask.repository;

import com.anton.testTask.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepo extends MongoRepository<Post, String> {
}
