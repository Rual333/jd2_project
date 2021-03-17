package by.it.academy.cv.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_TECHNOLOGIES")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"jobCandidates"})
@ToString(exclude = {"jobCandidates"})
public class Technology {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @Column(name="TECHNOLOGY_ID")
    private String technologyId;

    @Column(name = "F_TECHNOLOGY_NAME", nullable = false)
    private String technologyName;

    @ManyToMany(mappedBy = "technologies")
    private List<JobCandidate> jobCandidates;
}
