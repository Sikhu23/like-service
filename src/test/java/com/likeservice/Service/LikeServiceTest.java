package com.likeservice.Service;

import com.likeservice.Const.ConstFile;
import com.likeservice.Exception.LikeNotFoundException;
import com.likeservice.Feign.FeignUser;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDTO;
import com.likeservice.Model.User;
import com.likeservice.Repository.LikeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class LikeServiceTest {

    @InjectMocks
    LikeService likeService;

    @Mock
    FeignUser feignUser;

    @Mock
    LikeRepo likeRepo;

    private User createUser() throws ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        user.setUserID("123");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender("MALE");
        return user;

    }


    private Like createLikeModel(){
        return new Like("1","12","123",null);
    }

    private LikeDTO createLikeDTO() throws ParseException {
        return new LikeDTO("1","12",createUser(),null);
    }


    @Test
    void likeDetailsOnID() throws ParseException {
        Like like = createLikeModel();
        LikeDTO likeDTO =createLikeDTO();

        Mockito.when(this.likeRepo.findById("1")).thenReturn(Optional.of(like));

        assertThat(this.likeService.likeDetailsOnID("1").getLikeID()).isEqualTo(likeDTO.getLikeID());
        assertThat(this.likeService.likeDetailsOnID("1").getPostorcommentID()).isEqualTo(likeDTO.getPostorcommentID());
        assertThrows(LikeNotFoundException.class,()->this.likeService.likeDetailsOnID("2"));

    }

    @Test
    void deleteLike() throws ParseException {

        Like like = createLikeModel();
        LikeDTO likeDTO =createLikeDTO();

        doNothing().when(this.likeRepo).deleteById("1");
        when(this.likeRepo.findById("1")).thenReturn(Optional.of(like));

        assertEquals(this.likeService.deleteLike("1"), ConstFile.successCode);
        assertThrows(LikeNotFoundException.class,()->this.likeService.deleteLike("2"));
    }

    @Test
    void likeCount() throws ParseException {

        Like like = createLikeModel();
        LikeDTO likeDTO =createLikeDTO();

        when(this.likeRepo.findById("1")).thenReturn(Optional.of(like));

        assertEquals(this.likeService.likeCount("1"),0);


    }

    @Test
    void createLike() throws ParseException {

        Like like = createLikeModel();
        LikeDTO likeDTO =createLikeDTO();
        List<Like> likeList = new ArrayList<>();
        likeList.add(like);

        when(this.likeRepo.save(any(Like.class))).thenReturn(like);
        when(this.likeRepo.findBypostorcommentID("1")).thenReturn(likeList);


        assertThat(this.likeService.createLike(like,"12").getLikeID()).isEqualTo(likeDTO.getLikeID());
    }

    @Test
    void getLikesPage() throws ParseException {
        Like like = createLikeModel();
        LikeDTO likeDTO =createLikeDTO();
        List<Like> likeList = new ArrayList<>();
        likeList.add(like);



        PageImpl<Like> pageImpl = new PageImpl<>(likeList);
        Pageable firstPage = PageRequest.of(0, 2);

        when(this.likeRepo.findBypostorcommentID("12",firstPage)).thenReturn( likeList);


        assertEquals(1,this.likeService.getLikesPage("12",null,2).size());




    }
}