package com.flightlog.FlightLog;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightRepository repository;

    public FlightController(FlightRepository repository){
        this.repository = repository;
    }

    @PostMapping("/add")
    public Flight addFlight(@RequestBody Flight flight){
        return this.repository.save(flight);
    }

    @GetMapping("")
    public Iterable<Flight> getFlights(){
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Flight> getFlight(@PathVariable Long id){
        return this.repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable Long id){
        if(this.repository.findById(id).isEmpty()){
            return String.format("There is no flight with id: %d to delete", id);
        }
        this.repository.deleteById(id);
        if(this.repository.findById(id).isEmpty()){
            return String.format("Flight with id: %d was deleted", id);
        }

        return "Something went wrong";
    }

    @PatchMapping("/{flightId}/addpilot/{pilotId}")
    public Object addPilot(@PathVariable Long flightId, @PathVariable Long pilotId){
        Optional<Flight> result = this.repository.findById(flightId);

        if(result.isPresent()){
            Flight flight = result.get();

//            flight.setPilot_id(pilotId);

            return this.repository.save(flight);
        }else{
            return "Flight not found";
        }


    }
}
