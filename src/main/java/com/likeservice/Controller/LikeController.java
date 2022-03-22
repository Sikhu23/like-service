package com.likeservice.Controller;


import com.likeservice.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/postsOrComments/{postOrCommentId}/likes/count")
    public ResponseEntity<Integer> likeCount(@PathVariable("postOrCommentId") String postOrCommentId){
        return new ResponseEntity<>(likeService.likeCount(postOrCommentId), HttpStatus.ACCEPTED);
    }
}
