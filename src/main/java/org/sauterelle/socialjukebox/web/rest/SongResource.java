package org.sauterelle.socialjukebox.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.sauterelle.socialjukebox.domain.Playlist;
import org.sauterelle.socialjukebox.domain.Song;
import org.sauterelle.socialjukebox.repository.PlaylistRepository;
import org.sauterelle.socialjukebox.repository.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * REST controller for managing Song.
 */
@RestController
@RequestMapping("/api")
public class SongResource {

    private final Logger log = LoggerFactory.getLogger(SongResource.class);

    @Inject
    private SongRepository songRepository;

    @Inject
    private PlaylistRepository playlistRepository;

    /**
     * POST  /songs -> Create a new song.
     */
    @RequestMapping(value = "/songs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Song song) throws URISyntaxException {
        log.debug("REST request to save Song : {}", song);
        if (song.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new song cannot already have an ID").build();
        }
        songRepository.save(song);
        return ResponseEntity.created(new URI("/api/songs/" + song.getId())).build();
    }

   
    /**
     * PUT  /songs -> Updates an existing song.
     */
    @RequestMapping(value = "/songs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Song song) throws URISyntaxException {
        log.debug("REST request to update Song : {}", song);
        if (song.getId() == null) {
            return create(song);
        }
        songRepository.save(song);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /songs -> get all the songs.
     */
    @RequestMapping(value = "/songs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Song> getAll() {
        log.debug("REST request to get all Songs");
        return songRepository.findAll();
    }

    /**
     * GET  /songs/:id -> get the "id" song.
     */
    @RequestMapping(value = "/songs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Song> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Song : {}", id);
        Song song = songRepository.findOne(id);
        if (song == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    /**
     * DELETE  /songs/:id -> delete the "id" song.
     */
    @RequestMapping(value = "/songs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Song : {}", id);
        songRepository.delete(id);
    }
}
