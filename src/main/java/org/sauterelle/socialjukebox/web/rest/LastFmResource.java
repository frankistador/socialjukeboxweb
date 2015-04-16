package org.sauterelle.socialjukebox.web.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.google.api.services.youtube.YouTube;
import org.json.JSONException;
import org.json.JSONObject;
import org.sauterelle.socialjukebox.service.youtube.YoutubeSearch;
import org.sauterelle.socialjukebox.web.rest.util.HTTPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/service/lastfm")
public class LastFmResource {
private final String SERVICE_ENDPOINT= "http://www.last.fm/api/";
private final String API_KEY = "523cb53942efb6cbac609f4e3a0c3a94";
private final String SECRET = "is bde8cd4980b1633e91a6e1a46c6ee563";

    private static YouTube youtube;

    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;


    private final Logger log = LoggerFactory.getLogger(LastFmResource.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView method() {
            return new ModelAndView("redirect:" + SERVICE_ENDPOINT + "auth/?api_key="+ API_KEY);
    }

    @RequestMapping(value="/sign", method = RequestMethod.POST)
    @ResponseBody
    private byte[] sign(@RequestBody JSONObject params) throws NoSuchAlgorithmException, JSONException {
        return signMethod(this.toParamsMap(params));
    }

    @RequestMapping(value="/search", method = RequestMethod.POST)
    @ResponseBody
    private String search(@RequestParam String songName) throws Exception {
        String url = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="+songName+"&api_key="+API_KEY+"&format=json&limit=1";
        url = url.replaceAll(" ","%20");
        JSONObject response = HTTPUtils.getJson(url);
        return response.getJSONObject("results").getJSONObject("trackmatches").getJSONObject("track").getString("url");
    }

	/*
	 * TODO: Refactor this code.
	 */
	private byte[] signMethod(Map<String, String> parameters) throws NoSuchAlgorithmException{
		String toHash = "";
		List<String> paramNames = new ArrayList<String>(parameters.keySet());
		Collections.sort(paramNames);
		for (String paramName : paramNames){
			toHash += paramName + parameters.get(paramName);
		}
        toHash += SECRET;
		byte[] hashMe = toHash.getBytes();
		MessageDigest mg = MessageDigest.getInstance("md5");
		hashMe = mg.digest(hashMe);
		return hashMe;
	}

    public Map<String, String> toParamsMap(JSONObject json) throws JSONException {
        Map<String, String> retMap = new HashMap<String, String>();
        Iterator<String> keysItr = json.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            String value = (String) json.get(key);
            retMap.put(key, value);
        }
        return retMap;
    }

}
