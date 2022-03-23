package com.likeservice.Service;


import com.likeservice.Exception.LikeNotFoundException;
import com.likeservice.Feign.FeignUser;
import com.likeservice.Model.FeignRequest;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDTO;
import com.likeservice.Repository.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;


@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private FeignUser feignUser;


    public LikeDTO likeDetailsOnID(String likeId){
        if(likeRepo.findById(likeId).isPresent()){
            Like like = likeRepo.findById(likeId).get();
            LikeDTO likeDTO=new LikeDTO(like.getLikeID(),like.getPostorcommentID(),
                    feignUser.findByID(like.getLikedBy()).getFirstName(),like.getCreatedAt());

            return likeDTO;
        }
        else {
            throw new LikeNotFoundException("Like ID Doesnot Exists");
        }

    }


    public String deleteLike(String likeId){
        if(likeRepo.findById(likeId).isPresent()){
            likeRepo.deleteById(likeId);
            return "Like has been successfully removed.";
        }
        else
        {
            throw new LikeNotFoundException("Like ID Doesnot Exists");
        }
    }



    public int likeCount(String postOrCommentId){


        return likeRepo.findBypostorcommentID(postOrCommentId).size();

    }


    public LikeDTO createLike(Like like, String postOrCommentId){
        like.setPostorcommentID(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
         this.likeRepo.save(like);
         LikeDTO likeDTO =new LikeDTO(like.getLikeID(),like.getPostorcommentID(),
                 feignUser.findByID(like.getLikedBy()).getFirstName(),like.getCreatedAt());
         return likeDTO;

    }

    public List<LikeDTO> getLikesPage(String postOrCommentId,Integer page, Integer pageSize){
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        Pageable firstPage = PageRequest.of(page-1, pageSize);

        List<Like> allLikes=likeRepo.findBypostorcommentID(postOrCommentId,firstPage);
        if(allLikes.isEmpty()){
            throw new LikeNotFoundException("Like ID Doesnot Exists");
        }
        List<LikeDTO> likeDTOS = new ArrayList<>();
        for(Like like:allLikes){
            LikeDTO likeDTO=new LikeDTO(like.getLikeID(),like.getPostorcommentID(),
                    feignUser.findByID(like.getLikedBy()).getFirstName(),like.getCreatedAt());

            likeDTOS.add(likeDTO);
        }
        return  likeDTOS;

    }






}
