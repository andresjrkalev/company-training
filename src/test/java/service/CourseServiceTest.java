package service;

import ee.company.training.Application;
import ee.company.training.model.*;
import ee.company.training.repository.CourseRepository;
import ee.company.training.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void createParticipantShouldThrowNotFound() {
        Long id = 1L;
        Participant participant = new Participant();
        Executable executable = () -> courseService.createParticipant(id, participant);
        Exception exception = assertThrows(ResponseStatusException.class, executable);
        assertEquals("404 NOT_FOUND \"Course id: " + id + "\"", exception.getMessage());
    }

    @Test
    void createParticipantShouldThrowHasEnded() {
        Long id = 1L;
        Course course = new Course();
        course.setId(id);
        course.setEndTime(Instant.now());
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        Participant participant = new Participant();
        Executable executable = () -> courseService.createParticipant(id, participant);
        Exception exception = assertThrows(ResponseStatusException.class, executable);
        assertEquals("417 EXPECTATION_FAILED \"Course has ended\"", exception.getMessage());
    }

    @Test
    void createParticipantShouldThrowMaximumCapacityReached() {
        Long id = 1L;
        Course course = new Course();
        course.setId(id);
        Instant endTime = Instant.now().plus(Duration.ofHours(1));
        course.setEndTime(endTime);
        course.setParticipants(new HashSet<>());
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        Participant participant = new Participant();
        Executable executable = () -> courseService.createParticipant(id, participant);
        Exception exception = assertThrows(ResponseStatusException.class, executable);
        assertEquals("417 EXPECTATION_FAILED \"Course maximum capacity reached\"", exception.getMessage());
    }
}
