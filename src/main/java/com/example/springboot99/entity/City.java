package com.example.springboot99.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "cities")
@Immutable
@Cacheable
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @JsonProperty("city_name")
    private String cityName;
    private double lat;
    private double lon;

    @OneToMany(mappedBy = "city",
               cascade = CascadeType.ALL)
    private List<Weather> weatherList = new ArrayList<>();


//    ADD -1000 FOR TESTING
    public void addWeatherRaport(Weather weather, LocalDate date) {
        System.out.println("In addWeatherRaport-------------------------------------------------------------------------");
        if (weatherList.isEmpty()) {
            weatherList.add(weather);
            weather.setCityName(this.cityName);
            weather.setCity(this);
        } else if (weatherList.get(weatherList.size() - 1).getLocalDate().compareTo(date) < 0) {
            if (weatherList.size() <= 10) {
                weatherList.add(weather);
                weather.setCityName(this.cityName);
                weather.setCity(this);
            } else {
                weatherList.get(0).setCity(null);
                weatherList.remove(0);
                weatherList.add(weather);
                weather.setCity(this);
            }
        }
    }
//    TODO: ADD A DATABASE CLEANER
//    public void cleanDatabase(){
//        if (!weatherList.isEmpty()){
//            int size = weatherList.size();
//            if (size > 10){
//                int diff = weatherList.size() - 11;
//                for(int i = 0; i<=diff ; i++){
//                    weatherList.get(i).setCity(null);
//                weatherList.remove(i);
//            }
//        } else for(int i = 0; i< size-2; i++)
//            if (weatherList.get(size-1).getLocalDate().compareTo(weatherList.get(size-2).getLocalDate()) <= 0){
//                weatherList.get(0).setCity(null);
//                weatherList.remove(0);
//        }}
//    }
//        return weather;

    public Long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @JsonManagedReference
    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", weatherList=" + weatherList +
                '}';
    }
}
