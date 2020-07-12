package testFunctionsRepo;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apiPojoClass.WeatherReport;
import driverRepo.DriverClass;
import io.restassured.http.Method;

public class APIComponent extends DriverClass{
	public 	WeatherReport weatherReport_API;
    ObjectMapper mapper = new ObjectMapper();

	public void getWeather_API() {
		request.baseUri(getPropertyValue("baseURI"));
		request.param("q", City);
		request.param("appid", getPropertyValue("apiKey"));
		request.param("units", "metric");
		response = request.request(Method.GET);	
       
	}

	
	public HashMap<String,String> getCityWeatherReport_API(){
		HashMap<String,String> apiValues = new HashMap<String,String>();
		 try {
				weatherReport_API = mapper.readValue(response.asString(), WeatherReport.class);
	        } catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String Temp = weatherReport_API.getMain().getTemp().toString();
	    String Humidity = weatherReport_API.getMain().getHumidity().toString();
	    String WindSpeed = weatherReport_API.getWind().getSpeed().toString();
        apiValues.put("Temp in Degrees", Temp);
        apiValues.put("Humidity", Humidity);
        apiValues.put("WindSpeed", WindSpeed);
        return apiValues;
		
	}
}
