package org.sauterelle.socialjukebox.service;

import javax.inject.Inject;

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

    
/*
TODO: Implement submit method and change the test associated.
 */
    public void submit(String name){
        log.debug("Submitted song with name : "+name);
        songService.saveSong(name, "url");
    }
    
    
}
