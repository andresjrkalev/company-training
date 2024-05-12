package ee.company.training.service;

import ee.company.training.model.*;
import ee.company.training.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ParticipantService participantService;

    public CourseService(CourseRepository courseRepository, ParticipantService participantService) {
        this.courseRepository = courseRepository;
        this.participantService = participantService;
    }

    public List<Course> read() {
        return courseRepository.findAll();
    }

    private Course findById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course id: " + id);
        return course;
    }

    private Course findOngoingById(Long id) {
        Course course = findById(id);
        if (Instant.now().isAfter(course.getEndTime())) throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Course has ended");
        return course;
    }

    public Set<Participant> readParticipants(Long id) {
        Course course = findById(id);
        return course.getParticipants();
    }

    public Course createOrUpdate(Course course) {
        return courseRepository.save(course);
    }

    public void createParticipant(Long id, Participant participant) {
        Course course = findOngoingById(id);
        Set<Participant> participants = course.getParticipants();
        participants.add(participant);
        if (participants.size() > course.getMaximumNumberOfParticipants()) throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Course maximum capacity reached");
        Set<Course> courses = Set.of(course);
        participant.setCourses(courses);
        createOrUpdate(course);
    }

    public void delete(Long id) {
        boolean exists = courseRepository.existsById(id);
        if (!exists) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course id: " + id);
        courseRepository.deleteById(id);
    }

    public void deleteParticipant(Long courseId, Long participantId) {
        boolean isParticipantExists = participantService.existsById(participantId);
        if (!isParticipantExists) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant id: " + participantId);
        Course course = findOngoingById(courseId);
        Set<Participant> participants = course.getParticipants()
                .stream()
                .filter(filterParticipants(courseId, participantId))
                .collect(Collectors.toSet());
        course.setParticipants(participants);
        createOrUpdate(course);
    }

    private Predicate<Participant> filterParticipants(Long courseId, Long participantId) {
        return participant -> {
            boolean isParticipantExists = Objects.equals(participant.getId(), participantId);

            if (isParticipantExists) {
                Set<Course> courses = participant.getCourses()
                        .stream()
                        .filter(course -> !Objects.equals(course.getId(), courseId))
                        .collect(Collectors.toSet());
                participant.setCourses(courses);
                participantService.createOrUpdate(participant);
                return false;
            }
            return true;
        };
    }
}
