package org.sauterelle.socialjukebox.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.sauterelle.socialjukebox.domain.*;
import org.sauterelle.socialjukebox.repository.PlaylistRepository;
import org.sauterelle.socialjukebox.repository.UserRepository;
import org.sauterelle.socialjukebox.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * REST controller for managing Playlist.
 */
@RestController
@RequestMapping("/api")
public class PlaylistResource {

    private final Logger log = LoggerFactory.getLogger(PlaylistResource.class);

    @Inject
    private PlaylistRepository playlistRepository;
    @Inject 
    private UserRepository userRepository;

    /**
     * POST  /playlists -> Create a new playlist.
     */
    @RequestMapping(value = "/playlists",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Playlist playlist) throws URISyntaxException {
        log.debug("REST request to save Playlist : {}", playlist);
        User user = userRepository.findOneByLogin(playlist.getHost().getLogin());
        playlist.setHost(user);
        if (playlist.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new playlist cannot already have an ID").build();
        }
        playlistRepository.save(playlist);
        return ResponseEntity.created(new URI("/api/playlists/" + playlist.getId())).build();
    }

    /**
     * PUT  /playlists -> Updates an existing playlist.
     */
    @RequestMapping(value = "/playlists",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Playlist playlist) throws URISyntaxException {
        log.debug("REST request to update Playlist : {}", playlist);
        if (playlist.getId() == null) {
            return create(playlist);
        }
        playlistRepository.save(playlist);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /playlists -> get all the playlists.
     */
    @RequestMapping(value = "/playlists",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Playlist>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Playlist> page = playlistRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/playlists", offset, limit);
        return new ResponseEntity<List<Playlist>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /playlists/:id -> get the "id" playlist.
     */
    @RequestMapping(value = "/playlists/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Playlist> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Playlist : {}", id);
        Playlist playlist = playlistRepository.findOneWithEagerRelationships(id);
        if (playlist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    /**
     * DELETE  /playlists/:id -> delete the "id" playlist.
     */
    @RequestMapping(value = "/playlists/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Playlist : {}", id);
        playlistRepository.delete(id);
    }
}
