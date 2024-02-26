package com.rumpus.rumpus;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.rumpus.AbstractRumpusTest;
import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.views.AbstractViews;
import com.rumpus.rumpus.config.RumpusConfig;
import com.rumpus.rumpus.config.WebSecurityConfig;
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

@ContextConfiguration(classes = {RumpusConfig.class, WebSecurityConfig.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(RumpusRestController.class)
public class RumpusControllerTests extends AbstractRumpusTest {

    @MockBean IRumpusUserService mockUserService;
    @MockBean AbstractViews viewLoader;
    @Autowired MockMvc mockMvc;

    public RumpusControllerTests() {
        super(RumpusControllerTests.class);
    }

    @Override
    protected void setUpClass() {
    }

    @Override
    protected void tearDownClass() {
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }
 
    @Test
    public void testfindAll() throws Exception {
        RumpusUser user = RumpusUser.createEmptyUser();
        user.setUsername("Frodo");
        user.setPassword("coolpasswordbro");
        List<RumpusUser> users = Arrays.asList(user);

        Mockito.when(mockUserService.getAll()).thenReturn(users);
        mockMvc.perform(get(ICommonController.PATH_API_USERS + "/username")) // sorting by username
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(1)))
            .andExpect(jsonPath("$[0].username", Matchers.is("Frodo")));
    }

    @Test
    public void testFooter() throws Exception {
        RumpusViewLoader vl = RumpusViewLoader.create();
        com.rumpus.common.views.Footer footer = vl.getFooter();

        Mockito.when(viewLoader.getFooter()).thenReturn(footer);

        mockMvc.perform(get(ICommonController.PATH_VIEW_FOOTER))
            .andExpect(status().isOk());

            // .andExpect(jsonPath("$", Matchers.hasSize(1)))
            // .andExpect(jsonPath("$[0].name", Matchers.is("Frodo")));
    }
}
