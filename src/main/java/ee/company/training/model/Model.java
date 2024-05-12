package ee.company.training.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
