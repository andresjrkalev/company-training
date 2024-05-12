package ee.company.training.service;

import ee.company.training.model.*;
import ee.company.training.repository.ParticipantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> read() {
        return participantRepository.findAll();
    }

    public Set<Course> readCourses(Long id) {
        Participant participant = participantRepository.findById(id).orElse(null);
        if (participant == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant id: " + id);
        return participant.getCourses();
    }

    public boolean existsById(Long id) {
        return participantRepository.existsById(id);
    }

    public Participant createOrUpdate(Participant participant) {
        return participantRepository.save(participant);
    }

    public void delete(Long id) {
        boolean exists = existsById(id);
        if (!exists) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant id: " + id);
        participantRepository.deleteById(id);
    }
}
