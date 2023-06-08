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

import com.rumpus.RumpusTest;
import com.rumpus.common.views.IViewLoader;
import com.rumpus.rumpus.config.RumpusConfig;
import com.rumpus.rumpus.config.WebSecurityConfig;
import com.rumpus.rumpus.controller.RumpusController;
import com.rumpus.rumpus.controller.RumpusRestController;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.service.UserService;
import com.rumpus.rumpus.views.IRumpusViewLoader;
import com.rumpus.rumpus.views.RumpusViewLoader;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RumpusConfig.class, WebSecurityConfig.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(RumpusRestController.class)
public class RumpusControllerTests extends RumpusTest {

    @MockBean
    IRumpusUserService userService;
    @MockBean
    IRumpusViewLoader viewLoader;
 
    @Autowired
    MockMvc mockMvc;
 
    @Test
    public void testfindAll() throws Exception {
        // RumpusUser user = RumpusUser.create(Map.of("username", "Frodo"));
        RumpusUser user = new RumpusUser();
        user.setUsername("Frodo");
        user.setPassword("coolpasswordbro");
        List<RumpusUser> users = Arrays.asList(user);

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get(PATH_API_USERS))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(1)))
            .andExpect(jsonPath("$[0].username", Matchers.is("Frodo")));
    }

    @Test
    public void testFooter() throws Exception {
        RumpusViewLoader vl = new RumpusViewLoader();
        com.rumpus.common.views.Footer footer = vl.getFooter();

        Mockito.when(viewLoader.getFooter()).thenReturn(footer);

        mockMvc.perform(get(PATH_VIEW_FOOTER))
            .andExpect(status().isOk());

            // .andExpect(jsonPath("$", Matchers.hasSize(1)))
            // .andExpect(jsonPath("$[0].name", Matchers.is("Frodo")));
    }
}
