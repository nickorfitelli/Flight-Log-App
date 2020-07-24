package com.flightlog.FlightLog;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final FlightRepository flightrepository;
    private final PilotRepository pilotrepository;
    private final NotesRepository noterepository;


    public NotesController(FlightRepository flightrepository, PilotRepository pilotRepository, NotesRepository noterepository) {
        this.flightrepository = flightrepository;
        this.pilotrepository = pilotRepository;
        this.noterepository = noterepository;
    }

    @PostMapping("/{flightid}")
    public Object addNote(@PathVariable Long id, @RequestBody String bodynote){
          Notes note = new Notes();
//        Flight flight = new Flight();

            note.setFlight(id);

    }
}