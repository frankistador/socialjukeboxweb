package org.sauterelle.socialjukebox.web.rest;

import org.sauterelle.socialjukebox.Application;
import org.sauterelle.socialjukebox.domain.Playlist;
import org.sauterelle.socialjukebox.domain.User;
import org.sauterelle.socialjukebox.repository.PlaylistRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.sauterelle.socialjukebox.repository.UserRepository;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlaylistResource REST controller.
 *
 * @see PlaylistResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlaylistResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private PlaylistRepository playlistRepository;

    @Inject
    private UserRepository userRepository;

    private MockMvc restPlaylistMockMvc;

    private Playlist playlist;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlaylistResource playlistResource = new PlaylistResource();
        UserResource userResource = new UserResource();
        ReflectionTestUtils.setField(playlistResource, "playlistRepository", playlistRepository);
        ReflectionTestUtils.setField(userResource, "userRepository", userRepository);
        this.restPlaylistMockMvc = MockMvcBuilders.standaloneSetup(playlistResource).build();
    }

    @Before
    public void initTest() {
        playlist = new Playlist();
        playlist.setName(DEFAULT_NAME);
     }
/*
    @Test
    @Transactional
    public void createPlaylist() throws Exception {
        // Validate the database is empty
        assertThat(playlistRepository.findAll()).hasSize(0);

        // Create the Playlist
        restPlaylistMockMvc.perform(post("/api/playlists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(playlist)))
                .andExpect(status().isCreated());

        // Validate the Playlist in the database
        List<Playlist> playlists = playlistRepository.findAll();
        assertThat(playlists).hasSize(1);
        Playlist testPlaylist = playlists.iterator().next();
        assertThat(testPlaylist.getName()).isEqualTo(DEFAULT_NAME);
    }
*/
    @Test
    @Transactional
    public void getAllPlaylists() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get all the playlists
        restPlaylistMockMvc.perform(get("/api/playlists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(playlist.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getPlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get the playlist
        restPlaylistMockMvc.perform(get("/api/playlists/{id}", playlist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(playlist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaylist() throws Exception {
        // Get the playlist
        restPlaylistMockMvc.perform(get("/api/playlists/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Update the playlist
        playlist.setName(UPDATED_NAME);
        restPlaylistMockMvc.perform(put("/api/playlists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(playlist)))
                .andExpect(status().isOk());

        // Validate the Playlist in the database
        List<Playlist> playlists = playlistRepository.findAll();
        assertThat(playlists).hasSize(1);
        Playlist testPlaylist = playlists.iterator().next();
        assertThat(testPlaylist.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deletePlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get the playlist
        restPlaylistMockMvc.perform(delete("/api/playlists/{id}", playlist.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Playlist> playlists = playlistRepository.findAll();
        assertThat(playlists).hasSize(0);
    }
}
