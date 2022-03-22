package com.likeservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;


@Document(collection = "LikeService")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Like {

    @Id
    private String likeID;





    private String postorcommentID;
    @NotEmpty(message = "user ID is required")
    private String likedBy;


    private LocalDateTime createdAt;
}
