package com.likeservice.Exception;

public class LikeExistsException extends RuntimeException {
    public LikeExistsException(String msg){
        super(msg);
    }
}
