package org.sauterelle.socialjukebox.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sauterelle.socialjukebox.Application;
import org.sauterelle.socialjukebox.domain.Authority;
import org.sauterelle.socialjukebox.domain.User;
import org.sauterelle.socialjukebox.repository.AuthorityRepository;
import org.sauterelle.socialjukebox.repository.UserRepository;
import org.sauterelle.socialjukebox.security.AuthoritiesConstants;
import org.sauterelle.socialjukebox.service.MailService;
import org.sauterelle.socialjukebox.service.SubmissionService;
import org.sauterelle.socialjukebox.service.UserService;
import org.sauterelle.socialjukebox.web.rest.dto.UserDTO;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see org.sauterelle.socialjukebox.service.UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubmissionResourceTest {

    @Inject
    private SubmissionService submissionService;
/*
    @Mock
    private UserService mockUserService;

    @Mock
    private MailService mockMailService;
*/
    private MockMvc restUserMockMvc;

    private MockMvc restMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubmissionResource submissionResource = new SubmissionResource();
        ReflectionTestUtils.setField(submissionResource, "submissionService", submissionService);

        SubmissionResource submissionMockResource = new SubmissionResource();
        ReflectionTestUtils.setField(submissionMockResource, "submissionService", submissionService);
        this.restMvc = MockMvcBuilders.standaloneSetup(submissionResource).build();
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(submissionMockResource).build();

/*        doNothing().when(mockMailService).sendActivationEmail((User) anyObject(), anyString());


        AccountResource accountResource = new AccountResource();
        ReflectionTestUtils.setField(accountResource, "userRepository", userRepository);
        ReflectionTestUtils.setField(accountResource, "userService", userService);
        ReflectionTestUtils.setField(accountResource, "mailService", mockMailService);

        AccountResource accountUserMockResource = new AccountResource();
        ReflectionTestUtils.setField(accountUserMockResource, "userRepository", userRepository);
        ReflectionTestUtils.setField(accountUserMockResource, "userService", mockUserService);
        ReflectionTestUtils.setField(accountUserMockResource, "mailService", mockMailService);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource).build();
        */
    }

    @Test
    public void testSubmissionWithEmptyParamsIsNotAllowed() throws Exception {
        restUserMockMvc.perform(post("/api/submission/submit")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testSubmissionWithOneParamIsAccepted() throws Exception {
        restUserMockMvc.perform(post("/api/submission/submit").param("name","test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
