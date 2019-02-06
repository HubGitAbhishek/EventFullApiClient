package com.eventful.client;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.SearchResult;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.operations.EventOperations;

@RestController
public class CallEventfulApiService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(CallEventfulApiService.class);
	
	@Value("${application.apikey}")
	private String API_KEY;
	
	@Value("${application.db.user}")
	private String dbuser;
	
	@Value("${application.db.password}")
	private String dbpassword;
	
	@Value("${application.eventful.host}")
	private String eventfulHost;

       @GetMapping(value ="/getsearchresult1" , produces = MediaType.APPLICATION_XML_VALUE)
		public SearchResult getSearchResultFromWeb(@RequestParam String keyword, @RequestParam String location)
		{		
    	   return getSearchResultfromWebBL(keyword, location);
		}

	private SearchResult getSearchResultfromWebBL(String keyword, String location) {
		String transactionUrl = eventfulHost+ "/rest/events/search";
    	   
    	   UriComponentsBuilder builder = UriComponentsBuilder
    	       .fromUriString(transactionUrl)
    	       .queryParam("keywords", keyword)
    	       .queryParam("location", location)
    	       .queryParam("app_key", API_KEY);
    	   
			//String uri = eventfulHost+"/rest/events/search?keywords=music&location=Singapore&app_key=" + API_KEY;			
			RestTemplate restTemplate = new RestTemplate();
			//String result = restTemplate.getForObject(builder.toUriString(), String.class);
			SearchResult searchresult = restTemplate.getForObject(builder.toUriString(), SearchResult.class);
						
			//Sort			
			Collections.sort(searchresult.getEvents(), new Comparator<Event>() {
				@Override
				public int compare(Event e1, Event e2) {
					return  e1.getStartTime().compareTo(e2.getStartTime());
				}
			});
						
			return searchresult;
	}
       
       @GetMapping(value ="/getsearchresult2" , produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
		public List getSearchResultFromBackend(@RequestParam String keyword, @RequestParam String location)
		{
    	   return getSearchResultFromBackendBL(keyword, location);
		}

	private List getSearchResultFromBackendBL(String keyword, String location) {
		EventOperations eventOperations = new EventOperations();
    	   EventSearchRequest eventSearchResult = populateEventSearchResult(keyword, location);        
           SearchResult searchResult = null;
           try {
                   searchResult = eventOperations.search(eventSearchResult);
                   if (searchResult.getTotalItems() > 1) {

                	   LOGGER.debug("Total items: " + searchResult.getTotalItems());
                   }
           }
           catch(EVDBRuntimeException var)
           {
        	   LOGGER.debug("Opps got runtime an error..."+ var.getMessage());
        	   throw new EventNotFoundException("event not found");
        	              } 
           catch( EVDBAPIException var)
           {
        	   LOGGER.debug("Opps got runtime an error..."+ var.getMessage());  
        	   throw new EventNotFoundException("event not found");
           }
           
         //Sort			
			Collections.sort(searchResult.getEvents(), new Comparator<Event>() {
				@Override
				public int compare(Event e1, Event e2) {
					return  e1.getStartTime().compareTo(e2.getStartTime());
				}
			});
                     
           return searchResult.getEvents();
	}

	protected EventSearchRequest populateEventSearchResult(String keyword, String location) {
		APIConfiguration.setApiKey(API_KEY);
    	   APIConfiguration.setEvdbUser(dbuser);
    	   APIConfiguration.setEvdbPassword(dbpassword);
           EventSearchRequest eventSearchResult = new EventSearchRequest();
           eventSearchResult.setLocation(location);
           eventSearchResult.setKeywords(keyword);
           eventSearchResult.setPageSize(20);
           eventSearchResult.setPageNumber(1);
           eventSearchResult.setConnectionTimeout(60000);  
           eventSearchResult.setReadTimeout(60000);
		return eventSearchResult;
	}
       
       
       @GetMapping(value ="/getsearchresult3" , produces = MediaType.APPLICATION_XML_VALUE)
     		public Event getFirstEventFromWeb(@RequestParam String keyword, @RequestParam String location)
     		{		
         	   return getFirstEventFromWebBL(keyword, location);
     		}

	private Event getFirstEventFromWebBL(String keyword, String location) {
		String transactionUrl = eventfulHost+ "/rest/events/search";

		   UriComponentsBuilder builder = UriComponentsBuilder
		       .fromUriString(transactionUrl)
		       .queryParam("keywords", keyword)
		       .queryParam("location", location)
		       .queryParam("app_key", API_KEY);

			//String uri = eventfulHost+"/rest/events/search?keywords=music&location=Singapore&app_key=" + API_KEY;
			
			RestTemplate restTemplate = new RestTemplate();
			//String result = restTemplate.getForObject(builder.toUriString(), String.class);
			SearchResult searchresult = restTemplate.getForObject(builder.toUriString(), SearchResult.class);
						
			return searchresult.getEvents().get(0);
	}
	
	@GetMapping("/getlocationresult")
	
	public String getWeatherForLocation(@RequestParam String location)
	{
	String appKey = "da778bfe64332294577a57626a0c965a";
	RestTemplate restTemplate = new RestTemplate();
	
	String uri= "https://samples.openweathermap.org/data/2.5/forecast?q="+location+"&appid="+appKey;
	
	return restTemplate.getForObject(uri, String.class);
	}
      
	}


