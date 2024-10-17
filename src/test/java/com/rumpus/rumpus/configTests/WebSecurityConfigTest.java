package com.rumpus.rumpus.configTests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.rumpus.common.ICommon;
import com.rumpus.common.Controller.ICommonController;

/**
 * TODO: instead of PATH_API_USERS, can we use a TEST_PROTECTED_ENDPOINT?
 * @throws Exception
 */
@SpringBootTest
@AutoConfigureMockMvc
public class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPageAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get(ICommonController.PATH_INDEX))
                .andExpect(status().isOk());
    }

    @Test
    public void testProtectedPageRequiresAuthentication() throws Exception {
        mockMvc.perform(get(ICommonController.PATH_API_USERS))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = ICommon.ADMIN)
    public void testAdminAccessToProtectedPage() throws Exception {
        mockMvc.perform(get(ICommonController.PATH_API_USERS + "/username")) // TODO: replace final string
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    @Test
    @WithMockUser(roles = ICommon.USER)
    public void testNonAdminAccessToProtectedPage() throws Exception {
        mockMvc.perform(get(ICommonController.PATH_API_USERS))
                .andExpect(status().isForbidden());
    }

    // TODO: Create a mock user for this test. I don't want to expose any passwords that are in the database for valid users.
    // @Test
    // public void testLoginWithValidCredentials() throws Exception {
    //     mockMvc.perform(post(ICommonController.PATH_LOGIN)
    //             .param(ICommon.USERNAME, "chuck")
    //             .param("password", "validPassword") // Replace with a valid password
    //             .with(csrf()))  // Add CSRF token
    //             .andExpect(status().isOk());
    // }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        mockMvc.perform(post(ICommonController.PATH_LOGIN)
                .param(ICommon.USERNAME, "invalidUser")
                .param("password", "invalidPassword")
                .with(csrf()))  // Add CSRF token
                .andExpect(status().isUnauthorized());
    }
}
