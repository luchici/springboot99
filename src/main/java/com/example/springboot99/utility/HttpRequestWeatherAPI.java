package com.example.springboot99.utility;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

public class HttpRequestWeatherAPI {

    private static final String API = "6cabf84a4fba39918788106bb0cea492";
    private static ObjectMapper mapper = new ObjectMapper();
    private static LocalDate date = LocalDate.now();

    public static City getTheCity(String cityName){
        String cityNameProcess = cityName.substring(0,1).toUpperCase()+cityName.substring(1).toLowerCase();
        String theURL = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityNameProcess + ",RO&limit=3&appid=" + API;

        HttpResponse<String> response = httpRequestAPI(theURL);
        List<City> cities = null;
        try {
            cities = mapper.readValue(response.body(), new TypeReference<List<City>>() {            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Parsing error, the reposne is not good");
            System.out.println("Error parsing for City");
        }
        City localCity = cities.get(0);
        localCity.setCityName(cityNameProcess);
        return localCity;
        }

    public static Weather getTheWeather(City city){
        String theURL = "https://api.openweathermap.org/data/2.5/weather?lat="+city.getLat()+"&lon="+city.getLon()+"&appid="+API;
        HttpResponse<String> response = httpRequestAPI(theURL);
        Weather weatherToday = null;
        try {
            weatherToday = mapper.readValue(response.body(), Weather.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Parsing error, the reposne is not good");
            System.out.println("Error parsing for Weather");
        }
        weatherToday.setLocalDate(date);
        return weatherToday;
    }

    public static HttpResponse<String> httpRequestAPI(String theURL) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(theURL))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException e) {
            System.out.println("1.API Key is expired," +
                    "\n2.cityNameProcess is wrong" +
                    "\n3.Some other problem with the URL");
            e.printStackTrace();
            return null;
        } catch(InterruptedException e) {
            System.out.println("Connectivity problems, try again");
            e.printStackTrace();
            return null;
        }
    }
}
