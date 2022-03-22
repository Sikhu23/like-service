package com.likeservice.Service;


import com.likeservice.Repository.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    public String deleteLike(String likeId){
        likeRepo.deleteById(likeId);
        return "Deleted likeId "+likeId+" successfully";
    }
}
