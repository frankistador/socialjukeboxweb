package org.sauterelle.socialjukebox.web.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/service/lastfm")
public class LastFmResource {
private final String SERVICE_ENDPOINT= "http://www.last.fm/api/";
private final String API_KEY = "523cb53942efb6cbac609f4e3a0c3a94";
private final String SECRET = "is bde8cd4980b1633e91a6e1a46c6ee563";

private final Logger log = LoggerFactory.getLogger(LastFmResource.class);


	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView method() {
            return new ModelAndView("redirect:" + SERVICE_ENDPOINT + "auth/?api_key="+ API_KEY);
    }
	
	
	
	
	@RequestMapping(value="/sign", method = RequestMethod.POST)
	@ResponseBody
	private byte[] sign(@RequestBody HashMap<String,String> params) throws NoSuchAlgorithmException{
		log.debug("params = "+params);
		return signMethod(params);
	}
	@RequestMapping(value="/test", method = RequestMethod.POST)
	@ResponseBody
	private byte[] test(@RequestBody String params) {
		log.debug("params = "+params);
		return params.getBytes();
	}
	/*
	 * TODO: Refactor this code.
	 */
	private byte[] signMethod(HashMap<String, String> parameters) throws NoSuchAlgorithmException{
		String toHash = "";
		ArrayList<String> paramNames = (ArrayList<String>) parameters.keySet();
		Collections.sort(paramNames);
		for (String paramName : paramNames){
			toHash += paramName + parameters.get(paramName);
		}
		byte[] hashMe = toHash.getBytes();
		MessageDigest mg = MessageDigest.getInstance("md5");
		hashMe = mg.digest(hashMe);
		log.debug("hashed paramaters : "+hashMe);
		return hashMe;
	}
	
}
