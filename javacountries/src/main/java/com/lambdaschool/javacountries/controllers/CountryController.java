package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryRepository;

    //http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> listAllCountries(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    //http://localhost:2019/names/start/u

    //http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> getTotalPopulation(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        double total = 0.0;
        for(Country c : countryList){
            total += c.getPopulation();
        }

        System.out.println("The Total Population is " + total);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> getLowestPopulationCountry(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));

        Country lowestPopulationCountry = countryList.get(0);

        return new ResponseEntity<>(lowestPopulationCountry, HttpStatus.OK);
    }

    //http://loclahost:2019/population/max

}
