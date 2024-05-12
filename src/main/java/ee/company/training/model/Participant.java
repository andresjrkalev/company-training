package ee.company.training.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.company.training.common.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Participant extends Model {
    private String name;

    @JsonIgnoreProperties(Constant.PROPERTY_PARTICIPANTS)
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Course> courses;
}
