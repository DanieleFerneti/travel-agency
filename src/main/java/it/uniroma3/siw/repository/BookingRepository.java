package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Week;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking,Long> {

    List<Booking> findByGroup(Group group);

    boolean existsByGroupAndWeek(Group group, Week week);
}
