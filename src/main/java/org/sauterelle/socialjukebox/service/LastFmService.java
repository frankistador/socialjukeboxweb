package org.sauterelle.socialjukebox.service;

import java.text.DateFormat;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LastFmService {
    private final Logger log = LoggerFactory.getLogger(LastFmService.class);
    private final String key = "b25b959554ed76058ac220b7b2e0a026"; //this is the key used in the Last.fm API examples
    private final String user = "sauterelles";
    
    
    
    public String search(String songName){
    	log.debug("searching with name "+songName);
    	String result ="";
    	return result;
    }

}
