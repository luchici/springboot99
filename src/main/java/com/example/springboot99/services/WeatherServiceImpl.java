package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import com.example.springboot99.repository.CityRepository;
import com.example.springboot99.repository.WeatherRepository;
import com.example.springboot99.utility.HttpRequestWeatherAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService{

    private static LocalDate date = LocalDate.now();

    private WeatherRepository weatherRepository;
    private CityRepository cityRepository;

    public static LocalDate updateDate(){
        date = LocalDate.now();
        return date;
    }
    public static LocalDate parseDate(String dayAndMonth){
        String procesDate = dayAndMonth+".2022";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate =
                LocalDate.parse(procesDate, formatter);
        return localDate;
    }

//    TODO: Update all the cities, need to run daily at the same hour


//    public void updateWeatherByCityName(String cityName){
//        System.out.println("------------------------We are in updateWeatherByCityName ------------------------------------------");
//        updateDate();
//        City localCity = cityRepository.findByCityNameIgnoreCase(cityName).get(0);
//        Weather weatherToday = HttpRequestWeatherAPI.getTheWeather(localCity);
//        System.out.println(localCity);
//        System.out.println(weatherToday);
//
//        localCity.addWeatherRaport(weatherToday,date);
//
//        cityRepository.saveAndFlush(localCity);
//        weatherRepository.saveAndFlush(weatherToday);
////        return city;
//    }

    @Override
    public void updateWeatherByCityName(City city){
        System.out.println("------------------------We are in updateWeatherByCityName ------------------------------------------");
        updateDate();
        Weather weatherToday = HttpRequestWeatherAPI.getTheWeather(city);

//        System.out.println(city);
//        System.out.println(weatherToday);

        city.addWeatherRaport(weatherToday,date);

        cityRepository.saveAndFlush(city);
//        weatherRepository.saveAndFlush(weatherToday);
//        return city;

    }

    @Override
    public void updateWeatherAllCities(){
        System.out.println("------------------------We are in updateWeatherAllCities ------------------------------------------");
        List<City> cities = cityRepository.findAll();
        for (City city : cities) {
            updateWeatherByCityName(city);
        }
    }

    //  TODO: Add the case when no temperature exists
//    @Override
//    public List<Weather> getWeatherByTempValueAndDate(Integer tempValue, String date) {
//        System.out.println("------------------------We are in getWeatherByTempValueAndDate ------------------------------------------");
//        LocalDate localDate = parseDate(date);
//        return weatherRepository.findByBodyFeelsAndLocalDate(tempValue, localDate);
//    }
//
//    //  TODO: Add the case when no temperature exists
//    public List<Weather> getWeatherByTempValue(Integer tempValue) {
//        System.out.println("------------------------We are in getWeatherByTempValue ------------------------------------------");
//        return weatherRepository.findByBodyFeels(tempValue);
//    }
//
//    //  TODO: Add the case when no descrption exists
//    public List<Weather> getWeatherByDescriptionAndLocalDate(String description, String date) {
//        System.out.println("------------------------We are in getWeatherByDescriptionAndLocalDate ------------------------------------------");
//
//        LocalDate localDate = parseDate(date);
//        return weatherRepository.findByDescriptionAndLocalDate(description, localDate);
//    }
//
//    //  TODO: Add the case when no descrption exists
//    public List<Weather> getWeatherByDescription(String description) {
//        System.out.println("------------------------We are in getWeatherByDescription ------------------------------------------");
//
//        date = date == null ? updateDate() : date;
//        return weatherRepository.findByDescription(description);
//    }
}
