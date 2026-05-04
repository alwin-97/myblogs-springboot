package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import java.util.List;


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
	public String home(Model model) {
		List<Blog> blogs = blogRepository.findAll();
		model.addAttribute("blogs", blogs);
		return "home";
	}

	@GetMapping("/blog-create")
	public String blogCreate(Model model) {
		Blog blog = new Blog();
		model.addAttribute("blog", blog);
		model.addAttribute("formAction", "/blog-create");
		model.addAttribute("formTitle", "Create Blog");
		model.addAttribute("submitLabel", "Create Blog");
		return "blog-create";
	}

	@PostMapping("/blog-create")
	public String createBlog(Blog blog) {
		blogRepository.save(blog);
		return "redirect:/";
	}

	@GetMapping("/blog-view/{id}")
	public String blogView(@PathVariable("id") Long id, Model model) {
		Blog blog = blogRepository.findById(id).orElse(null);
		if (blog == null) {
			return "redirect:/";
		}
		model.addAttribute("blog",blog);
		return "blog-view";
	}

	@GetMapping("/blog-edit/{id}")
	public String blogEdit(@PathVariable("id") Long id, Model model) {
		Blog blog = blogRepository.findById(id).orElse(null);
		if (blog == null) {
			return "redirect:/";
		}
		model.addAttribute("blog",blog);
		model.addAttribute("formAction", "/blog-edit/" + id);
		model.addAttribute("formTitle", "Edit Blog");
		model.addAttribute("submitLabel", "Update Blog");
		return "blog-create";
	}

	@PostMapping("/blog-edit/{id}")
	public String updateBlog(@PathVariable("id") Long id, Blog blogForm) {
		Blog blog = blogRepository.findById(id).orElse(null);
		if (blog == null) {
			return "redirect:/";
		}

		blog.setTitle(blogForm.getTitle());
		blog.setContent(blogForm.getContent());
		blogRepository.save(blog);

		return "redirect:/blog-view/" + id;
	}
	
}
