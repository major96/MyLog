package com.major.mylog.controller;



import java.util.List;

import com.major.mylog.model.Post;
import com.major.mylog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class PostApiController {

    @Autowired
    private PostRepository repository;


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/posts")
    List<Post> all(@RequestParam(required = false, defaultValue = "") String title
    , @RequestParam(required = false, defaultValue = "") String text) {
        if((StringUtils.isEmpty(title)) && (StringUtils.isEmpty(text))){
            return repository.findAll();
        }else{
            return repository.findByTitleOrText(title, text);
        }
    }
    // end::get-aggregate-root[]

    @PostMapping("/posts")
    Post newPost(@RequestBody Post newPost) {
        return repository.save(newPost);
    }

    // Single item

    @GetMapping("/posts/{id}")
    Post one(@PathVariable Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    @PutMapping("/posts/{id}")
    Post replacePost(@RequestBody Post newPost, @PathVariable Long id) {

        return repository.findById(id)
                .map(Post -> {
                    Post.setTitle(newPost.getTitle());
                    Post.setText(newPost.getText());
                    return repository.save(Post);
                })
                .orElseGet(() -> {
                    newPost.setId(id);
                    return repository.save(newPost);
                });
    }

    @DeleteMapping("/posts/{id}")
    void deletePost(@PathVariable Long id) {
        repository.deleteById(id);
    }
}