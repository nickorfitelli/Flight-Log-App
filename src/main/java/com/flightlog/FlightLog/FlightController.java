package com.flightlog.FlightLog;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightRepository repository;
    private final PilotRepository pilotrepository;


    public FlightController(FlightRepository repository, PilotRepository pilotRepository){
        this.repository = repository;
        this.pilotrepository = pilotRepository;
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
        Optional<Pilot>  presult = this.pilotrepository.findById(pilotId);

        if(result.isPresent() && presult.isPresent()){
            Flight flight = result.get();
            Pilot pilot = presult.get();

            flight.setPilot(pilot);

            return this.repository.save(flight);
        }else{
            return "Flight not found";
        }
    }

    @PatchMapping("/assign")
    public List<Flight> assignRandom(){
        //get all flights without pilot
        //for (all flights)
        //get all flights on same date with pilots assigned
        // get all pilots where id != line 68
        //pick random index in list from line 69, assign to current flight

        List<Flight> flights = this.repository.findByPilotIsNull();

        for (Flight flight:flights) {
            System.out.println(flight.getDate());
            System.out.println(this.repository.findFlightsWithPilots(flight.getDate()).toString());
            Collection<Long> busyPilots = this.repository.findFlightsWithPilots(flight.getDate());

            if(busyPilots.size() == 0){
                busyPilots.add(0L);
            }
            List<Pilot> availPilots = this.pilotrepository.findByIdNotIn(busyPilots);
            Random r = new Random();
            System.out.println(availPilots.get(r.nextInt(availPilots.size())).getFirstName());

            flight.setPilot(availPilots.get(r.nextInt(availPilots.size())));

            this.repository.save(flight);

        }
       return flights;

    }

    @PatchMapping("/update/{id}")
    public Object updateFlight(@PathVariable long id, @RequestBody Flight flight){
        Optional<Flight> current = this.repository.findById(id);
        if(current.isPresent()){
            if(flight.getDate() != null){
                current.get().setDate(flight.getDate());
            }
            if(flight.getTime() != null){
                current.get().setTime(flight.getTime());
            }

            return this.repository.save(current.get());
        }
        return "No flight matches ID";
    }

    @GetMapping("/date/")
    public Iterable<Flight> getFlightsOnnDate(@RequestParam String date){
        return this.repository.findSameDayFlights(date);
    }
}
