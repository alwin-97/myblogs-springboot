package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}

@Controller
@RequestMapping()
class BlogController {

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/blog-create")
	public String blogCreate() {
		return "blog-create";
	}

	@GetMapping("/blog-view")
	public String blogView() {
		return "blog-view";
	}

}