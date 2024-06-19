package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Week;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekRepository extends CrudRepository<Week,Long> {

    public boolean existsByTransportAndPlanAndLocationAndHotelAndPrice(String transport, String plan, String location, String hotel, Integer price);
}
