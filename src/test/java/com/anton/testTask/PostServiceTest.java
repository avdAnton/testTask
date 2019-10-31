package com.anton.testTask;

import com.anton.testTask.Service.PostService;
import com.anton.testTask.domain.Post;
import com.anton.testTask.repository.PostRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PostServiceTest {
	@Autowired
	private PostService postService;

	@MockBean
	private PostRepo postRepo;

	@Test
	void getAllPostTest() throws Exception {
		Mockito.when(postRepo.findAll()).thenReturn(Arrays.asList(new Post()));
		List<Post> list = postService.getAllPosts();
		Mockito.verify(postRepo, Mockito.times(1)).findAll();
		assertEquals(1,list.size());
	}

	@Test
	void createPostTest() throws Exception {
		Mockito.when(postRepo.insert(new Post())).thenReturn(new Post());
		Post post = new Post();
		postService.createPost(post);
		Mockito.verify(postRepo, Mockito.times(1)).insert(post);
		assertTrue(post.getCreationDate()!=null);
	}

	@Test
	void updatePostTest() throws Exception {
		Mockito.when(postRepo.save(new Post())).thenReturn(new Post());

		Post postFromDb = new Post();
		postFromDb.setId("123");
		postFromDb.setText("some text");

		Post post = new Post();
		post.setText("some new text");

		postService.updatePost(postFromDb, post);
		Mockito.verify(postRepo, Mockito.times(1)).save(postFromDb);
		assertEquals("123", postFromDb.getId());
		assertEquals("some new text", postFromDb.getText());
	}

	@Test
	void deletePostTest() throws Exception{
		Post post = new Post();
		postService.deletePost(post);
		Mockito.verify(postRepo, Mockito.times(1)).delete(post);
	}


}
