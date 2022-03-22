package com.likeservice.Service;


import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    public Like likeDetailsOnID(String likeId){
        return likeRepo.findById(likeId).get();
    }
}
