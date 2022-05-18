package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.repository.CityRepository;
import com.example.springboot99.utility.HttpRequestWeatherAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private CityRepository theCityRepository;

    @Override
    public City getCityByCityName(String cityName){
        if(theCityRepository.findByCityNameIgnoreCase(cityName).isEmpty()) {
            City localCity = HttpRequestWeatherAPI.getTheCity(cityName);
            theCityRepository.save(localCity);}
        return theCityRepository.findByCityNameIgnoreCase(cityName).get(0);
    }

    @Override
    public List<City> getAllCtities() {
        return theCityRepository.findAll();
    }


}