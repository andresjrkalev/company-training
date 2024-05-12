package service;

import ee.company.training.Application;
import ee.company.training.model.Participant;
import ee.company.training.repository.ParticipantRepository;
import ee.company.training.service.ParticipantService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
public class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    void readShouldReturnParticipants() {
        List<Participant> participants = new ArrayList<>();
        when(participantRepository.findAll()).thenReturn(participants);
        List<Participant> response = participantService.read();
        assertThat(response).isEqualTo(participants);
    }

    @Test
    void createShouldReturnParticipant() {
        Participant participant = new Participant();
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        Participant response = participantService.createOrUpdate(participant);
        assertThat(response).isEqualTo(participant);
    }
}
