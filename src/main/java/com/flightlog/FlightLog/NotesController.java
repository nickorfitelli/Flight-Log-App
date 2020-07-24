package com.flightlog.FlightLog;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public Object addNote(@PathVariable Long flightid, @RequestBody(required=false) Notes bodynote) throws NullPointerException{

        try{
            String test = bodynote.getNotes();
            System.out.println(bodynote);
            System.out.println(test);
            if(test == null){
                return "No notes in request body";
            }
        } catch (NullPointerException ex){
            return "No body found";
        }

        Notes note = new Notes();
          Optional<Flight> flight = flightrepository.findById(flightid);;
          if(flight.isPresent() && (bodynote != null && !bodynote.getNotes().equals(""))){

              Flight flight1 = flight.get();

              Optional<Pilot> pilot = pilotrepository.findById(flight1.getPilot().getId());

              if(pilot.isPresent()){
                  note.setFlight(flight1);
                  Pilot pilot1 = pilot.get();
                  note.setPilot(pilot1);
                  note.setNotes(bodynote.getNotes());

                  return this.noterepository.save(note);
              }


          }

        return "Something went wrong...";
    }
}