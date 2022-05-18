package com.example.springboot99.repository;

import com.example.springboot99.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByBodyFeelsAndLocalDate(Integer tempValue, LocalDate localDate);

    List<Weather> findByDescriptionAndLocalDate(String description, LocalDate localDate);

    List<Weather> findByBodyFeels(Integer tempValue);

    List<Weather> findByDescription(String description);

}
