package ee.company.training.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.company.training.common.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course extends Model {
    private String title;
    private Instant startTime;
    private Instant endTime;
    private String description;
    private int maximumNumberOfParticipants;

    @JsonIgnoreProperties(Constant.PROPERTY_COURSES)
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Participant> participants;
}
