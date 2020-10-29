package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryRepository;

    public List<Country> filterCountries(List<Country> countryList, CheckCountry tester){
        List<Country> testList = new ArrayList<>();

        for(Country c : countryList){
            if(tester.test(c)){
                testList.add(c);
            }
        }

        return testList;
    }

    //http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> listAllCountries(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    //http://localhost:2019/names/start/{letter}
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> findCountriesThatStartWith(@PathVariable char letter){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        List<Country> rtnList = filterCountries(countryList, c -> c.getName().charAt(0) == letter);

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

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
    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> getHighestPopulationCountry(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> (int)(c2.getPopulation() - c1.getPopulation()));

        Country highestPopulationCountry = countryList.get(0);

        return new ResponseEntity<>(highestPopulationCountry, HttpStatus.OK);
    }

    //http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = "application/json")
    public ResponseEntity<?> getMedianPopulationCountry(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> (int)(c2.getPopulation() - c1.getPopulation()));

        Country medianPopulationCountry = new Country();

        if(countryList.size() % 2 == 0){
            //Even sized list
            //Grab the country just after the one in the middle
            medianPopulationCountry = countryList.get((countryList.size() / 2 + 1));
        }
        else{
            //Odd sized list
            //Grab the country before the country in the middle
            medianPopulationCountry = countryList.get((countryList.size() / 2 - 1));
        }

        return new ResponseEntity<>(medianPopulationCountry, HttpStatus.OK);
    }
}
