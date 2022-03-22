package com.likeservice.Service;


import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    public Like createLike(Like like, String postOrCommentId){
        like.setPostOrCommentId(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        return this.likeRepo.save(like);

    }
}
