package com.likeservice.Service;


import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

import java.time.LocalDateTime;


@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;


    public Like likeDetailsOnID(String likeId){
        return likeRepo.findById(likeId).get();
    }


    public String deleteLike(String likeId){
        likeRepo.deleteById(likeId);
        return "Deleted likeId "+likeId+" successfully";
    }



    public int likeCount(String postOrCommentId){

        return likeRepo.findBypostorcommentID(postOrCommentId).size();

    }


    public Like createLike(Like like, String postOrCommentId){
        like.setPostorcommentID(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        return this.likeRepo.save(like);

    }

    public List<Like> getLikesPage(String postOrCommentId,int page, int pageSize){
        Pageable firstPage = PageRequest.of(page, pageSize);

        List<Like> allLikes=likeRepo.findBypostorcommentID(postOrCommentId,firstPage);
        return  allLikes;

    }






}
