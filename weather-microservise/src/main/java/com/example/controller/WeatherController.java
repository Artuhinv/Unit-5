package com.example.controller;

import com.example.model.Main;
import com.example.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
public class WeatherController {
    @Value("${openweathermap.apikey}")
    private String apiKey;
    @Value("${url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    @Cacheable(value = "cacheForWeathers",key = "#lat+':'+#lon")
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {
        String url1 = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", url, lat, lon, apiKey);
        return restTemplate.getForObject(url1, Root.class).getMain();
    }
}
