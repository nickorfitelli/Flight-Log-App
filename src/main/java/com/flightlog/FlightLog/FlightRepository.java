package com.flightlog.FlightLog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Long > {
    List<Flight> findByPilotIsNull();
    @Query(value = "SELECT pilot_id FROM flight WHERE date = :date AND pilot_id IS NOT NULL", nativeQuery = true)
    List<Long> findFlightsWithPilots(Date date);
    Iterable<Flight> findByPilotId(Long id);

    @Query(value = "SELECT * FROM flight WHERE date = :date", nativeQuery = true)
    Iterable<Flight> findSameDayFlights(String date);

}
