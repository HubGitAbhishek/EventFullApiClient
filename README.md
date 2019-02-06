# EventFullApiClient
EventFullApiClient

This Project Calls EventFul API Client with Keywork and location and fetch the result.
API Example is: http://api.eventful.com/rest/events/search?...&location=San+Diego

This Project also fetches weather location for a particular location .
API example: api.openweathermap.org/data/2.5/weather?q={city name}

The application has used Spring boot and Java for creation and swagger for API documentation

Below is the list of all endpoints and their functionalities

1) http://localhost:8080/getsearchresult1?keyword=music&location=Singapore
    
    It fetched data from Eventful API and return the xml format output sorted by start time.
    It takes location and keyword as input
    Method Used is: CallEventfulAPiService:: getSearchResultFromWeb
    
 2)  http://localhost:8080/getsearchresult2?keyword=music&location=Singapore
    
    It fetched data from Eventful backend using its library as POM dependency and return the Json format output.
    It takes location and keyword as input
    Method Used is: CallEventfulAPiService:: getSearchResultFromBackend
    
 3) http://localhost:8080/getsearchresult3?keyword=music&location=Singapore
    
    It fetched data OF first event from Eventful API and return the xml format output.
    It takes location and keyword as input
    Method Used is: CallEventfulAPiService:: getFirstEventFromWeb
    
 4) https://samples.openweathermap.org/data/2.5/forecast?q=Singapore&appid=da778bfe64332294577a57626a0c965a
 
     It fetches weather information from openweathermap API with location as input
     Method Used is: CallEventfulAPiService::getWeatherForLocation
     
 For Swagger documentation, below URL can be used
 http://localhost:8080/swagger-ui.html
