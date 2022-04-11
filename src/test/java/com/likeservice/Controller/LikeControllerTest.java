package com.likeservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likeservice.Const.ConstFile;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDTO;
import com.likeservice.Model.User;
import com.likeservice.Service.LikeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @MockBean
    LikeService likeService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private User createOneUser() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user1 = new User();

        user1.setUserID("123");
        user1.setFirstName("FirstID");
        user1.setMiddleName("J");
        user1.setLastName("S");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("prabhu@mail.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup("O+");
        user1.setGender(String.valueOf("MALE"));
        user1.setAddress("Pune");
        return user1;
    }

    private Like createDummyLike(){
        return new Like("1","12","123",null);

    }
    private LikeDTO createDummyLikeDTO() throws ParseException {
        return new LikeDTO("1","12",createOneUser(),null);

    }
    private List<Like> createListLike(){
        List<Like> likeList =new ArrayList<>();
        Like like1= new Like("1","12","123",null);
        Like like2=new Like("2","12","123",null);
        likeList.add(like1);
        likeList.add(like2);
        return likeList;

    }
    private List<LikeDTO> createListLikeDto() throws ParseException {
        List<LikeDTO> likeList =new ArrayList<>();
        LikeDTO like1= new LikeDTO("1","12",createOneUser(),null);
        LikeDTO like2=new LikeDTO("2","12",createOneUser(),null);
        likeList.add(like1);
        likeList.add(like2);
        return likeList;

    }

    @Test
    void likeDetailsOnID() throws Exception {
        Like like =createDummyLike();
        LikeDTO likeDTO =createDummyLikeDTO();
        Mockito.when(likeService.likeDetailsOnID("1")).thenReturn(likeDTO);

        mockMvc.perform(get("/postsOrComments/1/likes/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(jsonPath("$.likeID", Matchers.is("1")));

    }

    @Test
    void deleteLike() throws Exception {
        Mockito.when(likeService.deleteLike("1")).thenReturn(ConstFile.successCode);
        mockMvc.perform(delete("/postsOrComments/1/likes/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.is(ConstFile.successCode)));
    }

    @Test
    void likeCount() throws Exception {

        Mockito.when(likeService.likeCount("1")).thenReturn(0);

        mockMvc.perform(get("/postsOrComments/1/likes/count"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.is(0)));


    }

    @Test
    void createLike() throws Exception {
        Like like =createDummyLike();
        LikeDTO likeDTO =createDummyLikeDTO();
        Mockito.when(likeService.createLike(like,"1")).thenReturn(likeDTO);

        mockMvc.perform(post("/postsOrComments/1/likes")
                        .content(asJsonString(like))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.likeID", Matchers.is("1")));

    }

    @Test
    void getLikesPage() throws Exception {
        List<Like> likes =createListLike();
        List<LikeDTO> likeDTOs =createListLikeDto();
        Mockito.when(likeService.getLikesPage("1",1,2)).thenReturn(likeDTOs);

        mockMvc.perform(get("/postsOrComments/1/likes?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].likeID", Matchers.is("1")))
                .andExpect(jsonPath("$[1].likeID", Matchers.is("2")));
    }
}