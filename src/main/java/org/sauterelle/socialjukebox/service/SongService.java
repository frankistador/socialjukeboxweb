package org.sauterelle.socialjukebox.service;

import java.util.Set;

import javax.inject.Inject;

import org.sauterelle.socialjukebox.domain.Playlist;
import org.sauterelle.socialjukebox.domain.Song;
import org.sauterelle.socialjukebox.repository.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SongService {

	   private final Logger log = LoggerFactory.getLogger(SongService.class);
	   @Inject
	   private SongRepository songRepository;
	   private Song song;
	   
	   public void saveSong(String name, String url){
		   song = new Song();
		   song.setName(name);
		   song.setUrl(url);
		   songRepository.save(song);
	       }
	   


}
