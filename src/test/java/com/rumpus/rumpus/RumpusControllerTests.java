package com.rumpus.rumpus;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rumpus.rumpus.config.RumpusTestConfig;
import com.rumpus.rumpus.controller.RumpusRestController;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.service.UserService;
import com.rumpus.rumpus.views.Footer;
import com.rumpus.rumpus.views.IViewLoader;
import com.rumpus.rumpus.views.ViewLoader;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Arrays;
import java.util.List;
 
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(classes = RumpusTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(RumpusRestController.class)
public class RumpusControllerTests {

    @MockBean
    IUserService userService;
    @MockBean
    IViewLoader viewLoader;
 
    @Autowired
    MockMvc mockMvc;
 
    @Test
    public void testfindAll() throws Exception {
        User user = User.createWithName("Frodo");
        List<User> users = Arrays.asList(user);

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(1)))
            .andExpect(jsonPath("$[0].userName", Matchers.is("Frodo")));
    }

    @Test
    public void testFooter() throws Exception {
        ViewLoader vl = new ViewLoader();
        Footer footer = vl.getFooter();

        Mockito.when(viewLoader.getFooter()).thenReturn(footer);

        mockMvc.perform(get("/api/footer"))
            .andExpect(status().isOk());

            // .andExpect(jsonPath("$", Matchers.hasSize(1)))
            // .andExpect(jsonPath("$[0].name", Matchers.is("Frodo")));
    }
}
