package org.sauterelle.socialjukebox.web.rest;

import org.sauterelle.socialjukebox.Application;
import org.sauterelle.socialjukebox.domain.Song;
import org.sauterelle.socialjukebox.repository.SongRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
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
 * Test class for the SongResource REST controller.
 *
 * @see SongResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SongResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_URL = "SAMPLE_TEXT";
    private static final String UPDATED_URL = "UPDATED_TEXT";
    private static final String DEFAULT_ARTIST = "SAMPLE_TEXT";
    private static final String UPDATED_ARTIST = "UPDATED_TEXT";

    @Inject
    private SongRepository songRepository;

    private MockMvc restSongMockMvc;

    private Song song;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SongResource songResource = new SongResource();
        ReflectionTestUtils.setField(songResource, "songRepository", songRepository);
        this.restSongMockMvc = MockMvcBuilders.standaloneSetup(songResource).build();
    }

    @Before
    public void initTest() {
        song = new Song();
        song.setName(DEFAULT_NAME);
        song.setUrl(DEFAULT_URL);
        song.setArtist(DEFAULT_ARTIST);
    }

    @Test
    @Transactional
    public void createSong() throws Exception {
        // Validate the database is empty
        assertThat(songRepository.findAll()).hasSize(0);

        // Create the Song
        restSongMockMvc.perform(post("/api/songs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(song)))
                .andExpect(status().isCreated());

        // Validate the Song in the database
        List<Song> songs = songRepository.findAll();
        assertThat(songs).hasSize(1);
        Song testSong = songs.iterator().next();
        assertThat(testSong.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSong.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSong.getArtist()).isEqualTo(DEFAULT_ARTIST);
    }

    @Test
    @Transactional
    public void getAllSongs() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songs
        restSongMockMvc.perform(get("/api/songs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(song.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].url").value(DEFAULT_URL.toString()))
                .andExpect(jsonPath("$.[0].artist").value(DEFAULT_ARTIST.toString()));
    }

    @Test
    @Transactional
    public void getSong() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get the song
        restSongMockMvc.perform(get("/api/songs/{id}", song.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(song.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.artist").value(DEFAULT_ARTIST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSong() throws Exception {
        // Get the song
        restSongMockMvc.perform(get("/api/songs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSong() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Update the song
        song.setName(UPDATED_NAME);
        song.setUrl(UPDATED_URL);
        song.setArtist(UPDATED_ARTIST);
        restSongMockMvc.perform(put("/api/songs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(song)))
                .andExpect(status().isOk());

        // Validate the Song in the database
        List<Song> songs = songRepository.findAll();
        assertThat(songs).hasSize(1);
        Song testSong = songs.iterator().next();
        assertThat(testSong.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSong.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSong.getArtist()).isEqualTo(UPDATED_ARTIST);
    }

    @Test
    @Transactional
    public void deleteSong() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get the song
        restSongMockMvc.perform(delete("/api/songs/{id}", song.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Song> songs = songRepository.findAll();
        assertThat(songs).hasSize(0);
    }
}
