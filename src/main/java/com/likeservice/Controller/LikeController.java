package com.likeservice.Controller;


import com.likeservice.Model.FeignRequest;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDTO;
import com.likeservice.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/postsOrComments/{postOrCommentId}")
public class LikeController {

    @Autowired
    private LikeService likeService;




    @GetMapping("/likes/{likeId}")
    public ResponseEntity<LikeDTO> likeDetailsOnID(@PathVariable("likeId") String likeId, @PathVariable("postOrCommentId") String postOrCommentId) {
        return new ResponseEntity<>(likeService.likeDetailsOnID(likeId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/likes/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable("likeId") String likeId,@PathVariable("postOrCommentId") String postOrCommentId ) {
        return new ResponseEntity<>(likeService.deleteLike(likeId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/likes/count")
    public ResponseEntity<Integer> likeCount(@PathVariable("postOrCommentId") String postOrCommentId) {
        return new ResponseEntity<>(likeService.likeCount(postOrCommentId), HttpStatus.ACCEPTED);
    }


    @PostMapping("/likes")
    public ResponseEntity<LikeDTO> createLike(@PathVariable("postOrCommentId") String postOrCommentId, @RequestBody @Valid Like like){
        return new ResponseEntity<>(likeService.createLike(like,postOrCommentId), HttpStatus.ACCEPTED);


    }

        @GetMapping("/likes")
        public  ResponseEntity<List<LikeDTO>> getLikesPage(@PathVariable("postOrCommentId") String postOrCommentId, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize){
            return new ResponseEntity<>(likeService.getLikesPage(postOrCommentId,page,pageSize),HttpStatus.ACCEPTED);
        }
}
