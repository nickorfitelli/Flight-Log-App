package com.flightlog.FlightLog;

import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public Pilot addPilot(@RequestBody Pilot pilot){
        return this.repository.save(pilot);
    }
}
