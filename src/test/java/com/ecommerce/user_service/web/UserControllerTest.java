package com.ecommerce.user_service.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerce.user_service.entity.UserProfile;
import com.ecommerce.user_service.service.UserNotFoundException;
import com.ecommerce.user_service.service.UserProfileService;
import com.ecommerce.user_service.web.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired private MockMvc mvc;

  @MockitoBean private UserProfileService service;

  @Test
  void create_returns201() throws Exception {
    when(service.createOrGet(any(CreateUserRequest.class)))
        .thenReturn(new UserProfile("ada@example.com", "Ada"));

    mvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ada@example.com\",\"displayName\":\"Ada\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.email").value("ada@example.com"));
  }

  @Test
  void create_rejectsInvalidEmail() throws Exception {
    mvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"not-an-email\",\"displayName\":\"Ada\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
  }

  @Test
  void getById_missing_returns404() throws Exception {
    when(service.getById(9L)).thenThrow(new UserNotFoundException("nope"));

    mvc.perform(get("/users/9"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
  }
}
