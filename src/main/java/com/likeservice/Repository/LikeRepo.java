package com.likeservice.Repository;

import com.likeservice.Model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeRepo extends MongoRepository<Like,String> {


}
