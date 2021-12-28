package com.major.mylog.controller;

import com.major.mylog.model.Post;
import com.major.mylog.repository.PostRepository;
import com.major.mylog.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class PostController {

    @Autowired
    private PostRepository postrepository;

    @Autowired
    private PostValidator postvalidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        // Page<Post> posts = postrepository.findAll(pageable);
        Page<Post> posts = postrepository.findByTitleContainingOrTextContaining(searchText, searchText, pageable);
        int startPage = Math.max(1,posts.getPageable().getPageNumber() - 4);
        int endPage = Math.min(Math.max(1, posts.getTotalPages()), posts.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("posts", posts);
        return "board/list";
    }


    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if(id == null){
            model.addAttribute("post", new Post());
        }

        else{
            Post post = postrepository.findById(id).orElse(null);
            model.addAttribute("post", post);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String formSubmit(@Valid Post post, BindingResult bindingResult) {
       postvalidator.validate(post, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        postrepository.save(post);
        return "redirect:/board/list";
    }
}
