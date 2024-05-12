package controller;

import ee.company.training.Application;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createShouldReturnCourse() throws Exception {
        String content = "{" +
                "\"title\": \"Title\"," +
                "\"startTime\": \"2015-07-23T10:31:33Z\"," +
                "\"endTime\": \"2015-07-24T10:31:33Z\"," +
                "\"description\": \"Description\"," +
                "\"maximumNumberOfParticipants\": 3" +
                "}";
        RequestBuilder requestBuilder = post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        Matcher<Integer> matcher = is(1);
        ResultMatcher resultMatcher = jsonPath("$.id", matcher);
        mockMvc.perform(requestBuilder).andExpect(resultMatcher);
    }
}
