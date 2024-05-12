package ee.company.training.controller;

import ee.company.training.common.Constant;
import ee.company.training.model.*;
import ee.company.training.service.ParticipantService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping(Constant.ENDPOINT_PARTICIPANT)
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public List<Participant> read() {
        return participantService.read();
    }

    @GetMapping(Constant.ENDPOINT_PARTICIPANT_COURSE)
    public Set<Course> readCourses(@PathVariable Long id) {
        return participantService.readCourses(id);
    }

    @PostMapping
    public Participant create(@RequestBody @NonNull Participant participant) {
        return participantService.createOrUpdate(participant);
    }

    @PutMapping
    public Participant update(@RequestBody @NonNull Participant participant) {
        if (participant.getId() == null) throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Id missing");
        return participantService.createOrUpdate(participant);
    }

    @DeleteMapping(Constant.ENDPOINT_ID)
    public void delete(@PathVariable @NonNull Long id) {
        participantService.delete(id);
    }
}
