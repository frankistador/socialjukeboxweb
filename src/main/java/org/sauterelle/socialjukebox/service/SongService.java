package org.sauterelle.socialjukebox.service;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.sauterelle.socialjukebox.domain.Playlist;
import org.sauterelle.socialjukebox.domain.Song;
import org.sauterelle.socialjukebox.repository.PlaylistRepository;
import org.sauterelle.socialjukebox.repository.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SongService {

	   private final Logger log = LoggerFactory.getLogger(SongService.class);
	   @Inject
	   private SongRepository songRepository;
	   @Inject
	   private PlaylistRepository playlistRepository;
	   private Song song;
	   private Playlist playlist;
	   
	   public void saveSong(String name, String url, Long idPlaylist){
		   log.debug("name="+name+" url="+url +" id="+idPlaylist);
		   song = new Song();
		   song.setName(name);
		   song.setUrl(url);
		   playlist = playlistRepository.findOneWithEagerRelationships(idPlaylist);
		   Set<Playlist> playlists= new HashSet<Playlist> ();
		   playlist.getSongs().add(song);
		   playlists.add(playlist);
		   song.setPlaylists(playlists);
		   songRepository.save(song);
		   playlistRepository.save(playlist);
	       }
	   


}
