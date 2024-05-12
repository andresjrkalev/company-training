package ee.company.training.controller;

import ee.company.training.common.Constant;
import ee.company.training.model.*;
import ee.company.training.service.CourseService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping(Constant.ENDPOINT_COURSE)
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> read() {
        return courseService.read();
    }

    @GetMapping(Constant.ENDPOINT_COURSE_PARTICIPANT)
    public Set<Participant> readParticipants(@PathVariable Long id) {
        return courseService.readParticipants(id);
    }

    @PostMapping
    public Course create(@RequestBody @NonNull Course course) {
        return courseService.createOrUpdate(course);
    }

    @PostMapping(Constant.ENDPOINT_COURSE_PARTICIPANT)
    public void createParticipant(@PathVariable Long id, @RequestBody @NonNull Participant participant) {
        courseService.createParticipant(id, participant);
    }

    @PutMapping
    public Course update(@RequestBody @NonNull Course course) {
        if (course.getId() == null) throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Id missing");
        return courseService.createOrUpdate(course);
    }

    @DeleteMapping(Constant.ENDPOINT_ID)
    public void delete(@PathVariable @NonNull Long id) {
        courseService.delete(id);
    }

    @DeleteMapping(Constant.ENDPOINT_DELETE_COURSE_PARTICIPANT)
    public void deleteParticipant(@PathVariable Long courseId, @PathVariable Long participantId) {
        courseService.deleteParticipant(courseId, participantId);
    }
}
