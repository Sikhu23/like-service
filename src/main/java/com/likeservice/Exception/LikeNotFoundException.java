package com.likeservice.Exception;

public class LikeNotFoundException extends RuntimeException{
    public LikeNotFoundException(String message){
        super(message);
    }
}
