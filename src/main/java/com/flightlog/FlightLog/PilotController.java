package com.flightlog.FlightLog;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pilot")
public class PilotController {

    private final PilotRepository repository;
    private final FlightRepository fltrepository;

    public PilotController(PilotRepository repository,FlightRepository fltrepository){
        this.repository = repository;
        this.fltrepository = fltrepository;
    }

    @GetMapping("")
    public Iterable<Pilot> getPilots(){
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Pilot> getPilot(@PathVariable Long id){
        return this.repository.findById(id);
    }

    @PostMapping("")
    public Pilot addPilot(@RequestBody Pilot pilot){
        return this.repository.save(pilot);
    }

    @DeleteMapping("/{id}")
    public String deletePilot(@PathVariable Long id){
        if(this.repository.findById(id).isEmpty()){
            return String.format("There is no pilot with id: %d to delete", id);
        }
        this.repository.deleteById(id);
        if(this.repository.findById(id).isEmpty()){
            return String.format("Pilot with id: %d was deleted", id);
        }

        return "Something went wrong";
    }

    @GetMapping("/{id}/flights")
    public Iterable<Flight> findYourFlights(@PathVariable Long id){
        return this.fltrepository.findByPilotId(id);
    }

    @DeleteMapping("{pilotid}/flight/{flightid}")
    public Object deletePilotFlight(@PathVariable Long pilotid, @PathVariable Long flightid){
        Optional<Flight> result = this.fltrepository.findById(flightid);
        Optional<Pilot>  presult = this.repository.findById(pilotid);

        if(result.isPresent() && presult.isPresent()){
            Flight flight = result.get();
            Pilot pilot = presult.get();

            flight.setPilot(null);

            return this.fltrepository.save(flight);
        }else{
            return "Flight not found";
        }
    }

}
