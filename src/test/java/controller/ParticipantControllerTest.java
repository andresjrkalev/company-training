package controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ee.company.training.Application;
import ee.company.training.model.Participant;
import ee.company.training.repository.ParticipantRepository;
import ee.company.training.service.ParticipantService;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipantRepository participantRepository;

    @MockBean
    private ParticipantService participantService;

    @Test
    void readShouldReturnParticipants() throws Exception {
        RequestBuilder requestBuilder = get("/participant");
        ResultMatcher resultMatcher = content().json("[]");
        mockMvc.perform(requestBuilder).andExpect(resultMatcher);
    }

    @Test
    void createShouldReturnParticipant() throws Exception {
        String content = "{ \"name\": \"John\" }";
        Participant participant = new Participant();
        Long id = 1L;
        participant.setId(id);
        when(participantService.createOrUpdate(any(Participant.class))).thenReturn(participant);
        RequestBuilder requestBuilder = post("/participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        Matcher<Integer> matcher = is(id.intValue());
        ResultMatcher resultMatcher = jsonPath("$.id", matcher);
        mockMvc.perform(requestBuilder).andExpect(resultMatcher);
    }

    @Test
    void deleteShouldCallService() throws Exception {
        Long id = 1L;
        when(participantRepository.existsById(id)).thenReturn(true);
        RequestBuilder requestBuilder = delete("/participant/" + id);
        mockMvc.perform(requestBuilder);
        verify(participantService).delete(id);
    }
}
