package com.likeservice.Controller;


import com.likeservice.Model.Like;
import com.likeservice.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;


    @PostMapping("/postsOrComments/{postOrCommentId}/likes")
    public ResponseEntity<Like> createLike(@PathVariable("postOrCommentId") String postOrCommentId, @RequestBody @Valid Like like){
        return new ResponseEntity<>(likeService.createLike(like,postOrCommentId), HttpStatus.ACCEPTED);
    }
}
