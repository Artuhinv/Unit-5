package com.example.controller;

import com.example.model.Main;
import com.example.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Value("${openweathermap.apikey}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{lat}/{lon}")
    @Cacheable(value = "cacheForWeathers",key = "#lat+':'+#lon")
    public Main getWeather(@PathVariable double lat, @PathVariable double lon) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;

        return restTemplate.getForObject(url, Root.class).getMain();
    }
}
