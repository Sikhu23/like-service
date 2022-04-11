package com.likeservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LikeDTO {

    @Id
    private String likeID;

    private String postorcommentID;
    @NotEmpty(message = "user Object is required")
    private User likedBy;


    private LocalDateTime createdAt;
}
