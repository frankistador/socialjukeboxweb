package org.sauterelle.socialjukebox.service;

import javax.inject.Inject;

import org.sauterelle.socialjukebox.web.rest.YoutubeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by William on 09/04/2015.
 */

@Service
@Transactional
public class SubmissionService {
    private final Logger log = LoggerFactory.getLogger(SubmissionService.class);
    @Inject
    private SongService songService;

    
    public void submit(String name){
        log.debug("Submitted song with name : "+name);
        String url = YoutubeResource.retrieveOneYoutubeVideo(name);
        songService.saveSong(name, url);
    }
    
    
}
