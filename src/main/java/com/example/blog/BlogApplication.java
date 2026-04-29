package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.springframework.data.jpa.repository.JpaRepository;


@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}


@Entity
@Table(name = "blogs")
class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;

	Blog(){}

	Blog(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

interface BlogRepository extends JpaRepository<Blog, Long> {
	// JpaRepository provides basic CRUD operations
}

@Controller
@RequestMapping()
class BlogController {

	private final BlogRepository blogRepository;

	public BlogController(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}


	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/blog-create")
	public String blogCreate() {
		return "blog-create";
	}

	@PostMapping("/blog-create")
	public String createBlog(@RequestBody Blog blog) {
		blogRepository.save(blog);
		return "home";
	}

	@GetMapping("/blog-view")
	public String blogView() {
		return "blog-view";
	}

}