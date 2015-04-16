package org.sauterelle.socialjukebox.web.rest;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;
import org.sauterelle.socialjukebox.service.youtube.YoutubeSearch;
import org.sauterelle.socialjukebox.web.rest.util.HTTPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/api/service/youtube")
public class YoutubeResource {

    private final Logger log = LoggerFactory.getLogger(YoutubeResource.class);

    @RequestMapping(value="/search", method = RequestMethod.POST)
    @ResponseBody
    private String search(@RequestParam String songName) {
        return retrieveOneYoutubeVideo(songName);
    }

    private String retrieveOneYoutubeVideo (String query){
        List<SearchResult> results = YoutubeSearch.search(query, new Long(1));
        if (results.size() == 0) {
            return "https://www.youtube.com/watch?v=oHg5SJYRHA0";
        }
        else return "https://www.youtube.com/watch?v="+results.get(0).getId().getVideoId();

    }

}
