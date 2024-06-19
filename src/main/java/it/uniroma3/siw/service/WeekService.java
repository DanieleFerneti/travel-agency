package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Week;
import it.uniroma3.siw.repository.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeekService {

    @Autowired
    private WeekRepository weekRepository;

    @Transactional
    public List<Week> getAllWeeks() {
        List<Week> weeks = new ArrayList<Week>();
        for(Week w : this.weekRepository.findAll()) {
            weeks.add(w);
        }
        return weeks;
    }


    @Transactional
    public void save(Week week) {
        this.weekRepository.save(week);
    }

    @Transactional
    public Week getWeek(Long id) {
        return this.weekRepository.findById(id).get();
    }

    @Transactional
    public boolean alreadyExists(Week week) {
        return this.weekRepository.existsByTransportAndPlanAndLocationAndHotelAndPrice(week.getTransport(),week.getPlan(),week.getLocation(),week.getHotel(),week.getPrice());
    }

    @Transactional
    public void deleteById(Long id) {
        this.weekRepository.deleteById(id);
    }
}
