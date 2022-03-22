package com.likeservice.Service;


import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import java.time.LocalDateTime;


@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;


    public String deleteLike(String likeId){
        likeRepo.deleteById(likeId);
        return "Deleted likeId "+likeId+" successfully";
    }



    public int likeCount(String postOrCommentId){
        List<Like> allData=likeRepo.findAll();
        int count=0;
        for(Like like:allData){
            if(like.getPostOrCommentId().equals(postOrCommentId)){
                count++;
            }
        }
        return count;
    }


    public Like createLike(Like like, String postOrCommentId){
        like.setPostOrCommentId(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        return this.likeRepo.save(like);

    }


}
