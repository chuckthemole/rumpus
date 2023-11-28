package com.rumpus.rumpus;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.rumpus.RumpusTest;
import com.rumpus.common.views.AbstractViewLoader;
import com.rumpus.rumpus.config.RumpusConfig;
import com.rumpus.rumpus.config.WebSecurityTestConfig;
import com.rumpus.rumpus.controller.RumpusRestController;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.views.RumpusViewLoader;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RumpusConfig.class, WebSecurityTestConfig.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(RumpusRestController.class)
public class RumpusControllerTests extends RumpusTest {

    @MockBean IRumpusUserService userService;
    @MockBean AbstractViewLoader viewLoader;
 
    @Autowired MockMvc mockMvc;
 
    @Test
    public void testfindAll() throws Exception {
        RumpusUser user = RumpusUser.createEmptyUser();
        user.setUsername("Frodo");
        user.setPassword("coolpasswordbro");
        List<RumpusUser> users = Arrays.asList(user);

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get(PATH_API_USERS + "/username")) // sorting by username
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(1)))
            .andExpect(jsonPath("$[0].username", Matchers.is("Frodo")));
    }

    @Test
    public void testFooter() throws Exception {
        RumpusViewLoader vl = RumpusViewLoader.create();
        com.rumpus.common.views.Footer footer = vl.getFooter();

        Mockito.when(viewLoader.getFooter()).thenReturn(footer);

        mockMvc.perform(get(PATH_VIEW_FOOTER))
            .andExpect(status().isOk());

            // .andExpect(jsonPath("$", Matchers.hasSize(1)))
            // .andExpect(jsonPath("$[0].name", Matchers.is("Frodo")));
    }
}
