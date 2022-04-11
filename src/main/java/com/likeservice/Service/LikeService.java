package com.likeservice.Service;


import com.likeservice.Const.ConstFile;
import com.likeservice.Exception.LikeExistsException;
import com.likeservice.Exception.PnCIDMismatchException;
import com.likeservice.Exception.LikeNotFoundException;
import com.likeservice.Exception.UserNotFoundException;
import com.likeservice.Feign.FeignUser;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDTO;
import com.likeservice.Model.User;
import com.likeservice.Repository.LikeRepo;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                    feignUser.findByID(like.getLikedBy()),like.getCreatedAt());

            return likeDTO;
        }
        else {
            throw new LikeNotFoundException(ConstFile.errorCode);
        }

    }


    public String deleteLike(String likeId){
        if(likeRepo.findById(likeId).isPresent()){
            likeRepo.deleteById(likeId);
            return ConstFile.successCode;
        }
        else
        {
            throw new LikeNotFoundException(ConstFile.errorCode);
        }
    }



    public int likeCount(String postOrCommentId){


        return likeRepo.findBypostorcommentID(postOrCommentId).size();

    }


    public LikeDTO createLike(Like like, String postOrCommentId){




        if(postOrCommentId.equals(like.getPostorcommentID()))
        {

            if(likeRepo.findBypostorcommentID(postOrCommentId).isEmpty()){

                like.setPostorcommentID(postOrCommentId);
                like.setCreatedAt(LocalDateTime.now());

                try {
                    User user=feignUser.findByID(like.getLikedBy());
                    this.likeRepo.save(like);
                    LikeDTO likeDTO =new LikeDTO(like.getLikeID(),like.getPostorcommentID(),
                          user  ,like.getCreatedAt());

                    return likeDTO;
                }
                catch (FeignException e){
                    throw new UserNotFoundException("No User found with this likedBy ID");
                }
            }
            else{
                List<Like> likes=likeRepo.findBypostorcommentID(postOrCommentId);
                for(Like like1: likes){
                    if(like1.getLikedBy().equals(like.getLikedBy())){
                        throw new LikeExistsException("User Already Liked this ");
                    }
                }

                like.setPostorcommentID(postOrCommentId);
                like.setCreatedAt(LocalDateTime.now());

                try {
                    User user=feignUser.findByID(like.getLikedBy());
                    this.likeRepo.save(like);
                    LikeDTO likeDTO =new LikeDTO(like.getLikeID(),like.getPostorcommentID(),
                            user  ,like.getCreatedAt());

                    return likeDTO;
                }
                catch (FeignException e){
                    throw new UserNotFoundException("No User found with this likedBy ID");
                }

            }

        }
        else{
            throw new PnCIDMismatchException("URL and Body postOrCommentId are not equal ");
        }

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
            throw new PnCIDMismatchException("PostorCommentID doesnot exist");
        }
        List<LikeDTO> likeDTOS = new ArrayList<>();
        for(Like like:allLikes){
            LikeDTO likeDTO=new LikeDTO(like.getLikeID(),like.getPostorcommentID(),
                    feignUser.findByID(like.getLikedBy()),like.getCreatedAt());

            likeDTOS.add(likeDTO);
        }
        return  likeDTOS;

    }






}
