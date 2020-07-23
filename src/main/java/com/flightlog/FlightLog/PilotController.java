package com.flightlog.FlightLog;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pilot")
public class PilotController {

    private final PilotRepository repository;

    public PilotController(PilotRepository repository){
        this.repository = repository;
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


}
