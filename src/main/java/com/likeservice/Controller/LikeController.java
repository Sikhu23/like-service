package com.likeservice.Controller;


import com.likeservice.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
}
