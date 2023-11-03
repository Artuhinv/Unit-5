package com.example.controller;

import com.example.model.Location;
import com.example.model.Weather;
import com.example.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${url}")
    String Url;
    @GetMapping
    public Optional<Location> getWeather(@RequestParam String location) {
        return repository.findByName(location);
    }

    @GetMapping("/weather")
    public ResponseEntity<Weather> redirectRequestWeather(@RequestParam String weather) {
        Optional<Location> locationOptional = repository.findByName(weather);
        if(locationOptional.isPresent()) {
            Location location = locationOptional.get();
            String url = String.format("http://%s/?lat=%s&lon=%s",Url , location.getLat(), location.getLon());
            Weather weather_loc = restTemplate.getForObject(url, Weather.class);
            return new ResponseEntity<>(weather_loc,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    public Iterable<Location> getMessages() {
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Location> findById(@PathVariable int id) {
        return repository.findById(id);
    }
    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        Optional<Location> locationOptional = repository.findByName(location.getName());
        if(locationOptional.isPresent())
            return new ResponseEntity<>(locationOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>(repository.save(location), HttpStatus.CREATED);
        }
    }
}

