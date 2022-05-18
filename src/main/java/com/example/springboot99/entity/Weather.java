package com.example.springboot99.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "weather_list")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private Long id;

    private String description;
    private int temp;
    private int bodyFeels;
    private int tempMin;
    private int tempMax;
    private LocalDate localDate;
    @JsonProperty("name")
    private String cityName;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "city_id")
    private City city;

    @JsonProperty("weather")
    private void setDesc(List<Map<String, String>> weather) {
        this.description = weather.get(0).get("description");
    }

    @JsonProperty("main")
    private void setMain(Map<String, Double> main) {
        this.temp = tempConvertions(main.get("temp"));
        this.bodyFeels = tempConvertions(main.get("feels_like"));
        this.tempMin = tempConvertions(main.get("temp_min"));
        this.tempMax = tempConvertions(main.get("temp_max"));
    }

    private int tempConvertions(Double temp) {
        Double d = temp - 273.5;
        return d.intValue();
    }


    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getBodyFeels() {
        return bodyFeels;
    }

    public void setBodyFeels(int bodyFeels) {
        this.bodyFeels = bodyFeels;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @JsonBackReference
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", temp=" + temp +
                ", localDate=" + localDate +
                ", cityName='" + cityName + '\'' +
                ", city=" + city +
                '}';
    }
}
