package com.flightlog.FlightLog;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface PilotRepository extends CrudRepository<Pilot, Long>  {
    List<Pilot> findByIdNotIn(Collection<Long> ids);
}
