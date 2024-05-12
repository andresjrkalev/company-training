package ee.company.training.common;

public class Constant {
    public static final String ENDPOINT_ID = "/{id}";

    public static final String ENDPOINT_COURSE = "/course";
    public static final String ENDPOINT_COURSE_PARTICIPANT = ENDPOINT_ID + "/participant";
    public static final String ENDPOINT_DELETE_COURSE_PARTICIPANT = "/{courseId}/participant/{participantId}";
    public static final String ENDPOINT_PARTICIPANT = "/participant";
    public static final String ENDPOINT_PARTICIPANT_COURSE = ENDPOINT_ID + "/course";

    public static final String PROPERTY_COURSES = "courses";
    public static final String PROPERTY_PARTICIPANTS = "participants";
}
